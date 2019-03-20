import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassSession } from 'app/shared/model/class-session.model';

@Component({
    selector: 'jhi-class-session-detail',
    templateUrl: './class-session-detail.component.html'
})
export class ClassSessionDetailComponent implements OnInit {
    classSession: IClassSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classSession }) => {
            this.classSession = classSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
