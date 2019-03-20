/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { ActorIdentifierNumberDeleteDialogComponent } from 'app/entities/actor-identifier-number/actor-identifier-number-delete-dialog.component';
import { ActorIdentifierNumberService } from 'app/entities/actor-identifier-number/actor-identifier-number.service';

describe('Component Tests', () => {
    describe('ActorIdentifierNumber Management Delete Component', () => {
        let comp: ActorIdentifierNumberDeleteDialogComponent;
        let fixture: ComponentFixture<ActorIdentifierNumberDeleteDialogComponent>;
        let service: ActorIdentifierNumberService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorIdentifierNumberDeleteDialogComponent]
            })
                .overrideTemplate(ActorIdentifierNumberDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActorIdentifierNumberDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActorIdentifierNumberService);
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
