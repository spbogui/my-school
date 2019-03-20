import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoomType } from 'app/shared/model/room-type.model';
import { RoomTypeService } from './room-type.service';
import { RoomTypeComponent } from './room-type.component';
import { RoomTypeDetailComponent } from './room-type-detail.component';
import { RoomTypeUpdateComponent } from './room-type-update.component';
import { RoomTypeDeletePopupComponent } from './room-type-delete-dialog.component';
import { IRoomType } from 'app/shared/model/room-type.model';

@Injectable({ providedIn: 'root' })
export class RoomTypeResolve implements Resolve<IRoomType> {
    constructor(private service: RoomTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoomType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoomType>) => response.ok),
                map((roomType: HttpResponse<RoomType>) => roomType.body)
            );
        }
        return of(new RoomType());
    }
}

export const roomTypeRoute: Routes = [
    {
        path: '',
        component: RoomTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.roomType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoomTypeDetailComponent,
        resolve: {
            roomType: RoomTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.roomType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoomTypeUpdateComponent,
        resolve: {
            roomType: RoomTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.roomType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoomTypeUpdateComponent,
        resolve: {
            roomType: RoomTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.roomType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoomTypeDeletePopupComponent,
        resolve: {
            roomType: RoomTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.roomType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
