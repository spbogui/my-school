import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    RubricAmountComponent,
    RubricAmountDetailComponent,
    RubricAmountUpdateComponent,
    RubricAmountDeletePopupComponent,
    RubricAmountDeleteDialogComponent,
    rubricAmountRoute,
    rubricAmountPopupRoute
} from './';

const ENTITY_STATES = [...rubricAmountRoute, ...rubricAmountPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RubricAmountComponent,
        RubricAmountDetailComponent,
        RubricAmountUpdateComponent,
        RubricAmountDeleteDialogComponent,
        RubricAmountDeletePopupComponent
    ],
    entryComponents: [
        RubricAmountComponent,
        RubricAmountUpdateComponent,
        RubricAmountDeleteDialogComponent,
        RubricAmountDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolRubricAmountModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
