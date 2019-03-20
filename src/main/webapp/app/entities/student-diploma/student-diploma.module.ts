import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    StudentDiplomaComponent,
    StudentDiplomaDetailComponent,
    StudentDiplomaUpdateComponent,
    StudentDiplomaDeletePopupComponent,
    StudentDiplomaDeleteDialogComponent,
    studentDiplomaRoute,
    studentDiplomaPopupRoute
} from './';

const ENTITY_STATES = [...studentDiplomaRoute, ...studentDiplomaPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentDiplomaComponent,
        StudentDiplomaDetailComponent,
        StudentDiplomaUpdateComponent,
        StudentDiplomaDeleteDialogComponent,
        StudentDiplomaDeletePopupComponent
    ],
    entryComponents: [
        StudentDiplomaComponent,
        StudentDiplomaUpdateComponent,
        StudentDiplomaDeleteDialogComponent,
        StudentDiplomaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolStudentDiplomaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
