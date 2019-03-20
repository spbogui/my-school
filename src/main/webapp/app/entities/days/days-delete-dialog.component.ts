import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDays } from 'app/shared/model/days.model';
import { DaysService } from './days.service';

@Component({
    selector: 'jhi-days-delete-dialog',
    templateUrl: './days-delete-dialog.component.html'
})
export class DaysDeleteDialogComponent {
    days: IDays;

    constructor(protected daysService: DaysService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.daysService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'daysListModification',
                content: 'Deleted an days'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-days-delete-popup',
    template: ''
})
export class DaysDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ days }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DaysDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.days = days;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/days', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/days', { outlets: { popup: null } }]);
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
