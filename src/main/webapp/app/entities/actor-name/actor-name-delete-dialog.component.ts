import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActorName } from 'app/shared/model/actor-name.model';
import { ActorNameService } from './actor-name.service';

@Component({
    selector: 'jhi-actor-name-delete-dialog',
    templateUrl: './actor-name-delete-dialog.component.html'
})
export class ActorNameDeleteDialogComponent {
    actorName: IActorName;

    constructor(
        protected actorNameService: ActorNameService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actorNameService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actorNameListModification',
                content: 'Deleted an actorName'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actor-name-delete-popup',
    template: ''
})
export class ActorNameDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actorName }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActorNameDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.actorName = actorName;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/actor-name', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/actor-name', { outlets: { popup: null } }]);
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
