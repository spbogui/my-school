import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    DiplomaComponent,
    DiplomaDetailComponent,
    DiplomaUpdateComponent,
    DiplomaDeletePopupComponent,
    DiplomaDeleteDialogComponent,
    diplomaRoute,
    diplomaPopupRoute
} from './';

const ENTITY_STATES = [...diplomaRoute, ...diplomaPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiplomaComponent,
        DiplomaDetailComponent,
        DiplomaUpdateComponent,
        DiplomaDeleteDialogComponent,
        DiplomaDeletePopupComponent
    ],
    entryComponents: [DiplomaComponent, DiplomaUpdateComponent, DiplomaDeleteDialogComponent, DiplomaDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolDiplomaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
