import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAskingPermission } from 'app/shared/model/asking-permission.model';
import { AskingPermissionService } from './asking-permission.service';

@Component({
    selector: 'jhi-asking-permission-delete-dialog',
    templateUrl: './asking-permission-delete-dialog.component.html'
})
export class AskingPermissionDeleteDialogComponent {
    askingPermission: IAskingPermission;

    constructor(
        protected askingPermissionService: AskingPermissionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.askingPermissionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'askingPermissionListModification',
                content: 'Deleted an askingPermission'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-asking-permission-delete-popup',
    template: ''
})
export class AskingPermissionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ askingPermission }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AskingPermissionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.askingPermission = askingPermission;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/asking-permission', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/asking-permission', { outlets: { popup: null } }]);
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
