/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationTypeDeleteDialogComponent } from 'app/entities/evaluation-type/evaluation-type-delete-dialog.component';
import { EvaluationTypeService } from 'app/entities/evaluation-type/evaluation-type.service';

describe('Component Tests', () => {
    describe('EvaluationType Management Delete Component', () => {
        let comp: EvaluationTypeDeleteDialogComponent;
        let fixture: ComponentFixture<EvaluationTypeDeleteDialogComponent>;
        let service: EvaluationTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationTypeDeleteDialogComponent]
            })
                .overrideTemplate(EvaluationTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvaluationTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationTypeService);
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
