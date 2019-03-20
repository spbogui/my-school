import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from './school-school-year.service';
import { SchoolSchoolYearComponent } from './school-school-year.component';
import { SchoolSchoolYearDetailComponent } from './school-school-year-detail.component';
import { SchoolSchoolYearUpdateComponent } from './school-school-year-update.component';
import { SchoolSchoolYearDeletePopupComponent } from './school-school-year-delete-dialog.component';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';

@Injectable({ providedIn: 'root' })
export class SchoolSchoolYearResolve implements Resolve<ISchoolSchoolYear> {
    constructor(private service: SchoolSchoolYearService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISchoolSchoolYear> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SchoolSchoolYear>) => response.ok),
                map((schoolSchoolYear: HttpResponse<SchoolSchoolYear>) => schoolSchoolYear.body)
            );
        }
        return of(new SchoolSchoolYear());
    }
}

export const schoolSchoolYearRoute: Routes = [
    {
        path: '',
        component: SchoolSchoolYearComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.schoolSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SchoolSchoolYearDetailComponent,
        resolve: {
            schoolSchoolYear: SchoolSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SchoolSchoolYearUpdateComponent,
        resolve: {
            schoolSchoolYear: SchoolSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SchoolSchoolYearUpdateComponent,
        resolve: {
            schoolSchoolYear: SchoolSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolSchoolYearPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SchoolSchoolYearDeletePopupComponent,
        resolve: {
            schoolSchoolYear: SchoolSchoolYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.schoolSchoolYear.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
