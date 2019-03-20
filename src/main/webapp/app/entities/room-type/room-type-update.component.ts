import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRoomType } from 'app/shared/model/room-type.model';
import { RoomTypeService } from './room-type.service';

@Component({
    selector: 'jhi-room-type-update',
    templateUrl: './room-type-update.component.html'
})
export class RoomTypeUpdateComponent implements OnInit {
    roomType: IRoomType;
    isSaving: boolean;

    constructor(protected roomTypeService: RoomTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roomType }) => {
            this.roomType = roomType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roomType.id !== undefined) {
            this.subscribeToSaveResponse(this.roomTypeService.update(this.roomType));
        } else {
            this.subscribeToSaveResponse(this.roomTypeService.create(this.roomType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoomType>>) {
        result.subscribe((res: HttpResponse<IRoomType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
