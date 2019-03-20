import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';

@Component({
    selector: 'jhi-evaluation-mode-detail',
    templateUrl: './evaluation-mode-detail.component.html'
})
export class EvaluationModeDetailComponent implements OnInit {
    evaluationMode: IEvaluationMode;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evaluationMode }) => {
            this.evaluationMode = evaluationMode;
        });
    }

    previousState() {
        window.history.back();
    }
}
