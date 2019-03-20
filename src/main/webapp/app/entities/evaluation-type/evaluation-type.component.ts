import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEvaluationType } from 'app/shared/model/evaluation-type.model';
import { AccountService } from 'app/core';
import { EvaluationTypeService } from './evaluation-type.service';

@Component({
    selector: 'jhi-evaluation-type',
    templateUrl: './evaluation-type.component.html'
})
export class EvaluationTypeComponent implements OnInit, OnDestroy {
    evaluationTypes: IEvaluationType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected evaluationTypeService: EvaluationTypeService,
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
            this.evaluationTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IEvaluationType[]>) => res.ok),
                    map((res: HttpResponse<IEvaluationType[]>) => res.body)
                )
                .subscribe((res: IEvaluationType[]) => (this.evaluationTypes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.evaluationTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IEvaluationType[]>) => res.ok),
                map((res: HttpResponse<IEvaluationType[]>) => res.body)
            )
            .subscribe(
                (res: IEvaluationType[]) => {
                    this.evaluationTypes = res;
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
        this.registerChangeInEvaluationTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEvaluationType) {
        return item.id;
    }

    registerChangeInEvaluationTypes() {
        this.eventSubscriber = this.eventManager.subscribe('evaluationTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
