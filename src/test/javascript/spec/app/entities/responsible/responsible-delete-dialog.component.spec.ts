/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { ResponsibleDeleteDialogComponent } from 'app/entities/responsible/responsible-delete-dialog.component';
import { ResponsibleService } from 'app/entities/responsible/responsible.service';

describe('Component Tests', () => {
    describe('Responsible Management Delete Component', () => {
        let comp: ResponsibleDeleteDialogComponent;
        let fixture: ComponentFixture<ResponsibleDeleteDialogComponent>;
        let service: ResponsibleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ResponsibleDeleteDialogComponent]
            })
                .overrideTemplate(ResponsibleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResponsibleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResponsibleService);
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
