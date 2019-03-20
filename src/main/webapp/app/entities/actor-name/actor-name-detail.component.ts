import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActorName } from 'app/shared/model/actor-name.model';

@Component({
    selector: 'jhi-actor-name-detail',
    templateUrl: './actor-name-detail.component.html'
})
export class ActorNameDetailComponent implements OnInit {
    actorName: IActorName;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorName }) => {
            this.actorName = actorName;
        });
    }

    previousState() {
        window.history.back();
    }
}
