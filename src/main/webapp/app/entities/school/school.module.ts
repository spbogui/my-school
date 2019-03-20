import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    SchoolComponent,
    SchoolDetailComponent,
    SchoolUpdateComponent,
    SchoolDeletePopupComponent,
    SchoolDeleteDialogComponent,
    schoolRoute,
    schoolPopupRoute
} from './';

const ENTITY_STATES = [...schoolRoute, ...schoolPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SchoolComponent, SchoolDetailComponent, SchoolUpdateComponent, SchoolDeleteDialogComponent, SchoolDeletePopupComponent],
    entryComponents: [SchoolComponent, SchoolUpdateComponent, SchoolDeleteDialogComponent, SchoolDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolSchoolModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
