import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriodType } from 'app/shared/model/period-type.model';
import { AccountService } from 'app/core';
import { PeriodTypeService } from './period-type.service';

@Component({
    selector: 'jhi-period-type',
    templateUrl: './period-type.component.html'
})
export class PeriodTypeComponent implements OnInit, OnDestroy {
    periodTypes: IPeriodType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected periodTypeService: PeriodTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.periodTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IPeriodType[]>) => res.ok),
                    map((res: HttpResponse<IPeriodType[]>) => res.body)
                )
                .subscribe((res: IPeriodType[]) => (this.periodTypes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.periodTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IPeriodType[]>) => res.ok),
                map((res: HttpResponse<IPeriodType[]>) => res.body)
            )
            .subscribe(
                (res: IPeriodType[]) => {
                    this.periodTypes = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPeriodType) {
        return item.id;
    }

    registerChangeInPeriodTypes() {
        this.eventSubscriber = this.eventManager.subscribe('periodTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
