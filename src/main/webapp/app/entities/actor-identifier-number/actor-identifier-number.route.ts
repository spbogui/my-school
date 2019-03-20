import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';
import { ActorIdentifierNumberService } from './actor-identifier-number.service';
import { ActorIdentifierNumberComponent } from './actor-identifier-number.component';
import { ActorIdentifierNumberDetailComponent } from './actor-identifier-number-detail.component';
import { ActorIdentifierNumberUpdateComponent } from './actor-identifier-number-update.component';
import { ActorIdentifierNumberDeletePopupComponent } from './actor-identifier-number-delete-dialog.component';
import { IActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';

@Injectable({ providedIn: 'root' })
export class ActorIdentifierNumberResolve implements Resolve<IActorIdentifierNumber> {
    constructor(private service: ActorIdentifierNumberService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IActorIdentifierNumber> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActorIdentifierNumber>) => response.ok),
                map((actorIdentifierNumber: HttpResponse<ActorIdentifierNumber>) => actorIdentifierNumber.body)
            );
        }
        return of(new ActorIdentifierNumber());
    }
}

export const actorIdentifierNumberRoute: Routes = [
    {
        path: '',
        component: ActorIdentifierNumberComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.actorIdentifierNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ActorIdentifierNumberDetailComponent,
        resolve: {
            actorIdentifierNumber: ActorIdentifierNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorIdentifierNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ActorIdentifierNumberUpdateComponent,
        resolve: {
            actorIdentifierNumber: ActorIdentifierNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorIdentifierNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ActorIdentifierNumberUpdateComponent,
        resolve: {
            actorIdentifierNumber: ActorIdentifierNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorIdentifierNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actorIdentifierNumberPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ActorIdentifierNumberDeletePopupComponent,
        resolve: {
            actorIdentifierNumber: ActorIdentifierNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorIdentifierNumber.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
