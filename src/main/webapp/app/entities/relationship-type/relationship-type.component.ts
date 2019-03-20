import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { AccountService } from 'app/core';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type',
    templateUrl: './relationship-type.component.html'
})
export class RelationshipTypeComponent implements OnInit, OnDestroy {
    relationshipTypes: IRelationshipType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected relationshipTypeService: RelationshipTypeService,
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
            this.relationshipTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRelationshipType[]>) => res.ok),
                    map((res: HttpResponse<IRelationshipType[]>) => res.body)
                )
                .subscribe(
                    (res: IRelationshipType[]) => (this.relationshipTypes = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.relationshipTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IRelationshipType[]>) => res.ok),
                map((res: HttpResponse<IRelationshipType[]>) => res.body)
            )
            .subscribe(
                (res: IRelationshipType[]) => {
                    this.relationshipTypes = res;
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
        this.registerChangeInRelationshipTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRelationshipType) {
        return item.id;
    }

    registerChangeInRelationshipTypes() {
        this.eventSubscriber = this.eventManager.subscribe('relationshipTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
