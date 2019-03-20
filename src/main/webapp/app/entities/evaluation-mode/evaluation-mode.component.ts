import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';
import { AccountService } from 'app/core';
import { EvaluationModeService } from './evaluation-mode.service';

@Component({
    selector: 'jhi-evaluation-mode',
    templateUrl: './evaluation-mode.component.html'
})
export class EvaluationModeComponent implements OnInit, OnDestroy {
    evaluationModes: IEvaluationMode[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected evaluationModeService: EvaluationModeService,
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
            this.evaluationModeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IEvaluationMode[]>) => res.ok),
                    map((res: HttpResponse<IEvaluationMode[]>) => res.body)
                )
                .subscribe((res: IEvaluationMode[]) => (this.evaluationModes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.evaluationModeService
            .query()
            .pipe(
                filter((res: HttpResponse<IEvaluationMode[]>) => res.ok),
                map((res: HttpResponse<IEvaluationMode[]>) => res.body)
            )
            .subscribe(
                (res: IEvaluationMode[]) => {
                    this.evaluationModes = res;
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
        this.registerChangeInEvaluationModes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEvaluationMode) {
        return item.id;
    }

    registerChangeInEvaluationModes() {
        this.eventSubscriber = this.eventManager.subscribe('evaluationModeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
