import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelationshipType } from 'app/shared/model/relationship-type.model';

@Component({
    selector: 'jhi-relationship-type-detail',
    templateUrl: './relationship-type-detail.component.html'
})
export class RelationshipTypeDetailComponent implements OnInit {
    relationshipType: IRelationshipType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ relationshipType }) => {
            this.relationshipType = relationshipType;
        });
    }

    previousState() {
        window.history.back();
    }
}
