import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActorName } from 'app/shared/model/actor-name.model';
import { ActorNameService } from './actor-name.service';
import { ActorNameComponent } from './actor-name.component';
import { ActorNameDetailComponent } from './actor-name-detail.component';
import { ActorNameUpdateComponent } from './actor-name-update.component';
import { ActorNameDeletePopupComponent } from './actor-name-delete-dialog.component';
import { IActorName } from 'app/shared/model/actor-name.model';

@Injectable({ providedIn: 'root' })
export class ActorNameResolve implements Resolve<IActorName> {
    constructor(private service: ActorNameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IActorName> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActorName>) => response.ok),
                map((actorName: HttpResponse<ActorName>) => actorName.body)
            );
        }
        return of(new ActorName());
    }
}

export const actorNameRoute: Routes = [
    {
        path: '',
        component: ActorNameComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.actorName.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ActorNameDetailComponent,
        resolve: {
            actorName: ActorNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorName.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ActorNameUpdateComponent,
        resolve: {
            actorName: ActorNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorName.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ActorNameUpdateComponent,
        resolve: {
            actorName: ActorNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorName.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actorNamePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ActorNameDeletePopupComponent,
        resolve: {
            actorName: ActorNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorName.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
