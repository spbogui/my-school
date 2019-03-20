import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';
import { TeacherSubjectSchoolYearService } from './teacher-subject-school-year.service';
import { TeacherSubjectSchoolYearComponent } from './teacher-subject-school-year.component';
import { TeacherSubjectSchoolYearDetailComponent } from './teacher-subject-school-year-detail.component';
import { TeacherSubjectSchoolYearUpdateComponent } from './teacher-subject-school-year-update.component';
import { TeacherSubjectSchoolYearDeletePopupComponent } from './teacher-subject-school-year-delete-dialog.component';
import { ITeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';

@Injectable({ providedIn: 'root' })
export class TeacherSubjectSchoolYearResolve implements Resolve<ITeacherSubjectSchoolYear> {
    constructor(private service: TeacherSubjectSchoolYearService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITeacherSubjectSchoolYear> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TeacherSubjectSchoolYear>) => response.ok),
                map((teacherSubjectSchoolYear: HttpResponse<TeacherSubjectSchoolYear>) => teacherSubjectSchoolYear.body)
            );
        }
        return of(new TeacherSubjectSchoolYear());
    }
}

export const teacherSubjectSchoolYearRoute: Routes = [
    {
        path: '',
        component: TeacherSubjectSchoolYearComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.teacherSubjectSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TeacherSubjectSchoolYearDetailComponent,
        resolve: {
            teacherSubjectSchoolYear: TeacherSubjectSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherSubjectSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TeacherSubjectSchoolYearUpdateComponent,
        resolve: {
            teacherSubjectSchoolYear: TeacherSubjectSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherSubjectSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TeacherSubjectSchoolYearUpdateComponent,
        resolve: {
            teacherSubjectSchoolYear: TeacherSubjectSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherSubjectSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teacherSubjectSchoolYearPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TeacherSubjectSchoolYearDeletePopupComponent,
        resolve: {
            teacherSubjectSchoolYear: TeacherSubjectSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherSubjectSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
