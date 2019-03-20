import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClassRoom } from 'app/shared/model/class-room.model';
import { ClassRoomService } from './class-room.service';
import { ClassRoomComponent } from './class-room.component';
import { ClassRoomDetailComponent } from './class-room-detail.component';
import { ClassRoomUpdateComponent } from './class-room-update.component';
import { ClassRoomDeletePopupComponent } from './class-room-delete-dialog.component';
import { IClassRoom } from 'app/shared/model/class-room.model';

@Injectable({ providedIn: 'root' })
export class ClassRoomResolve implements Resolve<IClassRoom> {
    constructor(private service: ClassRoomService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassRoom> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ClassRoom>) => response.ok),
                map((classRoom: HttpResponse<ClassRoom>) => classRoom.body)
            );
        }
        return of(new ClassRoom());
    }
}

export const classRoomRoute: Routes = [
    {
        path: '',
        component: ClassRoomComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'mySchoolApp.classRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassRoomDetailComponent,
        resolve: {
            classRoom: ClassRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassRoomUpdateComponent,
        resolve: {
            classRoom: ClassRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassRoomUpdateComponent,
        resolve: {
            classRoom: ClassRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classRoomPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassRoomDeletePopupComponent,
        resolve: {
            classRoom: ClassRoomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mySchoolApp.classRoom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
