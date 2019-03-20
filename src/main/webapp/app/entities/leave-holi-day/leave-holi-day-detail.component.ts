import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveHoliDay } from 'app/shared/model/leave-holi-day.model';

@Component({
    selector: 'jhi-leave-holi-day-detail',
    templateUrl: './leave-holi-day-detail.component.html'
})
export class LeaveHoliDayDetailComponent implements OnInit {
    leaveHoliDay: ILeaveHoliDay;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveHoliDay }) => {
            this.leaveHoliDay = leaveHoliDay;
        });
    }

    previousState() {
        window.history.back();
    }
}
