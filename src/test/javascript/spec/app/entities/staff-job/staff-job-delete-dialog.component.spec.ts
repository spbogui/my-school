/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { StaffJobDeleteDialogComponent } from 'app/entities/staff-job/staff-job-delete-dialog.component';
import { StaffJobService } from 'app/entities/staff-job/staff-job.service';

describe('Component Tests', () => {
    describe('StaffJob Management Delete Component', () => {
        let comp: StaffJobDeleteDialogComponent;
        let fixture: ComponentFixture<StaffJobDeleteDialogComponent>;
        let service: StaffJobService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StaffJobDeleteDialogComponent]
            })
                .overrideTemplate(StaffJobDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaffJobDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaffJobService);
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
