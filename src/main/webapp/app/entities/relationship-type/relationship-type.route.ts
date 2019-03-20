import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RelationshipType } from 'app/shared/model/relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';
import { RelationshipTypeComponent } from './relationship-type.component';
import { RelationshipTypeDetailComponent } from './relationship-type-detail.component';
import { RelationshipTypeUpdateComponent } from './relationship-type-update.component';
import { RelationshipTypeDeletePopupComponent } from './relationship-type-delete-dialog.component';
import { IRelationshipType } from 'app/shared/model/relationship-type.model';

@Injectable({ providedIn: 'root' })
export class RelationshipTypeResolve implements Resolve<IRelationshipType> {
    constructor(private service: RelationshipTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRelationshipType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RelationshipType>) => response.ok),
                map((relationshipType: HttpResponse<RelationshipType>) => relationshipType.body)
            );
        }
        return of(new RelationshipType());
    }
}

export const relationshipTypeRoute: Routes = [
    {
        path: '',
        component: RelationshipTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.relationshipType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RelationshipTypeDetailComponent,
        resolve: {
            relationshipType: RelationshipTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.relationshipType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RelationshipTypeUpdateComponent,
        resolve: {
            relationshipType: RelationshipTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.relationshipType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RelationshipTypeUpdateComponent,
        resolve: {
            relationshipType: RelationshipTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.relationshipType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relationshipTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RelationshipTypeDeletePopupComponent,
        resolve: {
            relationshipType: RelationshipTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.relationshipType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
