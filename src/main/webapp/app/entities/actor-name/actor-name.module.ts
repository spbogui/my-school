import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    ActorNameComponent,
    ActorNameDetailComponent,
    ActorNameUpdateComponent,
    ActorNameDeletePopupComponent,
    ActorNameDeleteDialogComponent,
    actorNameRoute,
    actorNamePopupRoute
} from './';

const ENTITY_STATES = [...actorNameRoute, ...actorNamePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActorNameComponent,
        ActorNameDetailComponent,
        ActorNameUpdateComponent,
        ActorNameDeleteDialogComponent,
        ActorNameDeletePopupComponent
    ],
    entryComponents: [ActorNameComponent, ActorNameUpdateComponent, ActorNameDeleteDialogComponent, ActorNameDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolActorNameModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
