import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationType } from 'app/shared/model/evaluation-type.model';

@Component({
    selector: 'jhi-evaluation-type-detail',
    templateUrl: './evaluation-type-detail.component.html'
})
export class EvaluationTypeDetailComponent implements OnInit {
    evaluationType: IEvaluationType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evaluationType }) => {
            this.evaluationType = evaluationType;
        });
    }

    previousState() {
        window.history.back();
    }
}
