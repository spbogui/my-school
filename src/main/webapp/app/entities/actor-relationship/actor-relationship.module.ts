import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    ActorRelationshipComponent,
    ActorRelationshipDetailComponent,
    ActorRelationshipUpdateComponent,
    ActorRelationshipDeletePopupComponent,
    ActorRelationshipDeleteDialogComponent,
    actorRelationshipRoute,
    actorRelationshipPopupRoute
} from './';

const ENTITY_STATES = [...actorRelationshipRoute, ...actorRelationshipPopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActorRelationshipComponent,
        ActorRelationshipDetailComponent,
        ActorRelationshipUpdateComponent,
        ActorRelationshipDeleteDialogComponent,
        ActorRelationshipDeletePopupComponent
    ],
    entryComponents: [
        ActorRelationshipComponent,
        ActorRelationshipUpdateComponent,
        ActorRelationshipDeleteDialogComponent,
        ActorRelationshipDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolActorRelationshipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
