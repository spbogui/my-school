import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    LeaveHoliDayComponent,
    LeaveHoliDayDetailComponent,
    LeaveHoliDayUpdateComponent,
    LeaveHoliDayDeletePopupComponent,
    LeaveHoliDayDeleteDialogComponent,
    leaveHoliDayRoute,
    leaveHoliDayPopupRoute
} from './';

const ENTITY_STATES = [...leaveHoliDayRoute, ...leaveHoliDayPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveHoliDayComponent,
        LeaveHoliDayDetailComponent,
        LeaveHoliDayUpdateComponent,
        LeaveHoliDayDeleteDialogComponent,
        LeaveHoliDayDeletePopupComponent
    ],
    entryComponents: [
        LeaveHoliDayComponent,
        LeaveHoliDayUpdateComponent,
        LeaveHoliDayDeleteDialogComponent,
        LeaveHoliDayDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolLeaveHoliDayModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
