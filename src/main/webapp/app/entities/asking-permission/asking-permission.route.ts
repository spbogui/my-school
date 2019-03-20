import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AskingPermission } from 'app/shared/model/asking-permission.model';
import { AskingPermissionService } from './asking-permission.service';
import { AskingPermissionComponent } from './asking-permission.component';
import { AskingPermissionDetailComponent } from './asking-permission-detail.component';
import { AskingPermissionUpdateComponent } from './asking-permission-update.component';
import { AskingPermissionDeletePopupComponent } from './asking-permission-delete-dialog.component';
import { IAskingPermission } from 'app/shared/model/asking-permission.model';

@Injectable({ providedIn: 'root' })
export class AskingPermissionResolve implements Resolve<IAskingPermission> {
    constructor(private service: AskingPermissionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAskingPermission> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AskingPermission>) => response.ok),
                map((askingPermission: HttpResponse<AskingPermission>) => askingPermission.body)
            );
        }
        return of(new AskingPermission());
    }
}

export const askingPermissionRoute: Routes = [
    {
        path: '',
        component: AskingPermissionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.askingPermission.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AskingPermissionDetailComponent,
        resolve: {
            askingPermission: AskingPermissionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.askingPermission.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AskingPermissionUpdateComponent,
        resolve: {
            askingPermission: AskingPermissionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.askingPermission.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AskingPermissionUpdateComponent,
        resolve: {
            askingPermission: AskingPermissionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.askingPermission.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const askingPermissionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AskingPermissionDeletePopupComponent,
        resolve: {
            askingPermission: AskingPermissionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.askingPermission.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
