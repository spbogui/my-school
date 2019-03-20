import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RubricAmount } from 'app/shared/model/rubric-amount.model';
import { RubricAmountService } from './rubric-amount.service';
import { RubricAmountComponent } from './rubric-amount.component';
import { RubricAmountDetailComponent } from './rubric-amount-detail.component';
import { RubricAmountUpdateComponent } from './rubric-amount-update.component';
import { RubricAmountDeletePopupComponent } from './rubric-amount-delete-dialog.component';
import { IRubricAmount } from 'app/shared/model/rubric-amount.model';

@Injectable({ providedIn: 'root' })
export class RubricAmountResolve implements Resolve<IRubricAmount> {
    constructor(private service: RubricAmountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRubricAmount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RubricAmount>) => response.ok),
                map((rubricAmount: HttpResponse<RubricAmount>) => rubricAmount.body)
            );
        }
        return of(new RubricAmount());
    }
}

export const rubricAmountRoute: Routes = [
    {
        path: '',
        component: RubricAmountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.rubricAmount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RubricAmountDetailComponent,
        resolve: {
            rubricAmount: RubricAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubricAmount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RubricAmountUpdateComponent,
        resolve: {
            rubricAmount: RubricAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubricAmount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RubricAmountUpdateComponent,
        resolve: {
            rubricAmount: RubricAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubricAmount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rubricAmountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RubricAmountDeletePopupComponent,
        resolve: {
            rubricAmount: RubricAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubricAmount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
