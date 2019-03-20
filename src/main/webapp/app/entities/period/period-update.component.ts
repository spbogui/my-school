import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from './period.service';
import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from 'app/entities/period-type';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';

@Component({
    selector: 'jhi-period-update',
    templateUrl: './period-update.component.html'
})
export class PeriodUpdateComponent implements OnInit {
    period: IPeriod;
    isSaving: boolean;

    periodtypes: IPeriodType[];

    schoolyears: ISchoolYear[];
    startDateDp: any;
    endDateDp: any;
    createdAtDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected periodService: PeriodService,
        protected periodTypeService: PeriodTypeService,
        protected schoolYearService: SchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ period }) => {
            this.period = period;
        });
        this.periodTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeriodType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeriodType[]>) => response.body)
            )
            .subscribe((res: IPeriodType[]) => (this.periodtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.period.id !== undefined) {
            this.subscribeToSaveResponse(this.periodService.update(this.period));
        } else {
            this.subscribeToSaveResponse(this.periodService.create(this.period));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriod>>) {
        result.subscribe((res: HttpResponse<IPeriod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPeriodTypeById(index: number, item: IPeriodType) {
        return item.id;
    }

    trackSchoolYearById(index: number, item: ISchoolYear) {
        return item.id;
    }
}
