import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    IdentifierTypeComponent,
    IdentifierTypeDetailComponent,
    IdentifierTypeUpdateComponent,
    IdentifierTypeDeletePopupComponent,
    IdentifierTypeDeleteDialogComponent,
    identifierTypeRoute,
    identifierTypePopupRoute
} from './';

const ENTITY_STATES = [...identifierTypeRoute, ...identifierTypePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IdentifierTypeComponent,
        IdentifierTypeDetailComponent,
        IdentifierTypeUpdateComponent,
        IdentifierTypeDeleteDialogComponent,
        IdentifierTypeDeletePopupComponent
    ],
    entryComponents: [
        IdentifierTypeComponent,
        IdentifierTypeUpdateComponent,
        IdentifierTypeDeleteDialogComponent,
        IdentifierTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolIdentifierTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
