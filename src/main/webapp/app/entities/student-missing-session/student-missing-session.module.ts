import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    StudentMissingSessionComponent,
    StudentMissingSessionDetailComponent,
    StudentMissingSessionUpdateComponent,
    StudentMissingSessionDeletePopupComponent,
    StudentMissingSessionDeleteDialogComponent,
    studentMissingSessionRoute,
    studentMissingSessionPopupRoute
} from './';

const ENTITY_STATES = [...studentMissingSessionRoute, ...studentMissingSessionPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentMissingSessionComponent,
        StudentMissingSessionDetailComponent,
        StudentMissingSessionUpdateComponent,
        StudentMissingSessionDeleteDialogComponent,
        StudentMissingSessionDeletePopupComponent
    ],
    entryComponents: [
        StudentMissingSessionComponent,
        StudentMissingSessionUpdateComponent,
        StudentMissingSessionDeleteDialogComponent,
        StudentMissingSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolStudentMissingSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
