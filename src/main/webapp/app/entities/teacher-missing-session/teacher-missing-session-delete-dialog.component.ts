import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';
import { TeacherMissingSessionService } from './teacher-missing-session.service';

@Component({
    selector: 'jhi-teacher-missing-session-delete-dialog',
    templateUrl: './teacher-missing-session-delete-dialog.component.html'
})
export class TeacherMissingSessionDeleteDialogComponent {
    teacherMissingSession: ITeacherMissingSession;

    constructor(
        protected teacherMissingSessionService: TeacherMissingSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teacherMissingSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'teacherMissingSessionListModification',
                content: 'Deleted an teacherMissingSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-teacher-missing-session-delete-popup',
    template: ''
})
export class TeacherMissingSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherMissingSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeacherMissingSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.teacherMissingSession = teacherMissingSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/teacher-missing-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/teacher-missing-session', { outlets: { popup: null } }]);
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
