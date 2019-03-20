/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { StudentMissingSessionDeleteDialogComponent } from 'app/entities/student-missing-session/student-missing-session-delete-dialog.component';
import { StudentMissingSessionService } from 'app/entities/student-missing-session/student-missing-session.service';

describe('Component Tests', () => {
    describe('StudentMissingSession Management Delete Component', () => {
        let comp: StudentMissingSessionDeleteDialogComponent;
        let fixture: ComponentFixture<StudentMissingSessionDeleteDialogComponent>;
        let service: StudentMissingSessionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentMissingSessionDeleteDialogComponent]
            })
                .overrideTemplate(StudentMissingSessionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentMissingSessionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentMissingSessionService);
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
