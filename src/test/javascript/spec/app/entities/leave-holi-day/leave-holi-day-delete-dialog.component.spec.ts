/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { LeaveHoliDayDeleteDialogComponent } from 'app/entities/leave-holi-day/leave-holi-day-delete-dialog.component';
import { LeaveHoliDayService } from 'app/entities/leave-holi-day/leave-holi-day.service';

describe('Component Tests', () => {
    describe('LeaveHoliDay Management Delete Component', () => {
        let comp: LeaveHoliDayDeleteDialogComponent;
        let fixture: ComponentFixture<LeaveHoliDayDeleteDialogComponent>;
        let service: LeaveHoliDayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [LeaveHoliDayDeleteDialogComponent]
            })
                .overrideTemplate(LeaveHoliDayDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LeaveHoliDayDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeaveHoliDayService);
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
