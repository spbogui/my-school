import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    SalaryComponent,
    SalaryDetailComponent,
    SalaryUpdateComponent,
    SalaryDeletePopupComponent,
    SalaryDeleteDialogComponent,
    salaryRoute,
    salaryPopupRoute
} from './';

const ENTITY_STATES = [...salaryRoute, ...salaryPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SalaryComponent, SalaryDetailComponent, SalaryUpdateComponent, SalaryDeleteDialogComponent, SalaryDeletePopupComponent],
    entryComponents: [SalaryComponent, SalaryUpdateComponent, SalaryDeleteDialogComponent, SalaryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolSalaryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
