import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    RubricComponent,
    RubricDetailComponent,
    RubricUpdateComponent,
    RubricDeletePopupComponent,
    RubricDeleteDialogComponent,
    rubricRoute,
    rubricPopupRoute
} from './';

const ENTITY_STATES = [...rubricRoute, ...rubricPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RubricComponent, RubricDetailComponent, RubricUpdateComponent, RubricDeleteDialogComponent, RubricDeletePopupComponent],
    entryComponents: [RubricComponent, RubricUpdateComponent, RubricDeleteDialogComponent, RubricDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolRubricModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
