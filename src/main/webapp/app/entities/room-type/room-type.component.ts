import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRoomType } from 'app/shared/model/room-type.model';
import { AccountService } from 'app/core';
import { RoomTypeService } from './room-type.service';

@Component({
    selector: 'jhi-room-type',
    templateUrl: './room-type.component.html'
})
export class RoomTypeComponent implements OnInit, OnDestroy {
    roomTypes: IRoomType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected roomTypeService: RoomTypeService,
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
            this.roomTypeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRoomType[]>) => res.ok),
                    map((res: HttpResponse<IRoomType[]>) => res.body)
                )
                .subscribe((res: IRoomType[]) => (this.roomTypes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.roomTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IRoomType[]>) => res.ok),
                map((res: HttpResponse<IRoomType[]>) => res.body)
            )
            .subscribe(
                (res: IRoomType[]) => {
                    this.roomTypes = res;
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
        this.registerChangeInRoomTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRoomType) {
        return item.id;
    }

    registerChangeInRoomTypes() {
        this.eventSubscriber = this.eventManager.subscribe('roomTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
