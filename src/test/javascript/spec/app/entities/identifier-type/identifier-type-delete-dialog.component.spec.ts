/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { IdentifierTypeDeleteDialogComponent } from 'app/entities/identifier-type/identifier-type-delete-dialog.component';
import { IdentifierTypeService } from 'app/entities/identifier-type/identifier-type.service';

describe('Component Tests', () => {
    describe('IdentifierType Management Delete Component', () => {
        let comp: IdentifierTypeDeleteDialogComponent;
        let fixture: ComponentFixture<IdentifierTypeDeleteDialogComponent>;
        let service: IdentifierTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [IdentifierTypeDeleteDialogComponent]
            })
                .overrideTemplate(IdentifierTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IdentifierTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentifierTypeService);
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
