import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPermissionAgreement } from 'app/shared/model/permission-agreement.model';
import { PermissionAgreementService } from './permission-agreement.service';
import { IAskingPermission } from 'app/shared/model/asking-permission.model';
import { AskingPermissionService } from 'app/entities/asking-permission';

@Component({
    selector: 'jhi-permission-agreement-update',
    templateUrl: './permission-agreement-update.component.html'
})
export class PermissionAgreementUpdateComponent implements OnInit {
    permissionAgreement: IPermissionAgreement;
    isSaving: boolean;

    askingpermissions: IAskingPermission[];
    allowanceDateDp: any;
    permissionStartDateDp: any;
    permissionEndDateDp: any;
    returnDateDp: any;
    effectiveReturnDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected permissionAgreementService: PermissionAgreementService,
        protected askingPermissionService: AskingPermissionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ permissionAgreement }) => {
            this.permissionAgreement = permissionAgreement;
        });
        this.askingPermissionService
            .query({ filter: 'permissionagreement-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IAskingPermission[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAskingPermission[]>) => response.body)
            )
            .subscribe(
                (res: IAskingPermission[]) => {
                    if (!this.permissionAgreement.askingPermissionId) {
                        this.askingpermissions = res;
                    } else {
                        this.askingPermissionService
                            .find(this.permissionAgreement.askingPermissionId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IAskingPermission>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IAskingPermission>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IAskingPermission) => (this.askingpermissions = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.permissionAgreement.id !== undefined) {
            this.subscribeToSaveResponse(this.permissionAgreementService.update(this.permissionAgreement));
        } else {
            this.subscribeToSaveResponse(this.permissionAgreementService.create(this.permissionAgreement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermissionAgreement>>) {
        result.subscribe((res: HttpResponse<IPermissionAgreement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAskingPermissionById(index: number, item: IAskingPermission) {
        return item.id;
    }
}
