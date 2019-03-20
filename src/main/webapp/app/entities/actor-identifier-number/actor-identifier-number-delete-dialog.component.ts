import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';
import { ActorIdentifierNumberService } from './actor-identifier-number.service';

@Component({
    selector: 'jhi-actor-identifier-number-delete-dialog',
    templateUrl: './actor-identifier-number-delete-dialog.component.html'
})
export class ActorIdentifierNumberDeleteDialogComponent {
    actorIdentifierNumber: IActorIdentifierNumber;

    constructor(
        protected actorIdentifierNumberService: ActorIdentifierNumberService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actorIdentifierNumberService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actorIdentifierNumberListModification',
                content: 'Deleted an actorIdentifierNumber'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actor-identifier-number-delete-popup',
    template: ''
})
export class ActorIdentifierNumberDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorIdentifierNumber }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActorIdentifierNumberDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.actorIdentifierNumber = actorIdentifierNumber;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/actor-identifier-number', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/actor-identifier-number', { outlets: { popup: null } }]);
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
