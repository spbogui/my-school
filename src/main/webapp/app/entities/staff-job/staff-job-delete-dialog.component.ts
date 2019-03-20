import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStaffJob } from 'app/shared/model/staff-job.model';
import { StaffJobService } from './staff-job.service';

@Component({
    selector: 'jhi-staff-job-delete-dialog',
    templateUrl: './staff-job-delete-dialog.component.html'
})
export class StaffJobDeleteDialogComponent {
    staffJob: IStaffJob;

    constructor(protected staffJobService: StaffJobService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.staffJobService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'staffJobListModification',
                content: 'Deleted an staffJob'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-staff-job-delete-popup',
    template: ''
})
export class StaffJobDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ staffJob }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StaffJobDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.staffJob = staffJob;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/staff-job', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/staff-job', { outlets: { popup: null } }]);
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
