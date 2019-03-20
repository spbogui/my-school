import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    TeacherMissingSessionComponent,
    TeacherMissingSessionDetailComponent,
    TeacherMissingSessionUpdateComponent,
    TeacherMissingSessionDeletePopupComponent,
    TeacherMissingSessionDeleteDialogComponent,
    teacherMissingSessionRoute,
    teacherMissingSessionPopupRoute
} from './';

const ENTITY_STATES = [...teacherMissingSessionRoute, ...teacherMissingSessionPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TeacherMissingSessionComponent,
        TeacherMissingSessionDetailComponent,
        TeacherMissingSessionUpdateComponent,
        TeacherMissingSessionDeleteDialogComponent,
        TeacherMissingSessionDeletePopupComponent
    ],
    entryComponents: [
        TeacherMissingSessionComponent,
        TeacherMissingSessionUpdateComponent,
        TeacherMissingSessionDeleteDialogComponent,
        TeacherMissingSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolTeacherMissingSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
