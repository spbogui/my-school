import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActorRelationship } from 'app/shared/model/actor-relationship.model';

@Component({
    selector: 'jhi-actor-relationship-detail',
    templateUrl: './actor-relationship-detail.component.html'
})
export class ActorRelationshipDetailComponent implements OnInit {
    actorRelationship: IActorRelationship;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorRelationship }) => {
            this.actorRelationship = actorRelationship;
        });
    }

    previousState() {
        window.history.back();
    }
}
