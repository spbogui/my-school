import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveHoliDay } from 'app/shared/model/leave-holi-day.model';
import { LeaveHoliDayService } from './leave-holi-day.service';

@Component({
    selector: 'jhi-leave-holi-day-delete-dialog',
    templateUrl: './leave-holi-day-delete-dialog.component.html'
})
export class LeaveHoliDayDeleteDialogComponent {
    leaveHoliDay: ILeaveHoliDay;

    constructor(
        protected leaveHoliDayService: LeaveHoliDayService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leaveHoliDayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'leaveHoliDayListModification',
                content: 'Deleted an leaveHoliDay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leave-holi-day-delete-popup',
    template: ''
})
export class LeaveHoliDayDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveHoliDay }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveHoliDayDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.leaveHoliDay = leaveHoliDay;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/leave-holi-day', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/leave-holi-day', { outlets: { popup: null } }]);
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
