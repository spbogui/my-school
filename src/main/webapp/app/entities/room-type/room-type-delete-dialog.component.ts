import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoomType } from 'app/shared/model/room-type.model';
import { RoomTypeService } from './room-type.service';

@Component({
    selector: 'jhi-room-type-delete-dialog',
    templateUrl: './room-type-delete-dialog.component.html'
})
export class RoomTypeDeleteDialogComponent {
    roomType: IRoomType;

    constructor(protected roomTypeService: RoomTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roomTypeListModification',
                content: 'Deleted an roomType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-room-type-delete-popup',
    template: ''
})
export class RoomTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoomTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.roomType = roomType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/room-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/room-type', { outlets: { popup: null } }]);
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
