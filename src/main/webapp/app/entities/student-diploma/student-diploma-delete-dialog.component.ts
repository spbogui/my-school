import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentDiploma } from 'app/shared/model/student-diploma.model';
import { StudentDiplomaService } from './student-diploma.service';

@Component({
    selector: 'jhi-student-diploma-delete-dialog',
    templateUrl: './student-diploma-delete-dialog.component.html'
})
export class StudentDiplomaDeleteDialogComponent {
    studentDiploma: IStudentDiploma;

    constructor(
        protected studentDiplomaService: StudentDiplomaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentDiplomaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentDiplomaListModification',
                content: 'Deleted an studentDiploma'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-diploma-delete-popup',
    template: ''
})
export class StudentDiplomaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentDiploma }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentDiplomaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentDiploma = studentDiploma;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-diploma', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-diploma', { outlets: { popup: null } }]);
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
