import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRubricAmount } from 'app/shared/model/rubric-amount.model';

@Component({
    selector: 'jhi-rubric-amount-detail',
    templateUrl: './rubric-amount-detail.component.html'
})
export class RubricAmountDetailComponent implements OnInit {
    rubricAmount: IRubricAmount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rubricAmount }) => {
            this.rubricAmount = rubricAmount;
        });
    }

    previousState() {
        window.history.back();
    }
}
