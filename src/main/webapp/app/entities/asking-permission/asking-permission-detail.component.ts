import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAskingPermission } from 'app/shared/model/asking-permission.model';

@Component({
    selector: 'jhi-asking-permission-detail',
    templateUrl: './asking-permission-detail.component.html'
})
export class AskingPermissionDetailComponent implements OnInit {
    askingPermission: IAskingPermission;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ askingPermission }) => {
            this.askingPermission = askingPermission;
        });
    }

    previousState() {
        window.history.back();
    }
}
