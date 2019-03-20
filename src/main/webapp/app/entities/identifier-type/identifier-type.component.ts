import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIdentifierType } from 'app/shared/model/identifier-type.model';
import { AccountService } from 'app/core';
import { IdentifierTypeService } from './identifier-type.service';

@Component({
    selector: 'jhi-identifier-type',
    templateUrl: './identifier-type.component.html'
})
export class IdentifierTypeComponent implements OnInit, OnDestroy {
    identifierTypes: IIdentifierType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected identifierTypeService: IdentifierTypeService,
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
            this.identifierTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IIdentifierType[]>) => res.ok),
                    map((res: HttpResponse<IIdentifierType[]>) => res.body)
                )
                .subscribe((res: IIdentifierType[]) => (this.identifierTypes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.identifierTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IIdentifierType[]>) => res.ok),
                map((res: HttpResponse<IIdentifierType[]>) => res.body)
            )
            .subscribe(
                (res: IIdentifierType[]) => {
                    this.identifierTypes = res;
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
        this.registerChangeInIdentifierTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIdentifierType) {
        return item.id;
    }

    registerChangeInIdentifierTypes() {
        this.eventSubscriber = this.eventManager.subscribe('identifierTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
