import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActorRelationship } from 'app/shared/model/actor-relationship.model';
import { ActorRelationshipService } from './actor-relationship.service';
import { ActorRelationshipComponent } from './actor-relationship.component';
import { ActorRelationshipDetailComponent } from './actor-relationship-detail.component';
import { ActorRelationshipUpdateComponent } from './actor-relationship-update.component';
import { ActorRelationshipDeletePopupComponent } from './actor-relationship-delete-dialog.component';
import { IActorRelationship } from 'app/shared/model/actor-relationship.model';

@Injectable({ providedIn: 'root' })
export class ActorRelationshipResolve implements Resolve<IActorRelationship> {
    constructor(private service: ActorRelationshipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IActorRelationship> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActorRelationship>) => response.ok),
                map((actorRelationship: HttpResponse<ActorRelationship>) => actorRelationship.body)
            );
        }
        return of(new ActorRelationship());
    }
}

export const actorRelationshipRoute: Routes = [
    {
        path: '',
        component: ActorRelationshipComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.actorRelationship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ActorRelationshipDetailComponent,
        resolve: {
            actorRelationship: ActorRelationshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorRelationship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ActorRelationshipUpdateComponent,
        resolve: {
            actorRelationship: ActorRelationshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorRelationship.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ActorRelationshipUpdateComponent,
        resolve: {
            actorRelationship: ActorRelationshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorRelationship.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actorRelationshipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ActorRelationshipDeletePopupComponent,
        resolve: {
            actorRelationship: ActorRelationshipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.actorRelationship.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
