import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';
import { StudentEvaluationService } from './student-evaluation.service';

@Component({
    selector: 'jhi-student-evaluation-delete-dialog',
    templateUrl: './student-evaluation-delete-dialog.component.html'
})
export class StudentEvaluationDeleteDialogComponent {
    studentEvaluation: IStudentEvaluation;

    constructor(
        protected studentEvaluationService: StudentEvaluationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentEvaluationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentEvaluationListModification',
                content: 'Deleted an studentEvaluation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-evaluation-delete-popup',
    template: ''
})
export class StudentEvaluationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentEvaluation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentEvaluationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentEvaluation = studentEvaluation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-evaluation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-evaluation', { outlets: { popup: null } }]);
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
