import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';
import { TeacherMissingSessionService } from './teacher-missing-session.service';
import { IClassSession } from 'app/shared/model/class-session.model';
import { ClassSessionService } from 'app/entities/class-session';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';

@Component({
    selector: 'jhi-teacher-missing-session-update',
    templateUrl: './teacher-missing-session-update.component.html'
})
export class TeacherMissingSessionUpdateComponent implements OnInit {
    teacherMissingSession: ITeacherMissingSession;
    isSaving: boolean;

    classsessions: IClassSession[];

    teachers: ITeacher[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected teacherMissingSessionService: TeacherMissingSessionService,
        protected classSessionService: ClassSessionService,
        protected teacherService: TeacherService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ teacherMissingSession }) => {
            this.teacherMissingSession = teacherMissingSession;
        });
        this.classSessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassSession[]>) => response.body)
            )
            .subscribe((res: IClassSession[]) => (this.classsessions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.teacherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITeacher[]>) => response.body)
            )
            .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.teacherMissingSession.id !== undefined) {
            this.subscribeToSaveResponse(this.teacherMissingSessionService.update(this.teacherMissingSession));
        } else {
            this.subscribeToSaveResponse(this.teacherMissingSessionService.create(this.teacherMissingSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacherMissingSession>>) {
        result.subscribe(
            (res: HttpResponse<ITeacherMissingSession>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackClassSessionById(index: number, item: IClassSession) {
        return item.id;
    }

    trackTeacherById(index: number, item: ITeacher) {
        return item.id;
    }
}
