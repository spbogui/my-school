import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiploma } from 'app/shared/model/diploma.model';

@Component({
    selector: 'jhi-diploma-detail',
    templateUrl: './diploma-detail.component.html'
})
export class DiplomaDetailComponent implements OnInit {
    diploma: IDiploma;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diploma }) => {
            this.diploma = diploma;
        });
    }

    previousState() {
        window.history.back();
    }
}
