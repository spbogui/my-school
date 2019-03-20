import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';
import { SchoolSchoolYearService } from './school-school-year.service';

@Component({
    selector: 'jhi-school-school-year-delete-dialog',
    templateUrl: './school-school-year-delete-dialog.component.html'
})
export class SchoolSchoolYearDeleteDialogComponent {
    schoolSchoolYear: ISchoolSchoolYear;

    constructor(
        protected schoolSchoolYearService: SchoolSchoolYearService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schoolSchoolYearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schoolSchoolYearListModification',
                content: 'Deleted an schoolSchoolYear'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-school-school-year-delete-popup',
    template: ''
})
export class SchoolSchoolYearDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolSchoolYear }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SchoolSchoolYearDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.schoolSchoolYear = schoolSchoolYear;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/school-school-year', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/school-school-year', { outlets: { popup: null } }]);
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
