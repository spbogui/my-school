import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PermissionAgreement } from 'app/shared/model/permission-agreement.model';
import { PermissionAgreementService } from './permission-agreement.service';
import { PermissionAgreementComponent } from './permission-agreement.component';
import { PermissionAgreementDetailComponent } from './permission-agreement-detail.component';
import { PermissionAgreementUpdateComponent } from './permission-agreement-update.component';
import { PermissionAgreementDeletePopupComponent } from './permission-agreement-delete-dialog.component';
import { IPermissionAgreement } from 'app/shared/model/permission-agreement.model';

@Injectable({ providedIn: 'root' })
export class PermissionAgreementResolve implements Resolve<IPermissionAgreement> {
    constructor(private service: PermissionAgreementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPermissionAgreement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PermissionAgreement>) => response.ok),
                map((permissionAgreement: HttpResponse<PermissionAgreement>) => permissionAgreement.body)
            );
        }
        return of(new PermissionAgreement());
    }
}

export const permissionAgreementRoute: Routes = [
    {
        path: '',
        component: PermissionAgreementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.permissionAgreement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PermissionAgreementDetailComponent,
        resolve: {
            permissionAgreement: PermissionAgreementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.permissionAgreement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PermissionAgreementUpdateComponent,
        resolve: {
            permissionAgreement: PermissionAgreementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.permissionAgreement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PermissionAgreementUpdateComponent,
        resolve: {
            permissionAgreement: PermissionAgreementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.permissionAgreement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const permissionAgreementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PermissionAgreementDeletePopupComponent,
        resolve: {
            permissionAgreement: PermissionAgreementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.permissionAgreement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
