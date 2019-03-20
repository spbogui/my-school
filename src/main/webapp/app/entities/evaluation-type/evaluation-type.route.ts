import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EvaluationType } from 'app/shared/model/evaluation-type.model';
import { EvaluationTypeService } from './evaluation-type.service';
import { EvaluationTypeComponent } from './evaluation-type.component';
import { EvaluationTypeDetailComponent } from './evaluation-type-detail.component';
import { EvaluationTypeUpdateComponent } from './evaluation-type-update.component';
import { EvaluationTypeDeletePopupComponent } from './evaluation-type-delete-dialog.component';
import { IEvaluationType } from 'app/shared/model/evaluation-type.model';

@Injectable({ providedIn: 'root' })
export class EvaluationTypeResolve implements Resolve<IEvaluationType> {
    constructor(private service: EvaluationTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEvaluationType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EvaluationType>) => response.ok),
                map((evaluationType: HttpResponse<EvaluationType>) => evaluationType.body)
            );
        }
        return of(new EvaluationType());
    }
}

export const evaluationTypeRoute: Routes = [
    {
        path: '',
        component: EvaluationTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EvaluationTypeDetailComponent,
        resolve: {
            evaluationType: EvaluationTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EvaluationTypeUpdateComponent,
        resolve: {
            evaluationType: EvaluationTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EvaluationTypeUpdateComponent,
        resolve: {
            evaluationType: EvaluationTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const evaluationTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EvaluationTypeDeletePopupComponent,
        resolve: {
            evaluationType: EvaluationTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
