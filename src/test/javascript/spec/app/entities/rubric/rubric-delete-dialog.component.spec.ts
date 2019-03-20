/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { RubricDeleteDialogComponent } from 'app/entities/rubric/rubric-delete-dialog.component';
import { RubricService } from 'app/entities/rubric/rubric.service';

describe('Component Tests', () => {
    describe('Rubric Management Delete Component', () => {
        let comp: RubricDeleteDialogComponent;
        let fixture: ComponentFixture<RubricDeleteDialogComponent>;
        let service: RubricService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricDeleteDialogComponent]
            })
                .overrideTemplate(RubricDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RubricDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubricService);
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
