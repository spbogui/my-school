import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnrolment } from 'app/shared/model/enrolment.model';

@Component({
    selector: 'jhi-enrolment-detail',
    templateUrl: './enrolment-detail.component.html'
})
export class EnrolmentDetailComponent implements OnInit {
    enrolment: IEnrolment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enrolment }) => {
            this.enrolment = enrolment;
        });
    }

    previousState() {
        window.history.back();
    }
}
