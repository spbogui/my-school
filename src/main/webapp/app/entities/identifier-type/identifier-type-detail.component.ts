import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdentifierType } from 'app/shared/model/identifier-type.model';

@Component({
    selector: 'jhi-identifier-type-detail',
    templateUrl: './identifier-type-detail.component.html'
})
export class IdentifierTypeDetailComponent implements OnInit {
    identifierType: IIdentifierType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ identifierType }) => {
            this.identifierType = identifierType;
        });
    }

    previousState() {
        window.history.back();
    }
}
