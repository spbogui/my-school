import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClassSessionType } from 'app/shared/model/class-session-type.model';
import { AccountService } from 'app/core';
import { ClassSessionTypeService } from './class-session-type.service';

@Component({
    selector: 'jhi-class-session-type',
    templateUrl: './class-session-type.component.html'
})
export class ClassSessionTypeComponent implements OnInit, OnDestroy {
    classSessionTypes: IClassSessionType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected classSessionTypeService: ClassSessionTypeService,
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
            this.classSessionTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IClassSessionType[]>) => res.ok),
                    map((res: HttpResponse<IClassSessionType[]>) => res.body)
                )
                .subscribe(
                    (res: IClassSessionType[]) => (this.classSessionTypes = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.classSessionTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IClassSessionType[]>) => res.ok),
                map((res: HttpResponse<IClassSessionType[]>) => res.body)
            )
            .subscribe(
                (res: IClassSessionType[]) => {
                    this.classSessionTypes = res;
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
        this.registerChangeInClassSessionTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClassSessionType) {
        return item.id;
    }

    registerChangeInClassSessionTypes() {
        this.eventSubscriber = this.eventManager.subscribe('classSessionTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
