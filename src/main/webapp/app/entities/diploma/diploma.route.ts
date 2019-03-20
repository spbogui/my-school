import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Diploma } from 'app/shared/model/diploma.model';
import { DiplomaService } from './diploma.service';
import { DiplomaComponent } from './diploma.component';
import { DiplomaDetailComponent } from './diploma-detail.component';
import { DiplomaUpdateComponent } from './diploma-update.component';
import { DiplomaDeletePopupComponent } from './diploma-delete-dialog.component';
import { IDiploma } from 'app/shared/model/diploma.model';

@Injectable({ providedIn: 'root' })
export class DiplomaResolve implements Resolve<IDiploma> {
    constructor(private service: DiplomaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiploma> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Diploma>) => response.ok),
                map((diploma: HttpResponse<Diploma>) => diploma.body)
            );
        }
        return of(new Diploma());
    }
}

export const diplomaRoute: Routes = [
    {
        path: '',
        component: DiplomaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.diploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DiplomaDetailComponent,
        resolve: {
            diploma: DiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.diploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DiplomaUpdateComponent,
        resolve: {
            diploma: DiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.diploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DiplomaUpdateComponent,
        resolve: {
            diploma: DiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.diploma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diplomaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DiplomaDeletePopupComponent,
        resolve: {
            diploma: DiplomaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.diploma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
