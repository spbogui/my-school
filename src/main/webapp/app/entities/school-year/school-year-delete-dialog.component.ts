import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolYear } from 'app/shared/model/school-year.model';
import { SchoolYearService } from './school-year.service';

@Component({
    selector: 'jhi-school-year-delete-dialog',
    templateUrl: './school-year-delete-dialog.component.html'
})
export class SchoolYearDeleteDialogComponent {
    schoolYear: ISchoolYear;

    constructor(
        protected schoolYearService: SchoolYearService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schoolYearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schoolYearListModification',
                content: 'Deleted an schoolYear'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-school-year-delete-popup',
    template: ''
})
export class SchoolYearDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolYear }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SchoolYearDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.schoolYear = schoolYear;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/school-year', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/school-year', { outlets: { popup: null } }]);
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
