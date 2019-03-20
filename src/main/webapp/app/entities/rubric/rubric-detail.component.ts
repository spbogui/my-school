import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRubric } from 'app/shared/model/rubric.model';

@Component({
    selector: 'jhi-rubric-detail',
    templateUrl: './rubric-detail.component.html'
})
export class RubricDetailComponent implements OnInit {
    rubric: IRubric;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rubric }) => {
            this.rubric = rubric;
        });
    }

    previousState() {
        window.history.back();
    }
}
