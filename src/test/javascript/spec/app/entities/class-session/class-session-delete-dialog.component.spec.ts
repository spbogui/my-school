/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionDeleteDialogComponent } from 'app/entities/class-session/class-session-delete-dialog.component';
import { ClassSessionService } from 'app/entities/class-session/class-session.service';

describe('Component Tests', () => {
    describe('ClassSession Management Delete Component', () => {
        let comp: ClassSessionDeleteDialogComponent;
        let fixture: ComponentFixture<ClassSessionDeleteDialogComponent>;
        let service: ClassSessionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionDeleteDialogComponent]
            })
                .overrideTemplate(ClassSessionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassSessionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassSessionService);
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
