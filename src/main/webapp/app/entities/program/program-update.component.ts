import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProgram } from 'app/shared/model/program.model';
import { ProgramService } from './program.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';
import { IClassRoom } from 'app/shared/model/class-room.model';
import { ClassRoomService } from 'app/entities/class-room';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room';
import { IDays } from 'app/shared/model/days.model';
import { DaysService } from 'app/entities/days';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';

@Component({
    selector: 'jhi-program-update',
    templateUrl: './program-update.component.html'
})
export class ProgramUpdateComponent implements OnInit {
    program: IProgram;
    isSaving: boolean;

    subjects: ISubject[];

    classrooms: IClassRoom[];

    rooms: IRoom[];

    days: IDays[];

    schoolyears: ISchoolYear[];
    startHour: string;
    endHour: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected programService: ProgramService,
        protected subjectService: SubjectService,
        protected classRoomService: ClassRoomService,
        protected roomService: RoomService,
        protected daysService: DaysService,
        protected schoolYearService: SchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ program }) => {
            this.program = program;
            this.startHour = this.program.startHour != null ? this.program.startHour.format(DATE_TIME_FORMAT) : null;
            this.endHour = this.program.endHour != null ? this.program.endHour.format(DATE_TIME_FORMAT) : null;
        });
        this.subjectService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISubject[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISubject[]>) => response.body)
            )
            .subscribe((res: ISubject[]) => (this.subjects = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.classRoomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassRoom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassRoom[]>) => response.body)
            )
            .subscribe((res: IClassRoom[]) => (this.classrooms = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.roomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoom[]>) => response.body)
            )
            .subscribe((res: IRoom[]) => (this.rooms = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.daysService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDays[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDays[]>) => response.body)
            )
            .subscribe((res: IDays[]) => (this.days = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.program.startHour = this.startHour != null ? moment(this.startHour, DATE_TIME_FORMAT) : null;
        this.program.endHour = this.endHour != null ? moment(this.endHour, DATE_TIME_FORMAT) : null;
        if (this.program.id !== undefined) {
            this.subscribeToSaveResponse(this.programService.update(this.program));
        } else {
            this.subscribeToSaveResponse(this.programService.create(this.program));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgram>>) {
        result.subscribe((res: HttpResponse<IProgram>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSubjectById(index: number, item: ISubject) {
        return item.id;
    }

    trackClassRoomById(index: number, item: IClassRoom) {
        return item.id;
    }

    trackRoomById(index: number, item: IRoom) {
        return item.id;
    }

    trackDaysById(index: number, item: IDays) {
        return item.id;
    }

    trackSchoolYearById(index: number, item: ISchoolYear) {
        return item.id;
    }
}
