import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';

@Component({
    selector: 'jhi-school-school-year-detail',
    templateUrl: './school-school-year-detail.component.html'
})
export class SchoolSchoolYearDetailComponent implements OnInit {
    schoolSchoolYear: ISchoolSchoolYear;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolSchoolYear }) => {
            this.schoolSchoolYear = schoolSchoolYear;
        });
    }

    previousState() {
        window.history.back();
    }
}
