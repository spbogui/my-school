/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { StudentDiplomaDeleteDialogComponent } from 'app/entities/student-diploma/student-diploma-delete-dialog.component';
import { StudentDiplomaService } from 'app/entities/student-diploma/student-diploma.service';

describe('Component Tests', () => {
    describe('StudentDiploma Management Delete Component', () => {
        let comp: StudentDiplomaDeleteDialogComponent;
        let fixture: ComponentFixture<StudentDiplomaDeleteDialogComponent>;
        let service: StudentDiplomaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentDiplomaDeleteDialogComponent]
            })
                .overrideTemplate(StudentDiplomaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentDiplomaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentDiplomaService);
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
