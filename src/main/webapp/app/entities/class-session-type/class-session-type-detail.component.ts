import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassSessionType } from 'app/shared/model/class-session-type.model';

@Component({
    selector: 'jhi-class-session-type-detail',
    templateUrl: './class-session-type-detail.component.html'
})
export class ClassSessionTypeDetailComponent implements OnInit {
    classSessionType: IClassSessionType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classSessionType }) => {
            this.classSessionType = classSessionType;
        });
    }

    previousState() {
        window.history.back();
    }
}
