import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassSessionType } from 'app/shared/model/class-session-type.model';
import { ClassSessionTypeService } from './class-session-type.service';

@Component({
    selector: 'jhi-class-session-type-delete-dialog',
    templateUrl: './class-session-type-delete-dialog.component.html'
})
export class ClassSessionTypeDeleteDialogComponent {
    classSessionType: IClassSessionType;

    constructor(
        protected classSessionTypeService: ClassSessionTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classSessionTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classSessionTypeListModification',
                content: 'Deleted an classSessionType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-class-session-type-delete-popup',
    template: ''
})
export class ClassSessionTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classSessionType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClassSessionTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.classSessionType = classSessionType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/class-session-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/class-session-type', { outlets: { popup: null } }]);
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
