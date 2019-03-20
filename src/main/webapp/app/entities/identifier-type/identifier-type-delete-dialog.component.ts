import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdentifierType } from 'app/shared/model/identifier-type.model';
import { IdentifierTypeService } from './identifier-type.service';

@Component({
    selector: 'jhi-identifier-type-delete-dialog',
    templateUrl: './identifier-type-delete-dialog.component.html'
})
export class IdentifierTypeDeleteDialogComponent {
    identifierType: IIdentifierType;

    constructor(
        protected identifierTypeService: IdentifierTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.identifierTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'identifierTypeListModification',
                content: 'Deleted an identifierType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-identifier-type-delete-popup',
    template: ''
})
export class IdentifierTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ identifierType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IdentifierTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.identifierType = identifierType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/identifier-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/identifier-type', { outlets: { popup: null } }]);
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
