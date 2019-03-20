import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRubricAmount } from 'app/shared/model/rubric-amount.model';
import { RubricAmountService } from './rubric-amount.service';

@Component({
    selector: 'jhi-rubric-amount-delete-dialog',
    templateUrl: './rubric-amount-delete-dialog.component.html'
})
export class RubricAmountDeleteDialogComponent {
    rubricAmount: IRubricAmount;

    constructor(
        protected rubricAmountService: RubricAmountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rubricAmountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rubricAmountListModification',
                content: 'Deleted an rubricAmount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rubric-amount-delete-popup',
    template: ''
})
export class RubricAmountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rubricAmount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RubricAmountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rubricAmount = rubricAmount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rubric-amount', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rubric-amount', { outlets: { popup: null } }]);
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
