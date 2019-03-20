import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponsible } from 'app/shared/model/responsible.model';
import { ResponsibleService } from './responsible.service';

@Component({
    selector: 'jhi-responsible-delete-dialog',
    templateUrl: './responsible-delete-dialog.component.html'
})
export class ResponsibleDeleteDialogComponent {
    responsible: IResponsible;

    constructor(
        protected responsibleService: ResponsibleService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.responsibleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'responsibleListModification',
                content: 'Deleted an responsible'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-responsible-delete-popup',
    template: ''
})
export class ResponsibleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ responsible }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResponsibleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.responsible = responsible;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/responsible', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/responsible', { outlets: { popup: null } }]);
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
