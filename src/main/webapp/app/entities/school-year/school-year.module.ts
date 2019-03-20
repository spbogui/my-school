import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    SchoolYearComponent,
    SchoolYearDetailComponent,
    SchoolYearUpdateComponent,
    SchoolYearDeletePopupComponent,
    SchoolYearDeleteDialogComponent,
    schoolYearRoute,
    schoolYearPopupRoute
} from './';

const ENTITY_STATES = [...schoolYearRoute, ...schoolYearPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SchoolYearComponent,
        SchoolYearDetailComponent,
        SchoolYearUpdateComponent,
        SchoolYearDeleteDialogComponent,
        SchoolYearDeletePopupComponent
    ],
    entryComponents: [SchoolYearComponent, SchoolYearUpdateComponent, SchoolYearDeleteDialogComponent, SchoolYearDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolSchoolYearModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
