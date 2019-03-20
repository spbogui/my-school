import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPermissionAgreement } from 'app/shared/model/permission-agreement.model';
import { PermissionAgreementService } from './permission-agreement.service';

@Component({
    selector: 'jhi-permission-agreement-delete-dialog',
    templateUrl: './permission-agreement-delete-dialog.component.html'
})
export class PermissionAgreementDeleteDialogComponent {
    permissionAgreement: IPermissionAgreement;

    constructor(
        protected permissionAgreementService: PermissionAgreementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.permissionAgreementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'permissionAgreementListModification',
                content: 'Deleted an permissionAgreement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-permission-agreement-delete-popup',
    template: ''
})
export class PermissionAgreementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ permissionAgreement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PermissionAgreementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.permissionAgreement = permissionAgreement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/permission-agreement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/permission-agreement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
