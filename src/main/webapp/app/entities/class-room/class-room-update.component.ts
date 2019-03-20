import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IClassRoom } from 'app/shared/model/class-room.model';
import { ClassRoomService } from './class-room.service';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';

@Component({
    selector: 'jhi-class-room-update',
    templateUrl: './class-room-update.component.html'
})
export class ClassRoomUpdateComponent implements OnInit {
    classRoom: IClassRoom;
    isSaving: boolean;

    levels: ILevel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected classRoomService: ClassRoomService,
        protected levelService: LevelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classRoom }) => {
            this.classRoom = classRoom;
        });
        this.levelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILevel[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILevel[]>) => response.body)
            )
            .subscribe((res: ILevel[]) => (this.levels = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.classRoom.id !== undefined) {
            this.subscribeToSaveResponse(this.classRoomService.update(this.classRoom));
        } else {
            this.subscribeToSaveResponse(this.classRoomService.create(this.classRoom));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassRoom>>) {
        result.subscribe((res: HttpResponse<IClassRoom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLevelById(index: number, item: ILevel) {
        return item.id;
    }
}
