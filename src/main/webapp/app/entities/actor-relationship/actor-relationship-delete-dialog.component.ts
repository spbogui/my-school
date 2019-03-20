import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActorRelationship } from 'app/shared/model/actor-relationship.model';
import { ActorRelationshipService } from './actor-relationship.service';

@Component({
    selector: 'jhi-actor-relationship-delete-dialog',
    templateUrl: './actor-relationship-delete-dialog.component.html'
})
export class ActorRelationshipDeleteDialogComponent {
    actorRelationship: IActorRelationship;

    constructor(
        protected actorRelationshipService: ActorRelationshipService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actorRelationshipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actorRelationshipListModification',
                content: 'Deleted an actorRelationship'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actor-relationship-delete-popup',
    template: ''
})
export class ActorRelationshipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorRelationship }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActorRelationshipDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.actorRelationship = actorRelationship;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/actor-relationship', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/actor-relationship', { outlets: { popup: null } }]);
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
