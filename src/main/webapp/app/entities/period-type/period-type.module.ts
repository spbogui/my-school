import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    PeriodTypeComponent,
    PeriodTypeDetailComponent,
    PeriodTypeUpdateComponent,
    PeriodTypeDeletePopupComponent,
    PeriodTypeDeleteDialogComponent,
    periodTypeRoute,
    periodTypePopupRoute
} from './';

const ENTITY_STATES = [...periodTypeRoute, ...periodTypePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PeriodTypeComponent,
        PeriodTypeDetailComponent,
        PeriodTypeUpdateComponent,
        PeriodTypeDeleteDialogComponent,
        PeriodTypeDeletePopupComponent
    ],
    entryComponents: [PeriodTypeComponent, PeriodTypeUpdateComponent, PeriodTypeDeleteDialogComponent, PeriodTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolPeriodTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
