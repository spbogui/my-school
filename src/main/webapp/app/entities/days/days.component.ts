import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDays } from 'app/shared/model/days.model';
import { AccountService } from 'app/core';
import { DaysService } from './days.service';

@Component({
    selector: 'jhi-days',
    templateUrl: './days.component.html'
})
export class DaysComponent implements OnInit, OnDestroy {
    days: IDays[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected daysService: DaysService,
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
            this.daysService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IDays[]>) => res.ok),
                    map((res: HttpResponse<IDays[]>) => res.body)
                )
                .subscribe((res: IDays[]) => (this.days = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.daysService
            .query()
            .pipe(
                filter((res: HttpResponse<IDays[]>) => res.ok),
                map((res: HttpResponse<IDays[]>) => res.body)
            )
            .subscribe(
                (res: IDays[]) => {
                    this.days = res;
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
        this.registerChangeInDays();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDays) {
        return item.id;
    }

    registerChangeInDays() {
        this.eventSubscriber = this.eventManager.subscribe('daysListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
