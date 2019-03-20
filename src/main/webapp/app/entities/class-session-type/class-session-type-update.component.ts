import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IClassSessionType } from 'app/shared/model/class-session-type.model';
import { ClassSessionTypeService } from './class-session-type.service';

@Component({
    selector: 'jhi-class-session-type-update',
    templateUrl: './class-session-type-update.component.html'
})
export class ClassSessionTypeUpdateComponent implements OnInit {
    classSessionType: IClassSessionType;
    isSaving: boolean;

    constructor(protected classSessionTypeService: ClassSessionTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classSessionType }) => {
            this.classSessionType = classSessionType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.classSessionType.id !== undefined) {
            this.subscribeToSaveResponse(this.classSessionTypeService.update(this.classSessionType));
        } else {
            this.subscribeToSaveResponse(this.classSessionTypeService.create(this.classSessionType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassSessionType>>) {
        result.subscribe((res: HttpResponse<IClassSessionType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
