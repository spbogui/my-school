/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { RubricAmountDeleteDialogComponent } from 'app/entities/rubric-amount/rubric-amount-delete-dialog.component';
import { RubricAmountService } from 'app/entities/rubric-amount/rubric-amount.service';

describe('Component Tests', () => {
    describe('RubricAmount Management Delete Component', () => {
        let comp: RubricAmountDeleteDialogComponent;
        let fixture: ComponentFixture<RubricAmountDeleteDialogComponent>;
        let service: RubricAmountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricAmountDeleteDialogComponent]
            })
                .overrideTemplate(RubricAmountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RubricAmountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubricAmountService);
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
