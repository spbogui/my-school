import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClassSessionType } from 'app/shared/model/class-session-type.model';
import { ClassSessionTypeService } from './class-session-type.service';
import { ClassSessionTypeComponent } from './class-session-type.component';
import { ClassSessionTypeDetailComponent } from './class-session-type-detail.component';
import { ClassSessionTypeUpdateComponent } from './class-session-type-update.component';
import { ClassSessionTypeDeletePopupComponent } from './class-session-type-delete-dialog.component';
import { IClassSessionType } from 'app/shared/model/class-session-type.model';

@Injectable({ providedIn: 'root' })
export class ClassSessionTypeResolve implements Resolve<IClassSessionType> {
    constructor(private service: ClassSessionTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassSessionType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ClassSessionType>) => response.ok),
                map((classSessionType: HttpResponse<ClassSessionType>) => classSessionType.body)
            );
        }
        return of(new ClassSessionType());
    }
}

export const classSessionTypeRoute: Routes = [
    {
        path: '',
        component: ClassSessionTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSessionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassSessionTypeDetailComponent,
        resolve: {
            classSessionType: ClassSessionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSessionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassSessionTypeUpdateComponent,
        resolve: {
            classSessionType: ClassSessionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSessionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassSessionTypeUpdateComponent,
        resolve: {
            classSessionType: ClassSessionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSessionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classSessionTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassSessionTypeDeletePopupComponent,
        resolve: {
            classSessionType: ClassSessionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classSessionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
