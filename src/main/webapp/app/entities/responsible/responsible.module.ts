import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    ResponsibleComponent,
    ResponsibleDetailComponent,
    ResponsibleUpdateComponent,
    ResponsibleDeletePopupComponent,
    ResponsibleDeleteDialogComponent,
    responsibleRoute,
    responsiblePopupRoute
} from './';

const ENTITY_STATES = [...responsibleRoute, ...responsiblePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResponsibleComponent,
        ResponsibleDetailComponent,
        ResponsibleUpdateComponent,
        ResponsibleDeleteDialogComponent,
        ResponsibleDeletePopupComponent
    ],
    entryComponents: [ResponsibleComponent, ResponsibleUpdateComponent, ResponsibleDeleteDialogComponent, ResponsibleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolResponsibleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
