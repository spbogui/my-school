import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Enrolment } from 'app/shared/model/enrolment.model';
import { EnrolmentService } from './enrolment.service';
import { EnrolmentComponent } from './enrolment.component';
import { EnrolmentDetailComponent } from './enrolment-detail.component';
import { EnrolmentUpdateComponent } from './enrolment-update.component';
import { EnrolmentDeletePopupComponent } from './enrolment-delete-dialog.component';
import { IEnrolment } from 'app/shared/model/enrolment.model';

@Injectable({ providedIn: 'root' })
export class EnrolmentResolve implements Resolve<IEnrolment> {
    constructor(private service: EnrolmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEnrolment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Enrolment>) => response.ok),
                map((enrolment: HttpResponse<Enrolment>) => enrolment.body)
            );
        }
        return of(new Enrolment());
    }
}

export const enrolmentRoute: Routes = [
    {
        path: '',
        component: EnrolmentComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.enrolment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EnrolmentDetailComponent,
        resolve: {
            enrolment: EnrolmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.enrolment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EnrolmentUpdateComponent,
        resolve: {
            enrolment: EnrolmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.enrolment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EnrolmentUpdateComponent,
        resolve: {
            enrolment: EnrolmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.enrolment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enrolmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EnrolmentDeletePopupComponent,
        resolve: {
            enrolment: EnrolmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.enrolment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
