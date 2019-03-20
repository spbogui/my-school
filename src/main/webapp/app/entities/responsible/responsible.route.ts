import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Responsible } from 'app/shared/model/responsible.model';
import { ResponsibleService } from './responsible.service';
import { ResponsibleComponent } from './responsible.component';
import { ResponsibleDetailComponent } from './responsible-detail.component';
import { ResponsibleUpdateComponent } from './responsible-update.component';
import { ResponsibleDeletePopupComponent } from './responsible-delete-dialog.component';
import { IResponsible } from 'app/shared/model/responsible.model';

@Injectable({ providedIn: 'root' })
export class ResponsibleResolve implements Resolve<IResponsible> {
    constructor(private service: ResponsibleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResponsible> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Responsible>) => response.ok),
                map((responsible: HttpResponse<Responsible>) => responsible.body)
            );
        }
        return of(new Responsible());
    }
}

export const responsibleRoute: Routes = [
    {
        path: '',
        component: ResponsibleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.responsible.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ResponsibleDetailComponent,
        resolve: {
            responsible: ResponsibleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.responsible.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ResponsibleUpdateComponent,
        resolve: {
            responsible: ResponsibleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.responsible.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ResponsibleUpdateComponent,
        resolve: {
            responsible: ResponsibleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.responsible.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const responsiblePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ResponsibleDeletePopupComponent,
        resolve: {
            responsible: ResponsibleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.responsible.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
