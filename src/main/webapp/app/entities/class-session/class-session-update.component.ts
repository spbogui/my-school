import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IClassSession } from 'app/shared/model/class-session.model';
import { ClassSessionService } from './class-session.service';
import { IClassSessionType } from 'app/shared/model/class-session-type.model';
import { ClassSessionTypeService } from 'app/entities/class-session-type';
import { IProgram } from 'app/shared/model/program.model';
import { ProgramService } from 'app/entities/program';

@Component({
    selector: 'jhi-class-session-update',
    templateUrl: './class-session-update.component.html'
})
export class ClassSessionUpdateComponent implements OnInit {
    classSession: IClassSession;
    isSaving: boolean;

    classsessiontypes: IClassSessionType[];

    programs: IProgram[];
    startHour: string;
    endHour: string;
    createdAtDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected classSessionService: ClassSessionService,
        protected classSessionTypeService: ClassSessionTypeService,
        protected programService: ProgramService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classSession }) => {
            this.classSession = classSession;
            this.startHour = this.classSession.startHour != null ? this.classSession.startHour.format(DATE_TIME_FORMAT) : null;
            this.endHour = this.classSession.endHour != null ? this.classSession.endHour.format(DATE_TIME_FORMAT) : null;
        });
        this.classSessionTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassSessionType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassSessionType[]>) => response.body)
            )
            .subscribe((res: IClassSessionType[]) => (this.classsessiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.programService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProgram[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProgram[]>) => response.body)
            )
            .subscribe((res: IProgram[]) => (this.programs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.classSession.startHour = this.startHour != null ? moment(this.startHour, DATE_TIME_FORMAT) : null;
        this.classSession.endHour = this.endHour != null ? moment(this.endHour, DATE_TIME_FORMAT) : null;
        if (this.classSession.id !== undefined) {
            this.subscribeToSaveResponse(this.classSessionService.update(this.classSession));
        } else {
            this.subscribeToSaveResponse(this.classSessionService.create(this.classSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassSession>>) {
        result.subscribe((res: HttpResponse<IClassSession>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClassSessionTypeById(index: number, item: IClassSessionType) {
        return item.id;
    }

    trackProgramById(index: number, item: IProgram) {
        return item.id;
    }
}
