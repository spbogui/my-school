import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentDiploma } from 'app/shared/model/student-diploma.model';

@Component({
    selector: 'jhi-student-diploma-detail',
    templateUrl: './student-diploma-detail.component.html'
})
export class StudentDiplomaDetailComponent implements OnInit {
    studentDiploma: IStudentDiploma;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentDiploma }) => {
            this.studentDiploma = studentDiploma;
        });
    }

    previousState() {
        window.history.back();
    }
}
