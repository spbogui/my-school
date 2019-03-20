import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';

@Component({
    selector: 'jhi-student-evaluation-detail',
    templateUrl: './student-evaluation-detail.component.html'
})
export class StudentEvaluationDetailComponent implements OnInit {
    studentEvaluation: IStudentEvaluation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentEvaluation }) => {
            this.studentEvaluation = studentEvaluation;
        });
    }

    previousState() {
        window.history.back();
    }
}
