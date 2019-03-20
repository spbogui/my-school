import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';
import { EvaluationModeService } from './evaluation-mode.service';

@Component({
    selector: 'jhi-evaluation-mode-delete-dialog',
    templateUrl: './evaluation-mode-delete-dialog.component.html'
})
export class EvaluationModeDeleteDialogComponent {
    evaluationMode: IEvaluationMode;

    constructor(
        protected evaluationModeService: EvaluationModeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.evaluationModeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'evaluationModeListModification',
                content: 'Deleted an evaluationMode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-evaluation-mode-delete-popup',
    template: ''
})
export class EvaluationModeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evaluationMode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EvaluationModeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.evaluationMode = evaluationMode;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/evaluation-mode', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/evaluation-mode', { outlets: { popup: null } }]);
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
