import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentMissingSession } from 'app/shared/model/student-missing-session.model';
import { StudentMissingSessionService } from './student-missing-session.service';
import { IClassSession } from 'app/shared/model/class-session.model';
import { ClassSessionService } from 'app/entities/class-session';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
    selector: 'jhi-student-missing-session-update',
    templateUrl: './student-missing-session-update.component.html'
})
export class StudentMissingSessionUpdateComponent implements OnInit {
    studentMissingSession: IStudentMissingSession;
    isSaving: boolean;

    classsessions: IClassSession[];

    students: IStudent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentMissingSessionService: StudentMissingSessionService,
        protected classSessionService: ClassSessionService,
        protected studentService: StudentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentMissingSession }) => {
            this.studentMissingSession = studentMissingSession;
        });
        this.classSessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassSession[]>) => response.body)
            )
            .subscribe((res: IClassSession[]) => (this.classsessions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.studentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStudent[]>) => response.body)
            )
            .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentMissingSession.id !== undefined) {
            this.subscribeToSaveResponse(this.studentMissingSessionService.update(this.studentMissingSession));
        } else {
            this.subscribeToSaveResponse(this.studentMissingSessionService.create(this.studentMissingSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentMissingSession>>) {
        result.subscribe(
            (res: HttpResponse<IStudentMissingSession>) => this.onSaveSuccess(),
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

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }
}
