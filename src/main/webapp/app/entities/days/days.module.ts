import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    DaysComponent,
    DaysDetailComponent,
    DaysUpdateComponent,
    DaysDeletePopupComponent,
    DaysDeleteDialogComponent,
    daysRoute,
    daysPopupRoute
} from './';

const ENTITY_STATES = [...daysRoute, ...daysPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DaysComponent, DaysDetailComponent, DaysUpdateComponent, DaysDeleteDialogComponent, DaysDeletePopupComponent],
    entryComponents: [DaysComponent, DaysUpdateComponent, DaysDeleteDialogComponent, DaysDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolDaysModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
