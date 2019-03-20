import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from './evaluation.service';
import { IEvaluationType } from 'app/shared/model/evaluation-type.model';
import { EvaluationTypeService } from 'app/entities/evaluation-type';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from 'app/entities/school-school-year';
import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from 'app/entities/period';
import { IClassRoom } from 'app/shared/model/class-room.model';
import { ClassRoomService } from 'app/entities/class-room';

@Component({
    selector: 'jhi-evaluation-update',
    templateUrl: './evaluation-update.component.html'
})
export class EvaluationUpdateComponent implements OnInit {
    evaluation: IEvaluation;
    isSaving: boolean;

    evaluationtypes: IEvaluationType[];

    schoolschoolyears: ISchoolSchoolYear[];

    periods: IPeriod[];

    classrooms: IClassRoom[];
    plannedDateDp: any;
    evaluationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected evaluationService: EvaluationService,
        protected evaluationTypeService: EvaluationTypeService,
        protected schoolSchoolYearService: SchoolSchoolYearService,
        protected periodService: PeriodService,
        protected classRoomService: ClassRoomService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evaluation }) => {
            this.evaluation = evaluation;
        });
        this.evaluationTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEvaluationType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEvaluationType[]>) => response.body)
            )
            .subscribe((res: IEvaluationType[]) => (this.evaluationtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.schoolSchoolYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchoolSchoolYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchoolSchoolYear[]>) => response.body)
            )
            .subscribe((res: ISchoolSchoolYear[]) => (this.schoolschoolyears = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.periodService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeriod[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeriod[]>) => response.body)
            )
            .subscribe((res: IPeriod[]) => (this.periods = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.classRoomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassRoom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassRoom[]>) => response.body)
            )
            .subscribe((res: IClassRoom[]) => (this.classrooms = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.evaluation.id !== undefined) {
            this.subscribeToSaveResponse(this.evaluationService.update(this.evaluation));
        } else {
            this.subscribeToSaveResponse(this.evaluationService.create(this.evaluation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluation>>) {
        result.subscribe((res: HttpResponse<IEvaluation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEvaluationTypeById(index: number, item: IEvaluationType) {
        return item.id;
    }

    trackSchoolSchoolYearById(index: number, item: ISchoolSchoolYear) {
        return item.id;
    }

    trackPeriodById(index: number, item: IPeriod) {
        return item.id;
    }

    trackClassRoomById(index: number, item: IClassRoom) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
