import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    StaffJobComponent,
    StaffJobDetailComponent,
    StaffJobUpdateComponent,
    StaffJobDeletePopupComponent,
    StaffJobDeleteDialogComponent,
    staffJobRoute,
    staffJobPopupRoute
} from './';

const ENTITY_STATES = [...staffJobRoute, ...staffJobPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StaffJobComponent,
        StaffJobDetailComponent,
        StaffJobUpdateComponent,
        StaffJobDeleteDialogComponent,
        StaffJobDeletePopupComponent
    ],
    entryComponents: [StaffJobComponent, StaffJobUpdateComponent, StaffJobDeleteDialogComponent, StaffJobDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolStaffJobModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
