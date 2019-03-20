import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnrolment } from 'app/shared/model/enrolment.model';
import { EnrolmentService } from './enrolment.service';

@Component({
    selector: 'jhi-enrolment-delete-dialog',
    templateUrl: './enrolment-delete-dialog.component.html'
})
export class EnrolmentDeleteDialogComponent {
    enrolment: IEnrolment;

    constructor(
        protected enrolmentService: EnrolmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enrolmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enrolmentListModification',
                content: 'Deleted an enrolment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enrolment-delete-popup',
    template: ''
})
export class EnrolmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enrolment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnrolmentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.enrolment = enrolment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/enrolment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/enrolment', { outlets: { popup: null } }]);
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
