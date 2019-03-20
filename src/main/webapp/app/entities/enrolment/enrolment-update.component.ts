import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEnrolment } from 'app/shared/model/enrolment.model';
import { EnrolmentService } from './enrolment.service';
import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from 'app/entities/school-year';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';
import { IClassRoom } from 'app/shared/model/class-room.model';
import { ClassRoomService } from 'app/entities/class-room';
import { IRegimen } from 'app/shared/model/regimen.model';
import { RegimenService } from 'app/entities/regimen';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school';

@Component({
    selector: 'jhi-enrolment-update',
    templateUrl: './enrolment-update.component.html'
})
export class EnrolmentUpdateComponent implements OnInit {
    enrolment: IEnrolment;
    isSaving: boolean;

    schoolyears: ISchoolYear[];

    actors: IActor[];

    classrooms: IClassRoom[];

    regimen: IRegimen[];

    schools: ISchool[];
    enrolmentDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected enrolmentService: EnrolmentService,
        protected schoolYearService: SchoolYearService,
        protected actorService: ActorService,
        protected classRoomService: ClassRoomService,
        protected regimenService: RegimenService,
        protected schoolService: SchoolService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enrolment }) => {
            this.enrolment = enrolment;
        });
        this.schoolYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchoolYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchoolYear[]>) => response.body)
            )
            .subscribe((res: ISchoolYear[]) => (this.schoolyears = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.actorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe((res: IActor[]) => (this.actors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.classRoomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassRoom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassRoom[]>) => response.body)
            )
            .subscribe((res: IClassRoom[]) => (this.classrooms = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.regimenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRegimen[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRegimen[]>) => response.body)
            )
            .subscribe((res: IRegimen[]) => (this.regimen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.schoolService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchool[]>) => response.body)
            )
            .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enrolment.id !== undefined) {
            this.subscribeToSaveResponse(this.enrolmentService.update(this.enrolment));
        } else {
            this.subscribeToSaveResponse(this.enrolmentService.create(this.enrolment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnrolment>>) {
        result.subscribe((res: HttpResponse<IEnrolment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActorById(index: number, item: IActor) {
        return item.id;
    }

    trackClassRoomById(index: number, item: IClassRoom) {
        return item.id;
    }

    trackRegimenById(index: number, item: IRegimen) {
        return item.id;
    }

    trackSchoolById(index: number, item: ISchool) {
        return item.id;
    }
}
