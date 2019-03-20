import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClassSession } from 'app/shared/model/class-session.model';
import { ClassSessionService } from './class-session.service';
import { ClassSessionComponent } from './class-session.component';
import { ClassSessionDetailComponent } from './class-session-detail.component';
import { ClassSessionUpdateComponent } from './class-session-update.component';
import { ClassSessionDeletePopupComponent } from './class-session-delete-dialog.component';
import { IClassSession } from 'app/shared/model/class-session.model';

@Injectable({ providedIn: 'root' })
export class ClassSessionResolve implements Resolve<IClassSession> {
    constructor(private service: ClassSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ClassSession>) => response.ok),
                map((classSession: HttpResponse<ClassSession>) => classSession.body)
            );
        }
        return of(new ClassSession());
    }
}

export const classSessionRoute: Routes = [
    {
        path: '',
        component: ClassSessionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.classSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassSessionDetailComponent,
        resolve: {
            classSession: ClassSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassSessionUpdateComponent,
        resolve: {
            classSession: ClassSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassSessionUpdateComponent,
        resolve: {
            classSession: ClassSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassSessionDeletePopupComponent,
        resolve: {
            classSession: ClassSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
