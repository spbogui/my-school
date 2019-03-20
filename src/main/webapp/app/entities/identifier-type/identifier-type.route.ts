import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IdentifierType } from 'app/shared/model/identifier-type.model';
import { IdentifierTypeService } from './identifier-type.service';
import { IdentifierTypeComponent } from './identifier-type.component';
import { IdentifierTypeDetailComponent } from './identifier-type-detail.component';
import { IdentifierTypeUpdateComponent } from './identifier-type-update.component';
import { IdentifierTypeDeletePopupComponent } from './identifier-type-delete-dialog.component';
import { IIdentifierType } from 'app/shared/model/identifier-type.model';

@Injectable({ providedIn: 'root' })
export class IdentifierTypeResolve implements Resolve<IIdentifierType> {
    constructor(private service: IdentifierTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIdentifierType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IdentifierType>) => response.ok),
                map((identifierType: HttpResponse<IdentifierType>) => identifierType.body)
            );
        }
        return of(new IdentifierType());
    }
}

export const identifierTypeRoute: Routes = [
    {
        path: '',
        component: IdentifierTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.identifierType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IdentifierTypeDetailComponent,
        resolve: {
            identifierType: IdentifierTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.identifierType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IdentifierTypeUpdateComponent,
        resolve: {
            identifierType: IdentifierTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.identifierType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IdentifierTypeUpdateComponent,
        resolve: {
            identifierType: IdentifierTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.identifierType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const identifierTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IdentifierTypeDeletePopupComponent,
        resolve: {
            identifierType: IdentifierTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.identifierType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
