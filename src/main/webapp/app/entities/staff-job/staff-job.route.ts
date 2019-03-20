import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StaffJob } from 'app/shared/model/staff-job.model';
import { StaffJobService } from './staff-job.service';
import { StaffJobComponent } from './staff-job.component';
import { StaffJobDetailComponent } from './staff-job-detail.component';
import { StaffJobUpdateComponent } from './staff-job-update.component';
import { StaffJobDeletePopupComponent } from './staff-job-delete-dialog.component';
import { IStaffJob } from 'app/shared/model/staff-job.model';

@Injectable({ providedIn: 'root' })
export class StaffJobResolve implements Resolve<IStaffJob> {
    constructor(private service: StaffJobService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStaffJob> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StaffJob>) => response.ok),
                map((staffJob: HttpResponse<StaffJob>) => staffJob.body)
            );
        }
        return of(new StaffJob());
    }
}

export const staffJobRoute: Routes = [
    {
        path: '',
        component: StaffJobComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.staffJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StaffJobDetailComponent,
        resolve: {
            staffJob: StaffJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staffJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StaffJobUpdateComponent,
        resolve: {
            staffJob: StaffJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staffJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StaffJobUpdateComponent,
        resolve: {
            staffJob: StaffJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staffJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const staffJobPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StaffJobDeletePopupComponent,
        resolve: {
            staffJob: StaffJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.staffJob.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
