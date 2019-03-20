import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchool } from 'app/shared/model/school.model';

@Component({
    selector: 'jhi-school-detail',
    templateUrl: './school-detail.component.html'
})
export class SchoolDetailComponent implements OnInit {
    school: ISchool;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ school }) => {
            this.school = school;
        });
    }

    previousState() {
        window.history.back();
    }
}
