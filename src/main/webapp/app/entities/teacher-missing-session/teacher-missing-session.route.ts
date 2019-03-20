import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';
import { TeacherMissingSessionService } from './teacher-missing-session.service';
import { TeacherMissingSessionComponent } from './teacher-missing-session.component';
import { TeacherMissingSessionDetailComponent } from './teacher-missing-session-detail.component';
import { TeacherMissingSessionUpdateComponent } from './teacher-missing-session-update.component';
import { TeacherMissingSessionDeletePopupComponent } from './teacher-missing-session-delete-dialog.component';
import { ITeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';

@Injectable({ providedIn: 'root' })
export class TeacherMissingSessionResolve implements Resolve<ITeacherMissingSession> {
    constructor(private service: TeacherMissingSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITeacherMissingSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TeacherMissingSession>) => response.ok),
                map((teacherMissingSession: HttpResponse<TeacherMissingSession>) => teacherMissingSession.body)
            );
        }
        return of(new TeacherMissingSession());
    }
}

export const teacherMissingSessionRoute: Routes = [
    {
        path: '',
        component: TeacherMissingSessionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.teacherMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TeacherMissingSessionDetailComponent,
        resolve: {
            teacherMissingSession: TeacherMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TeacherMissingSessionUpdateComponent,
        resolve: {
            teacherMissingSession: TeacherMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TeacherMissingSessionUpdateComponent,
        resolve: {
            teacherMissingSession: TeacherMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teacherMissingSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TeacherMissingSessionDeletePopupComponent,
        resolve: {
            teacherMissingSession: TeacherMissingSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.teacherMissingSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
