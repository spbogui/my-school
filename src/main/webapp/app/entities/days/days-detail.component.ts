import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDays } from 'app/shared/model/days.model';

@Component({
    selector: 'jhi-days-detail',
    templateUrl: './days-detail.component.html'
})
export class DaysDetailComponent implements OnInit {
    days: IDays;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ days }) => {
            this.days = days;
        });
    }

    previousState() {
        window.history.back();
    }
}
