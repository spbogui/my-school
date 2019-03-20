import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAskingPermission } from 'app/shared/model/asking-permission.model';
import { AskingPermissionService } from './asking-permission.service';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from 'app/entities/school-school-year';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
    selector: 'jhi-asking-permission-update',
    templateUrl: './asking-permission-update.component.html'
})
export class AskingPermissionUpdateComponent implements OnInit {
    askingPermission: IAskingPermission;
    isSaving: boolean;

    schoolschoolyears: ISchoolSchoolYear[];

    students: IStudent[];
    askingDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected askingPermissionService: AskingPermissionService,
        protected schoolSchoolYearService: SchoolSchoolYearService,
        protected studentService: StudentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ askingPermission }) => {
            this.askingPermission = askingPermission;
        });
        this.schoolSchoolYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchoolSchoolYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchoolSchoolYear[]>) => response.body)
            )
            .subscribe((res: ISchoolSchoolYear[]) => (this.schoolschoolyears = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.askingPermission.id !== undefined) {
            this.subscribeToSaveResponse(this.askingPermissionService.update(this.askingPermission));
        } else {
            this.subscribeToSaveResponse(this.askingPermissionService.create(this.askingPermission));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAskingPermission>>) {
        result.subscribe((res: HttpResponse<IAskingPermission>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSchoolSchoolYearById(index: number, item: ISchoolSchoolYear) {
        return item.id;
    }

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }
}
