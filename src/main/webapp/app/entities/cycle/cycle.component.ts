import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICycle } from 'app/shared/model/cycle.model';
import { AccountService } from 'app/core';
import { CycleService } from './cycle.service';

@Component({
    selector: 'jhi-cycle',
    templateUrl: './cycle.component.html'
})
export class CycleComponent implements OnInit, OnDestroy {
    cycles: ICycle[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected cycleService: CycleService,
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
            this.cycleService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ICycle[]>) => res.ok),
                    map((res: HttpResponse<ICycle[]>) => res.body)
                )
                .subscribe((res: ICycle[]) => (this.cycles = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.cycleService
            .query()
            .pipe(
                filter((res: HttpResponse<ICycle[]>) => res.ok),
                map((res: HttpResponse<ICycle[]>) => res.body)
            )
            .subscribe(
                (res: ICycle[]) => {
                    this.cycles = res;
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
        this.registerChangeInCycles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICycle) {
        return item.id;
    }

    registerChangeInCycles() {
        this.eventSubscriber = this.eventManager.subscribe('cycleListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
