import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';

@Component({
    selector: 'jhi-actor-identifier-number-detail',
    templateUrl: './actor-identifier-number-detail.component.html'
})
export class ActorIdentifierNumberDetailComponent implements OnInit {
    actorIdentifierNumber: IActorIdentifierNumber;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorIdentifierNumber }) => {
            this.actorIdentifierNumber = actorIdentifierNumber;
        });
    }

    previousState() {
        window.history.back();
    }
}
