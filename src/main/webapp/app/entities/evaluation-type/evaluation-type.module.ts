import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    EvaluationTypeComponent,
    EvaluationTypeDetailComponent,
    EvaluationTypeUpdateComponent,
    EvaluationTypeDeletePopupComponent,
    EvaluationTypeDeleteDialogComponent,
    evaluationTypeRoute,
    evaluationTypePopupRoute
} from './';

const ENTITY_STATES = [...evaluationTypeRoute, ...evaluationTypePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EvaluationTypeComponent,
        EvaluationTypeDetailComponent,
        EvaluationTypeUpdateComponent,
        EvaluationTypeDeleteDialogComponent,
        EvaluationTypeDeletePopupComponent
    ],
    entryComponents: [
        EvaluationTypeComponent,
        EvaluationTypeUpdateComponent,
        EvaluationTypeDeleteDialogComponent,
        EvaluationTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolEvaluationTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
