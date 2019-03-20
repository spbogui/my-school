import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';

@Component({
    selector: 'jhi-teacher-missing-session-detail',
    templateUrl: './teacher-missing-session-detail.component.html'
})
export class TeacherMissingSessionDetailComponent implements OnInit {
    teacherMissingSession: ITeacherMissingSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherMissingSession }) => {
            this.teacherMissingSession = teacherMissingSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
