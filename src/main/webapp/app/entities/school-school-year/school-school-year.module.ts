import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    SchoolSchoolYearComponent,
    SchoolSchoolYearDetailComponent,
    SchoolSchoolYearUpdateComponent,
    SchoolSchoolYearDeletePopupComponent,
    SchoolSchoolYearDeleteDialogComponent,
    schoolSchoolYearRoute,
    schoolSchoolYearPopupRoute
} from './';

const ENTITY_STATES = [...schoolSchoolYearRoute, ...schoolSchoolYearPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SchoolSchoolYearComponent,
        SchoolSchoolYearDetailComponent,
        SchoolSchoolYearUpdateComponent,
        SchoolSchoolYearDeleteDialogComponent,
        SchoolSchoolYearDeletePopupComponent
    ],
    entryComponents: [
        SchoolSchoolYearComponent,
        SchoolSchoolYearUpdateComponent,
        SchoolSchoolYearDeleteDialogComponent,
        SchoolSchoolYearDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolSchoolSchoolYearModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
