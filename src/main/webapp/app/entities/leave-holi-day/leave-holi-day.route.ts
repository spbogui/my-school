import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LeaveHoliDay } from 'app/shared/model/leave-holi-day.model';
import { LeaveHoliDayService } from './leave-holi-day.service';
import { LeaveHoliDayComponent } from './leave-holi-day.component';
import { LeaveHoliDayDetailComponent } from './leave-holi-day-detail.component';
import { LeaveHoliDayUpdateComponent } from './leave-holi-day-update.component';
import { LeaveHoliDayDeletePopupComponent } from './leave-holi-day-delete-dialog.component';
import { ILeaveHoliDay } from 'app/shared/model/leave-holi-day.model';

@Injectable({ providedIn: 'root' })
export class LeaveHoliDayResolve implements Resolve<ILeaveHoliDay> {
    constructor(private service: LeaveHoliDayService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveHoliDay> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LeaveHoliDay>) => response.ok),
                map((leaveHoliDay: HttpResponse<LeaveHoliDay>) => leaveHoliDay.body)
            );
        }
        return of(new LeaveHoliDay());
    }
}

export const leaveHoliDayRoute: Routes = [
    {
        path: '',
        component: LeaveHoliDayComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.leaveHoliDay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveHoliDayDetailComponent,
        resolve: {
            leaveHoliDay: LeaveHoliDayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.leaveHoliDay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveHoliDayUpdateComponent,
        resolve: {
            leaveHoliDay: LeaveHoliDayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.leaveHoliDay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveHoliDayUpdateComponent,
        resolve: {
            leaveHoliDay: LeaveHoliDayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.leaveHoliDay.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveHoliDayPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveHoliDayDeletePopupComponent,
        resolve: {
            leaveHoliDay: LeaveHoliDayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.leaveHoliDay.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
