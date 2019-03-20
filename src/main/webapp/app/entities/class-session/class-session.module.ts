import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    ClassSessionComponent,
    ClassSessionDetailComponent,
    ClassSessionUpdateComponent,
    ClassSessionDeletePopupComponent,
    ClassSessionDeleteDialogComponent,
    classSessionRoute,
    classSessionPopupRoute
} from './';

const ENTITY_STATES = [...classSessionRoute, ...classSessionPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClassSessionComponent,
        ClassSessionDetailComponent,
        ClassSessionUpdateComponent,
        ClassSessionDeleteDialogComponent,
        ClassSessionDeletePopupComponent
    ],
    entryComponents: [
        ClassSessionComponent,
        ClassSessionUpdateComponent,
        ClassSessionDeleteDialogComponent,
        ClassSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolClassSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
