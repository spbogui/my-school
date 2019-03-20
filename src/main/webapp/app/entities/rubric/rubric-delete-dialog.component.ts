import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRubric } from 'app/shared/model/rubric.model';
import { RubricService } from './rubric.service';

@Component({
    selector: 'jhi-rubric-delete-dialog',
    templateUrl: './rubric-delete-dialog.component.html'
})
export class RubricDeleteDialogComponent {
    rubric: IRubric;

    constructor(protected rubricService: RubricService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rubricService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rubricListModification',
                content: 'Deleted an rubric'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rubric-delete-popup',
    template: ''
})
export class RubricDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rubric }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RubricDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rubric = rubric;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rubric', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rubric', { outlets: { popup: null } }]);
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
