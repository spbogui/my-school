import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalary } from 'app/shared/model/salary.model';
import { SalaryService } from './salary.service';

@Component({
    selector: 'jhi-salary-delete-dialog',
    templateUrl: './salary-delete-dialog.component.html'
})
export class SalaryDeleteDialogComponent {
    salary: ISalary;

    constructor(protected salaryService: SalaryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salaryListModification',
                content: 'Deleted an salary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-salary-delete-popup',
    template: ''
})
export class SalaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalaryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.salary = salary;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/salary', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/salary', { outlets: { popup: null } }]);
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
