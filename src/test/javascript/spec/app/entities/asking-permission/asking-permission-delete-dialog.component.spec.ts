/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { AskingPermissionDeleteDialogComponent } from 'app/entities/asking-permission/asking-permission-delete-dialog.component';
import { AskingPermissionService } from 'app/entities/asking-permission/asking-permission.service';

describe('Component Tests', () => {
    describe('AskingPermission Management Delete Component', () => {
        let comp: AskingPermissionDeleteDialogComponent;
        let fixture: ComponentFixture<AskingPermissionDeleteDialogComponent>;
        let service: AskingPermissionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [AskingPermissionDeleteDialogComponent]
            })
                .overrideTemplate(AskingPermissionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AskingPermissionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AskingPermissionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
