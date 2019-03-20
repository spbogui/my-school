import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    AskingPermissionComponent,
    AskingPermissionDetailComponent,
    AskingPermissionUpdateComponent,
    AskingPermissionDeletePopupComponent,
    AskingPermissionDeleteDialogComponent,
    askingPermissionRoute,
    askingPermissionPopupRoute
} from './';

const ENTITY_STATES = [...askingPermissionRoute, ...askingPermissionPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AskingPermissionComponent,
        AskingPermissionDetailComponent,
        AskingPermissionUpdateComponent,
        AskingPermissionDeleteDialogComponent,
        AskingPermissionDeletePopupComponent
    ],
    entryComponents: [
        AskingPermissionComponent,
        AskingPermissionUpdateComponent,
        AskingPermissionDeleteDialogComponent,
        AskingPermissionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolAskingPermissionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
