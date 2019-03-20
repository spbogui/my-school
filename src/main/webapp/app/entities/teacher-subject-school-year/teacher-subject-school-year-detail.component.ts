import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';

@Component({
    selector: 'jhi-teacher-subject-school-year-detail',
    templateUrl: './teacher-subject-school-year-detail.component.html'
})
export class TeacherSubjectSchoolYearDetailComponent implements OnInit {
    teacherSubjectSchoolYear: ITeacherSubjectSchoolYear;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherSubjectSchoolYear }) => {
            this.teacherSubjectSchoolYear = teacherSubjectSchoolYear;
        });
    }

    previousState() {
        window.history.back();
    }
}
