/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { StudentEvaluationDeleteDialogComponent } from 'app/entities/student-evaluation/student-evaluation-delete-dialog.component';
import { StudentEvaluationService } from 'app/entities/student-evaluation/student-evaluation.service';

describe('Component Tests', () => {
    describe('StudentEvaluation Management Delete Component', () => {
        let comp: StudentEvaluationDeleteDialogComponent;
        let fixture: ComponentFixture<StudentEvaluationDeleteDialogComponent>;
        let service: StudentEvaluationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentEvaluationDeleteDialogComponent]
            })
                .overrideTemplate(StudentEvaluationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentEvaluationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentEvaluationService);
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
