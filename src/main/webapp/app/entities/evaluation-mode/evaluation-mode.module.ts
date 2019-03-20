import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    EvaluationModeComponent,
    EvaluationModeDetailComponent,
    EvaluationModeUpdateComponent,
    EvaluationModeDeletePopupComponent,
    EvaluationModeDeleteDialogComponent,
    evaluationModeRoute,
    evaluationModePopupRoute
} from './';

const ENTITY_STATES = [...evaluationModeRoute, ...evaluationModePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EvaluationModeComponent,
        EvaluationModeDetailComponent,
        EvaluationModeUpdateComponent,
        EvaluationModeDeleteDialogComponent,
        EvaluationModeDeletePopupComponent
    ],
    entryComponents: [
        EvaluationModeComponent,
        EvaluationModeUpdateComponent,
        EvaluationModeDeleteDialogComponent,
        EvaluationModeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolEvaluationModeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
