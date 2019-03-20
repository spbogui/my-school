import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    EnrolmentComponent,
    EnrolmentDetailComponent,
    EnrolmentUpdateComponent,
    EnrolmentDeletePopupComponent,
    EnrolmentDeleteDialogComponent,
    enrolmentRoute,
    enrolmentPopupRoute
} from './';

const ENTITY_STATES = [...enrolmentRoute, ...enrolmentPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnrolmentComponent,
        EnrolmentDetailComponent,
        EnrolmentUpdateComponent,
        EnrolmentDeleteDialogComponent,
        EnrolmentDeletePopupComponent
    ],
    entryComponents: [EnrolmentComponent, EnrolmentUpdateComponent, EnrolmentDeleteDialogComponent, EnrolmentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolEnrolmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
