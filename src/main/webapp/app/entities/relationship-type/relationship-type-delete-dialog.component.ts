import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type-delete-dialog',
    templateUrl: './relationship-type-delete-dialog.component.html'
})
export class RelationshipTypeDeleteDialogComponent {
    relationshipType: IRelationshipType;

    constructor(
        protected relationshipTypeService: RelationshipTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.relationshipTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'relationshipTypeListModification',
                content: 'Deleted an relationshipType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-relationship-type-delete-popup',
    template: ''
})
export class RelationshipTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ relationshipType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RelationshipTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.relationshipType = relationshipType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/relationship-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/relationship-type', { outlets: { popup: null } }]);
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
