import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegimen } from 'app/shared/model/regimen.model';
import { AccountService } from 'app/core';
import { RegimenService } from './regimen.service';

@Component({
    selector: 'jhi-regimen',
    templateUrl: './regimen.component.html'
})
export class RegimenComponent implements OnInit, OnDestroy {
    regimen: IRegimen[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected regimenService: RegimenService,
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
            this.regimenService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRegimen[]>) => res.ok),
                    map((res: HttpResponse<IRegimen[]>) => res.body)
                )
                .subscribe((res: IRegimen[]) => (this.regimen = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.regimenService
            .query()
            .pipe(
                filter((res: HttpResponse<IRegimen[]>) => res.ok),
                map((res: HttpResponse<IRegimen[]>) => res.body)
            )
            .subscribe(
                (res: IRegimen[]) => {
                    this.regimen = res;
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
        this.registerChangeInRegimen();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegimen) {
        return item.id;
    }

    registerChangeInRegimen() {
        this.eventSubscriber = this.eventManager.subscribe('regimenListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
