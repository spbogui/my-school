import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    RoomTypeComponent,
    RoomTypeDetailComponent,
    RoomTypeUpdateComponent,
    RoomTypeDeletePopupComponent,
    RoomTypeDeleteDialogComponent,
    roomTypeRoute,
    roomTypePopupRoute
} from './';

const ENTITY_STATES = [...roomTypeRoute, ...roomTypePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoomTypeComponent,
        RoomTypeDetailComponent,
        RoomTypeUpdateComponent,
        RoomTypeDeleteDialogComponent,
        RoomTypeDeletePopupComponent
    ],
    entryComponents: [RoomTypeComponent, RoomTypeUpdateComponent, RoomTypeDeleteDialogComponent, RoomTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolRoomTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
