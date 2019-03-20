import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentDiploma } from 'app/shared/model/student-diploma.model';
import { StudentDiplomaService } from './student-diploma.service';
import { StudentDiplomaComponent } from './student-diploma.component';
import { StudentDiplomaDetailComponent } from './student-diploma-detail.component';
import { StudentDiplomaUpdateComponent } from './student-diploma-update.component';
import { StudentDiplomaDeletePopupComponent } from './student-diploma-delete-dialog.component';
import { IStudentDiploma } from 'app/shared/model/student-diploma.model';

@Injectable({ providedIn: 'root' })
export class StudentDiplomaResolve implements Resolve<IStudentDiploma> {
    constructor(private service: StudentDiplomaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentDiploma> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentDiploma>) => response.ok),
                map((studentDiploma: HttpResponse<StudentDiploma>) => studentDiploma.body)
            );
        }
        return of(new StudentDiploma());
    }
}

export const studentDiplomaRoute: Routes = [
    {
        path: '',
        component: StudentDiplomaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.studentDiploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentDiplomaDetailComponent,
        resolve: {
            studentDiploma: StudentDiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentDiploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentDiplomaUpdateComponent,
        resolve: {
            studentDiploma: StudentDiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentDiploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentDiplomaUpdateComponent,
        resolve: {
            studentDiploma: StudentDiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentDiploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentDiplomaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentDiplomaDeletePopupComponent,
        resolve: {
            studentDiploma: StudentDiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentDiploma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
