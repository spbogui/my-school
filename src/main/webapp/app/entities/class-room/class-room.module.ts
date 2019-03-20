import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    ClassRoomComponent,
    ClassRoomDetailComponent,
    ClassRoomUpdateComponent,
    ClassRoomDeletePopupComponent,
    ClassRoomDeleteDialogComponent,
    classRoomRoute,
    classRoomPopupRoute
} from './';

const ENTITY_STATES = [...classRoomRoute, ...classRoomPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClassRoomComponent,
        ClassRoomDetailComponent,
        ClassRoomUpdateComponent,
        ClassRoomDeleteDialogComponent,
        ClassRoomDeletePopupComponent
    ],
    entryComponents: [ClassRoomComponent, ClassRoomUpdateComponent, ClassRoomDeleteDialogComponent, ClassRoomDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolClassRoomModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
