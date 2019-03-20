import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiploma } from 'app/shared/model/diploma.model';
import { DiplomaService } from './diploma.service';

@Component({
    selector: 'jhi-diploma-delete-dialog',
    templateUrl: './diploma-delete-dialog.component.html'
})
export class DiplomaDeleteDialogComponent {
    diploma: IDiploma;

    constructor(protected diplomaService: DiplomaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diplomaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'diplomaListModification',
                content: 'Deleted an diploma'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-diploma-delete-popup',
    template: ''
})
export class DiplomaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diploma }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DiplomaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.diploma = diploma;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/diploma', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/diploma', { outlets: { popup: null } }]);
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
