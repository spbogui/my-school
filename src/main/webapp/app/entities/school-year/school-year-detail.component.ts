import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolYear } from 'app/shared/model/school-year.model';

@Component({
    selector: 'jhi-school-year-detail',
    templateUrl: './school-year-detail.component.html'
})
export class SchoolYearDetailComponent implements OnInit {
    schoolYear: ISchoolYear;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolYear }) => {
            this.schoolYear = schoolYear;
        });
    }

    previousState() {
        window.history.back();
    }
}
