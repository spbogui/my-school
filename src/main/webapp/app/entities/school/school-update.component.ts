import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';

@Component({
    selector: 'jhi-school-update',
    templateUrl: './school-update.component.html'
})
export class SchoolUpdateComponent implements OnInit {
    school: ISchool;
    isSaving: boolean;
    openningDateDp: any;

    constructor(protected schoolService: SchoolService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ school }) => {
            this.school = school;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.school.id !== undefined) {
            this.subscribeToSaveResponse(this.schoolService.update(this.school));
        } else {
            this.subscribeToSaveResponse(this.schoolService.create(this.school));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchool>>) {
        result.subscribe((res: HttpResponse<ISchool>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
