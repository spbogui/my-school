import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentEvaluation } from 'app/shared/model/student-evaluation.model';
import { StudentEvaluationService } from './student-evaluation.service';
import { StudentEvaluationComponent } from './student-evaluation.component';
import { StudentEvaluationDetailComponent } from './student-evaluation-detail.component';
import { StudentEvaluationUpdateComponent } from './student-evaluation-update.component';
import { StudentEvaluationDeletePopupComponent } from './student-evaluation-delete-dialog.component';
import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';

@Injectable({ providedIn: 'root' })
export class StudentEvaluationResolve implements Resolve<IStudentEvaluation> {
    constructor(private service: StudentEvaluationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentEvaluation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentEvaluation>) => response.ok),
                map((studentEvaluation: HttpResponse<StudentEvaluation>) => studentEvaluation.body)
            );
        }
        return of(new StudentEvaluation());
    }
}

export const studentEvaluationRoute: Routes = [
    {
        path: '',
        component: StudentEvaluationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.studentEvaluation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentEvaluationDetailComponent,
        resolve: {
            studentEvaluation: StudentEvaluationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentEvaluation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentEvaluationUpdateComponent,
        resolve: {
            studentEvaluation: StudentEvaluationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentEvaluation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentEvaluationUpdateComponent,
        resolve: {
            studentEvaluation: StudentEvaluationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentEvaluation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentEvaluationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentEvaluationDeletePopupComponent,
        resolve: {
            studentEvaluation: StudentEvaluationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.studentEvaluation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
