import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentMissingSession } from 'app/shared/model/student-missing-session.model';
import { StudentMissingSessionService } from './student-missing-session.service';

@Component({
    selector: 'jhi-student-missing-session-delete-dialog',
    templateUrl: './student-missing-session-delete-dialog.component.html'
})
export class StudentMissingSessionDeleteDialogComponent {
    studentMissingSession: IStudentMissingSession;

    constructor(
        protected studentMissingSessionService: StudentMissingSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentMissingSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentMissingSessionListModification',
                content: 'Deleted an studentMissingSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-missing-session-delete-popup',
    template: ''
})
export class StudentMissingSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentMissingSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentMissingSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentMissingSession = studentMissingSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-missing-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-missing-session', { outlets: { popup: null } }]);
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
