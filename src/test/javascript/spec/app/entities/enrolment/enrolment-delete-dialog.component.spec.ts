/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { EnrolmentDeleteDialogComponent } from 'app/entities/enrolment/enrolment-delete-dialog.component';
import { EnrolmentService } from 'app/entities/enrolment/enrolment.service';

describe('Component Tests', () => {
    describe('Enrolment Management Delete Component', () => {
        let comp: EnrolmentDeleteDialogComponent;
        let fixture: ComponentFixture<EnrolmentDeleteDialogComponent>;
        let service: EnrolmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EnrolmentDeleteDialogComponent]
            })
                .overrideTemplate(EnrolmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnrolmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnrolmentService);
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
