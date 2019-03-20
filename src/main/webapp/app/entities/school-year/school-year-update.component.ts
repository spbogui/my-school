import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from './school-year.service';

@Component({
    selector: 'jhi-school-year-update',
    templateUrl: './school-year-update.component.html'
})
export class SchoolYearUpdateComponent implements OnInit {
    schoolYear: ISchoolYear;
    isSaving: boolean;
    courseStartDateDp: any;
    courseEndDateDp: any;
    startDateDp: any;
    endDateDp: any;

    constructor(protected schoolYearService: SchoolYearService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ schoolYear }) => {
            this.schoolYear = schoolYear;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.schoolYear.id !== undefined) {
            this.subscribeToSaveResponse(this.schoolYearService.update(this.schoolYear));
        } else {
            this.subscribeToSaveResponse(this.schoolYearService.create(this.schoolYear));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolYear>>) {
        result.subscribe((res: HttpResponse<ISchoolYear>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
