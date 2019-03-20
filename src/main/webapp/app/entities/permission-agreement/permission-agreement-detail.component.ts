import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPermissionAgreement } from 'app/shared/model/permission-agreement.model';

@Component({
    selector: 'jhi-permission-agreement-detail',
    templateUrl: './permission-agreement-detail.component.html'
})
export class PermissionAgreementDetailComponent implements OnInit {
    permissionAgreement: IPermissionAgreement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ permissionAgreement }) => {
            this.permissionAgreement = permissionAgreement;
        });
    }

    previousState() {
        window.history.back();
    }
}
