import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MySchoolSharedModule } from 'app/shared';
import {
    RelationshipTypeComponent,
    RelationshipTypeDetailComponent,
    RelationshipTypeUpdateComponent,
    RelationshipTypeDeletePopupComponent,
    RelationshipTypeDeleteDialogComponent,
    relationshipTypeRoute,
    relationshipTypePopupRoute
} from './';

const ENTITY_STATES = [...relationshipTypeRoute, ...relationshipTypePopupRoute];

@NgModule({
    imports: [MySchoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RelationshipTypeComponent,
        RelationshipTypeDetailComponent,
        RelationshipTypeUpdateComponent,
        RelationshipTypeDeleteDialogComponent,
        RelationshipTypeDeletePopupComponent
    ],
    entryComponents: [
        RelationshipTypeComponent,
        RelationshipTypeUpdateComponent,
        RelationshipTypeDeleteDialogComponent,
        RelationshipTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolRelationshipTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
