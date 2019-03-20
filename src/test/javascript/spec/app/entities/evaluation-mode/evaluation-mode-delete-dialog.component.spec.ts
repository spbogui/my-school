/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationModeDeleteDialogComponent } from 'app/entities/evaluation-mode/evaluation-mode-delete-dialog.component';
import { EvaluationModeService } from 'app/entities/evaluation-mode/evaluation-mode.service';

describe('Component Tests', () => {
    describe('EvaluationMode Management Delete Component', () => {
        let comp: EvaluationModeDeleteDialogComponent;
        let fixture: ComponentFixture<EvaluationModeDeleteDialogComponent>;
        let service: EvaluationModeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationModeDeleteDialogComponent]
            })
                .overrideTemplate(EvaluationModeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvaluationModeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationModeService);
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
