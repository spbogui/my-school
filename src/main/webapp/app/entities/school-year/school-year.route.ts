import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from './school-year.service';
import { SchoolYearComponent } from './school-year.component';
import { SchoolYearDetailComponent } from './school-year-detail.component';
import { SchoolYearUpdateComponent } from './school-year-update.component';
import { SchoolYearDeletePopupComponent } from './school-year-delete-dialog.component';
import { ISchoolYear } from 'app/shared/model/school-year.model';

@Injectable({ providedIn: 'root' })
export class SchoolYearResolve implements Resolve<ISchoolYear> {
    constructor(private service: SchoolYearService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISchoolYear> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SchoolYear>) => response.ok),
                map((schoolYear: HttpResponse<SchoolYear>) => schoolYear.body)
            );
        }
        return of(new SchoolYear());
    }
}

export const schoolYearRoute: Routes = [
    {
        path: '',
        component: SchoolYearComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SchoolYearDetailComponent,
        resolve: {
            schoolYear: SchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SchoolYearUpdateComponent,
        resolve: {
            schoolYear: SchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SchoolYearUpdateComponent,
        resolve: {
            schoolYear: SchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolYearPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SchoolYearDeletePopupComponent,
        resolve: {
            schoolYear: SchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
