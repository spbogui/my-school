import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EvaluationMode } from 'app/shared/model/evaluation-mode.model';
import { EvaluationModeService } from './evaluation-mode.service';
import { EvaluationModeComponent } from './evaluation-mode.component';
import { EvaluationModeDetailComponent } from './evaluation-mode-detail.component';
import { EvaluationModeUpdateComponent } from './evaluation-mode-update.component';
import { EvaluationModeDeletePopupComponent } from './evaluation-mode-delete-dialog.component';
import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';

@Injectable({ providedIn: 'root' })
export class EvaluationModeResolve implements Resolve<IEvaluationMode> {
    constructor(private service: EvaluationModeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEvaluationMode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EvaluationMode>) => response.ok),
                map((evaluationMode: HttpResponse<EvaluationMode>) => evaluationMode.body)
            );
        }
        return of(new EvaluationMode());
    }
}

export const evaluationModeRoute: Routes = [
    {
        path: '',
        component: EvaluationModeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EvaluationModeDetailComponent,
        resolve: {
            evaluationMode: EvaluationModeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EvaluationModeUpdateComponent,
        resolve: {
            evaluationMode: EvaluationModeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EvaluationModeUpdateComponent,
        resolve: {
            evaluationMode: EvaluationModeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const evaluationModePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EvaluationModeDeletePopupComponent,
        resolve: {
            evaluationMode: EvaluationModeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.evaluationMode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
