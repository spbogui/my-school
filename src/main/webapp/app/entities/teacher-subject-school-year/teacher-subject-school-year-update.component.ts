import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';
import { TeacherSubjectSchoolYearService } from './teacher-subject-school-year.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from 'app/entities/school-school-year';

@Component({
    selector: 'jhi-teacher-subject-school-year-update',
    templateUrl: './teacher-subject-school-year-update.component.html'
})
export class TeacherSubjectSchoolYearUpdateComponent implements OnInit {
    teacherSubjectSchoolYear: ITeacherSubjectSchoolYear;
    isSaving: boolean;

    actors: IActor[];

    teachers: ITeacher[];

    schoolschoolyears: ISchoolSchoolYear[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected teacherSubjectSchoolYearService: TeacherSubjectSchoolYearService,
        protected actorService: ActorService,
        protected teacherService: TeacherService,
        protected schoolSchoolYearService: SchoolSchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ teacherSubjectSchoolYear }) => {
            this.teacherSubjectSchoolYear = teacherSubjectSchoolYear;
        });
        this.actorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe((res: IActor[]) => (this.actors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.teacherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITeacher[]>) => response.body)
            )
            .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.schoolSchoolYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchoolSchoolYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchoolSchoolYear[]>) => response.body)
            )
            .subscribe((res: ISchoolSchoolYear[]) => (this.schoolschoolyears = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.teacherSubjectSchoolYear.id !== undefined) {
            this.subscribeToSaveResponse(this.teacherSubjectSchoolYearService.update(this.teacherSubjectSchoolYear));
        } else {
            this.subscribeToSaveResponse(this.teacherSubjectSchoolYearService.create(this.teacherSubjectSchoolYear));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacherSubjectSchoolYear>>) {
        result.subscribe(
            (res: HttpResponse<ITeacherSubjectSchoolYear>) => this.onSaveSuccess(),
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

    trackActorById(index: number, item: IActor) {
        return item.id;
    }

    trackTeacherById(index: number, item: ITeacher) {
        return item.id;
    }

    trackSchoolSchoolYearById(index: number, item: ISchoolSchoolYear) {
        return item.id;
    }
}
