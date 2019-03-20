import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Staff } from 'app/shared/model/staff.model';
import { StaffService } from './staff.service';
import { StaffComponent } from './staff.component';
import { StaffDetailComponent } from './staff-detail.component';
import { StaffUpdateComponent } from './staff-update.component';
import { StaffDeletePopupComponent } from './staff-delete-dialog.component';
import { IStaff } from 'app/shared/model/staff.model';

@Injectable({ providedIn: 'root' })
export class StaffResolve implements Resolve<IStaff> {
    constructor(private service: StaffService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStaff> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Staff>) => response.ok),
                map((staff: HttpResponse<Staff>) => staff.body)
            );
        }
        return of(new Staff());
    }
}

export const staffRoute: Routes = [
    {
        path: '',
        component: StaffComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.staff.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StaffDetailComponent,
        resolve: {
            staff: StaffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staff.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StaffUpdateComponent,
        resolve: {
            staff: StaffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staff.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StaffUpdateComponent,
        resolve: {
            staff: StaffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staff.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const staffPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StaffDeletePopupComponent,
        resolve: {
            staff: StaffResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staff.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
