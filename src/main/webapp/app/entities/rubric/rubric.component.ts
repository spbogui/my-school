import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRubric } from 'app/shared/model/rubric.model';
import { AccountService } from 'app/core';
import { RubricService } from './rubric.service';

@Component({
    selector: 'jhi-rubric',
    templateUrl: './rubric.component.html'
})
export class RubricComponent implements OnInit, OnDestroy {
    rubrics: IRubric[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected rubricService: RubricService,
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
            this.rubricService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRubric[]>) => res.ok),
                    map((res: HttpResponse<IRubric[]>) => res.body)
                )
                .subscribe((res: IRubric[]) => (this.rubrics = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.rubricService
            .query()
            .pipe(
                filter((res: HttpResponse<IRubric[]>) => res.ok),
                map((res: HttpResponse<IRubric[]>) => res.body)
            )
            .subscribe(
                (res: IRubric[]) => {
                    this.rubrics = res;
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
        this.registerChangeInRubrics();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRubric) {
        return item.id;
    }

    registerChangeInRubrics() {
        this.eventSubscriber = this.eventManager.subscribe('rubricListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
