import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaffJob } from 'app/shared/model/staff-job.model';

@Component({
    selector: 'jhi-staff-job-detail',
    templateUrl: './staff-job-detail.component.html'
})
export class StaffJobDetailComponent implements OnInit {
    staffJob: IStaffJob;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staffJob }) => {
            this.staffJob = staffJob;
        });
    }

    previousState() {
        window.history.back();
    }
}
