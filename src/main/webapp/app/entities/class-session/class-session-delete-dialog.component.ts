import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassSession } from 'app/shared/model/class-session.model';
import { ClassSessionService } from './class-session.service';

@Component({
    selector: 'jhi-class-session-delete-dialog',
    templateUrl: './class-session-delete-dialog.component.html'
})
export class ClassSessionDeleteDialogComponent {
    classSession: IClassSession;

    constructor(
        protected classSessionService: ClassSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classSessionListModification',
                content: 'Deleted an classSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-class-session-delete-popup',
    template: ''
})
export class ClassSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClassSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.classSession = classSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/class-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/class-session', { outlets: { popup: null } }]);
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
