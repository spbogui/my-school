import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from './school-school-year.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';

@Component({
    selector: 'jhi-school-school-year-update',
    templateUrl: './school-school-year-update.component.html'
})
export class SchoolSchoolYearUpdateComponent implements OnInit {
    schoolSchoolYear: ISchoolSchoolYear;
    isSaving: boolean;

    schools: ISchool[];

    schoolyears: ISchoolYear[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected schoolSchoolYearService: SchoolSchoolYearService,
        protected schoolService: SchoolService,
        protected schoolYearService: SchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ schoolSchoolYear }) => {
            this.schoolSchoolYear = schoolSchoolYear;
        });
        this.schoolService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchool[]>) => response.body)
            )
            .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.schoolYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchoolYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchoolYear[]>) => response.body)
            )
            .subscribe((res: ISchoolYear[]) => (this.schoolyears = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.schoolSchoolYear.id !== undefined) {
            this.subscribeToSaveResponse(this.schoolSchoolYearService.update(this.schoolSchoolYear));
        } else {
            this.subscribeToSaveResponse(this.schoolSchoolYearService.create(this.schoolSchoolYear));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolSchoolYear>>) {
        result.subscribe((res: HttpResponse<ISchoolSchoolYear>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSchoolById(index: number, item: ISchool) {
        return item.id;
    }

    trackSchoolYearById(index: number, item: ISchoolYear) {
        return item.id;
    }
}
