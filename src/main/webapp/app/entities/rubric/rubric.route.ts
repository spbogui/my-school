import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rubric } from 'app/shared/model/rubric.model';
import { RubricService } from './rubric.service';
import { RubricComponent } from './rubric.component';
import { RubricDetailComponent } from './rubric-detail.component';
import { RubricUpdateComponent } from './rubric-update.component';
import { RubricDeletePopupComponent } from './rubric-delete-dialog.component';
import { IRubric } from 'app/shared/model/rubric.model';

@Injectable({ providedIn: 'root' })
export class RubricResolve implements Resolve<IRubric> {
    constructor(private service: RubricService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRubric> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Rubric>) => response.ok),
                map((rubric: HttpResponse<Rubric>) => rubric.body)
            );
        }
        return of(new Rubric());
    }
}

export const rubricRoute: Routes = [
    {
        path: '',
        component: RubricComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubric.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RubricDetailComponent,
        resolve: {
            rubric: RubricResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubric.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RubricUpdateComponent,
        resolve: {
            rubric: RubricResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubric.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RubricUpdateComponent,
        resolve: {
            rubric: RubricResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubric.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rubricPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RubricDeletePopupComponent,
        resolve: {
            rubric: RubricResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.rubric.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
