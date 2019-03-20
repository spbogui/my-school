import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILeaveHoliDay } from 'app/shared/model/leave-holi-day.model';
import { LeaveHoliDayService } from './leave-holi-day.service';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';

@Component({
    selector: 'jhi-leave-holi-day-update',
    templateUrl: './leave-holi-day-update.component.html'
})
export class LeaveHoliDayUpdateComponent implements OnInit {
    leaveHoliDay: ILeaveHoliDay;
    isSaving: boolean;

    schoolyears: ISchoolYear[];
    startDateDp: any;
    endDateDp: any;
    createdAtDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveHoliDayService: LeaveHoliDayService,
        protected schoolYearService: SchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ leaveHoliDay }) => {
            this.leaveHoliDay = leaveHoliDay;
        });
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
        if (this.leaveHoliDay.id !== undefined) {
            this.subscribeToSaveResponse(this.leaveHoliDayService.update(this.leaveHoliDay));
        } else {
            this.subscribeToSaveResponse(this.leaveHoliDayService.create(this.leaveHoliDay));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveHoliDay>>) {
        result.subscribe((res: HttpResponse<ILeaveHoliDay>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSchoolYearById(index: number, item: ISchoolYear) {
        return item.id;
    }
}
