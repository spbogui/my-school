import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentDiploma } from 'app/shared/model/student-diploma.model';
import { StudentDiplomaService } from './student-diploma.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';
import { IDiploma } from 'app/shared/model/diploma.model';
import { DiplomaService } from 'app/entities/diploma';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from 'app/entities/school-school-year';

@Component({
    selector: 'jhi-student-diploma-update',
    templateUrl: './student-diploma-update.component.html'
})
export class StudentDiplomaUpdateComponent implements OnInit {
    studentDiploma: IStudentDiploma;
    isSaving: boolean;

    students: IStudent[];

    diplomas: IDiploma[];

    schoolschoolyears: ISchoolSchoolYear[];
    graduationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentDiplomaService: StudentDiplomaService,
        protected studentService: StudentService,
        protected diplomaService: DiplomaService,
        protected schoolSchoolYearService: SchoolSchoolYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentDiploma }) => {
            this.studentDiploma = studentDiploma;
        });
        this.studentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStudent[]>) => response.body)
            )
            .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.diplomaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDiploma[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDiploma[]>) => response.body)
            )
            .subscribe((res: IDiploma[]) => (this.diplomas = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.studentDiploma.id !== undefined) {
            this.subscribeToSaveResponse(this.studentDiplomaService.update(this.studentDiploma));
        } else {
            this.subscribeToSaveResponse(this.studentDiplomaService.create(this.studentDiploma));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentDiploma>>) {
        result.subscribe((res: HttpResponse<IStudentDiploma>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }

    trackDiplomaById(index: number, item: IDiploma) {
        return item.id;
    }

    trackSchoolSchoolYearById(index: number, item: ISchoolSchoolYear) {
        return item.id;
    }
}
