import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    TeacherComponent,
    TeacherDetailComponent,
    TeacherUpdateComponent,
    TeacherDeletePopupComponent,
    TeacherDeleteDialogComponent,
    teacherRoute,
    teacherPopupRoute
} from './';

const ENTITY_STATES = [...teacherRoute, ...teacherPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TeacherComponent,
        TeacherDetailComponent,
        TeacherUpdateComponent,
        TeacherDeleteDialogComponent,
        TeacherDeletePopupComponent
    ],
    entryComponents: [TeacherComponent, TeacherUpdateComponent, TeacherDeleteDialogComponent, TeacherDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolTeacherModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
