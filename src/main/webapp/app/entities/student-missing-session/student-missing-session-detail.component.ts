import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentMissingSession } from 'app/shared/model/student-missing-session.model';

@Component({
    selector: 'jhi-student-missing-session-detail',
    templateUrl: './student-missing-session-detail.component.html'
})
export class StudentMissingSessionDetailComponent implements OnInit {
    studentMissingSession: IStudentMissingSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentMissingSession }) => {
            this.studentMissingSession = studentMissingSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
