import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentMissingSession } from 'app/shared/model/student-missing-session.model';
import { StudentMissingSessionService } from './student-missing-session.service';
import { StudentMissingSessionComponent } from './student-missing-session.component';
import { StudentMissingSessionDetailComponent } from './student-missing-session-detail.component';
import { StudentMissingSessionUpdateComponent } from './student-missing-session-update.component';
import { StudentMissingSessionDeletePopupComponent } from './student-missing-session-delete-dialog.component';
import { IStudentMissingSession } from 'app/shared/model/student-missing-session.model';

@Injectable({ providedIn: 'root' })
export class StudentMissingSessionResolve implements Resolve<IStudentMissingSession> {
    constructor(private service: StudentMissingSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentMissingSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentMissingSession>) => response.ok),
                map((studentMissingSession: HttpResponse<StudentMissingSession>) => studentMissingSession.body)
            );
        }
        return of(new StudentMissingSession());
    }
}

export const studentMissingSessionRoute: Routes = [
    {
        path: '',
        component: StudentMissingSessionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.studentMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentMissingSessionDetailComponent,
        resolve: {
            studentMissingSession: StudentMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentMissingSessionUpdateComponent,
        resolve: {
            studentMissingSession: StudentMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentMissingSessionUpdateComponent,
        resolve: {
            studentMissingSession: StudentMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentMissingSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentMissingSessionDeletePopupComponent,
        resolve: {
            studentMissingSession: StudentMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
