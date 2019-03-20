import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Days } from 'app/shared/model/days.model';
import { DaysService } from './days.service';
import { DaysComponent } from './days.component';
import { DaysDetailComponent } from './days-detail.component';
import { DaysUpdateComponent } from './days-update.component';
import { DaysDeletePopupComponent } from './days-delete-dialog.component';
import { IDays } from 'app/shared/model/days.model';

@Injectable({ providedIn: 'root' })
export class DaysResolve implements Resolve<IDays> {
    constructor(private service: DaysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDays> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Days>) => response.ok),
                map((days: HttpResponse<Days>) => days.body)
            );
        }
        return of(new Days());
    }
}

export const daysRoute: Routes = [
    {
        path: '',
        component: DaysComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.days.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DaysDetailComponent,
        resolve: {
            days: DaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.days.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DaysUpdateComponent,
        resolve: {
            days: DaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.days.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DaysUpdateComponent,
        resolve: {
            days: DaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.days.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const daysPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DaysDeletePopupComponent,
        resolve: {
            days: DaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.days.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
