import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    StudentEvaluationComponent,
    StudentEvaluationDetailComponent,
    StudentEvaluationUpdateComponent,
    StudentEvaluationDeletePopupComponent,
    StudentEvaluationDeleteDialogComponent,
    studentEvaluationRoute,
    studentEvaluationPopupRoute
} from './';

const ENTITY_STATES = [...studentEvaluationRoute, ...studentEvaluationPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentEvaluationComponent,
        StudentEvaluationDetailComponent,
        StudentEvaluationUpdateComponent,
        StudentEvaluationDeleteDialogComponent,
        StudentEvaluationDeletePopupComponent
    ],
    entryComponents: [
        StudentEvaluationComponent,
        StudentEvaluationUpdateComponent,
        StudentEvaluationDeleteDialogComponent,
        StudentEvaluationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolStudentEvaluationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
