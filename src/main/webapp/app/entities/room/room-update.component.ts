import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from './room.service';
import { IRoomType } from 'app/shared/model/room-type.model';
import { RoomTypeService } from 'app/entities/room-type';

@Component({
    selector: 'jhi-room-update',
    templateUrl: './room-update.component.html'
})
export class RoomUpdateComponent implements OnInit {
    room: IRoom;
    isSaving: boolean;

    roomtypes: IRoomType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roomService: RoomService,
        protected roomTypeService: RoomTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ room }) => {
            this.room = room;
        });
        this.roomTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoomType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoomType[]>) => response.body)
            )
            .subscribe((res: IRoomType[]) => (this.roomtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.room.id !== undefined) {
            this.subscribeToSaveResponse(this.roomService.update(this.room));
        } else {
            this.subscribeToSaveResponse(this.roomService.create(this.room));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>) {
        result.subscribe((res: HttpResponse<IRoom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRoomTypeById(index: number, item: IRoomType) {
        return item.id;
    }
}
