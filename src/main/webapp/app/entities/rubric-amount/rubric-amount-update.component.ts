import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRubricAmount } from 'app/shared/model/rubric-amount.model';
import { RubricAmountService } from './rubric-amount.service';
import { IRubric } from 'app/shared/model/rubric.model';
import { RubricService } from 'app/entities/rubric';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';

@Component({
    selector: 'jhi-rubric-amount-update',
    templateUrl: './rubric-amount-update.component.html'
})
export class RubricAmountUpdateComponent implements OnInit {
    rubricAmount: IRubricAmount;
    isSaving: boolean;

    rubrics: IRubric[];

    levels: ILevel[];

    schoolyears: ISchoolYear[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rubricAmountService: RubricAmountService,
        protected rubricService: RubricService,
        protected levelService: LevelService,
        protected schoolYearService: SchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rubricAmount }) => {
            this.rubricAmount = rubricAmount;
        });
        this.rubricService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRubric[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRubric[]>) => response.body)
            )
            .subscribe((res: IRubric[]) => (this.rubrics = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.levelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILevel[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILevel[]>) => response.body)
            )
            .subscribe((res: ILevel[]) => (this.levels = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.rubricAmount.id !== undefined) {
            this.subscribeToSaveResponse(this.rubricAmountService.update(this.rubricAmount));
        } else {
            this.subscribeToSaveResponse(this.rubricAmountService.create(this.rubricAmount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRubricAmount>>) {
        result.subscribe((res: HttpResponse<IRubricAmount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRubricById(index: number, item: IRubric) {
        return item.id;
    }

    trackLevelById(index: number, item: ILevel) {
        return item.id;
    }

    trackSchoolYearById(index: number, item: ISchoolYear) {
        return item.id;
    }
}
