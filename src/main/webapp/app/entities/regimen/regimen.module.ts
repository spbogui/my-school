import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    RegimenComponent,
    RegimenDetailComponent,
    RegimenUpdateComponent,
    RegimenDeletePopupComponent,
    RegimenDeleteDialogComponent,
    regimenRoute,
    regimenPopupRoute
} from './';

const ENTITY_STATES = [...regimenRoute, ...regimenPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegimenComponent,
        RegimenDetailComponent,
        RegimenUpdateComponent,
        RegimenDeleteDialogComponent,
        RegimenDeletePopupComponent
    ],
    entryComponents: [RegimenComponent, RegimenUpdateComponent, RegimenDeleteDialogComponent, RegimenDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolRegimenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
