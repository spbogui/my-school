import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    CycleComponent,
    CycleDetailComponent,
    CycleUpdateComponent,
    CycleDeletePopupComponent,
    CycleDeleteDialogComponent,
    cycleRoute,
    cyclePopupRoute
} from './';

const ENTITY_STATES = [...cycleRoute, ...cyclePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CycleComponent, CycleDetailComponent, CycleUpdateComponent, CycleDeleteDialogComponent, CycleDeletePopupComponent],
    entryComponents: [CycleComponent, CycleUpdateComponent, CycleDeleteDialogComponent, CycleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolCycleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
