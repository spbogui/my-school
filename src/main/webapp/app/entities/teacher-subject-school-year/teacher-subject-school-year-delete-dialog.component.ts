import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';
import { TeacherSubjectSchoolYearService } from './teacher-subject-school-year.service';

@Component({
    selector: 'jhi-teacher-subject-school-year-delete-dialog',
    templateUrl: './teacher-subject-school-year-delete-dialog.component.html'
})
export class TeacherSubjectSchoolYearDeleteDialogComponent {
    teacherSubjectSchoolYear: ITeacherSubjectSchoolYear;

    constructor(
        protected teacherSubjectSchoolYearService: TeacherSubjectSchoolYearService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teacherSubjectSchoolYearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'teacherSubjectSchoolYearListModification',
                content: 'Deleted an teacherSubjectSchoolYear'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-teacher-subject-school-year-delete-popup',
    template: ''
})
export class TeacherSubjectSchoolYearDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherSubjectSchoolYear }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeacherSubjectSchoolYearDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.teacherSubjectSchoolYear = teacherSubjectSchoolYear;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/teacher-subject-school-year', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/teacher-subject-school-year', { outlets: { popup: null } }]);
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
