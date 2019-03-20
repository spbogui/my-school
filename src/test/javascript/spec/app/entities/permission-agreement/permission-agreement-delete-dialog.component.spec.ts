/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { PermissionAgreementDeleteDialogComponent } from 'app/entities/permission-agreement/permission-agreement-delete-dialog.component';
import { PermissionAgreementService } from 'app/entities/permission-agreement/permission-agreement.service';

describe('Component Tests', () => {
    describe('PermissionAgreement Management Delete Component', () => {
        let comp: PermissionAgreementDeleteDialogComponent;
        let fixture: ComponentFixture<PermissionAgreementDeleteDialogComponent>;
        let service: PermissionAgreementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [PermissionAgreementDeleteDialogComponent]
            })
                .overrideTemplate(PermissionAgreementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PermissionAgreementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionAgreementService);
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
