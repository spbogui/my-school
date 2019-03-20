/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { SchoolSchoolYearDeleteDialogComponent } from 'app/entities/school-school-year/school-school-year-delete-dialog.component';
import { SchoolSchoolYearService } from 'app/entities/school-school-year/school-school-year.service';

describe('Component Tests', () => {
    describe('SchoolSchoolYear Management Delete Component', () => {
        let comp: SchoolSchoolYearDeleteDialogComponent;
        let fixture: ComponentFixture<SchoolSchoolYearDeleteDialogComponent>;
        let service: SchoolSchoolYearService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [SchoolSchoolYearDeleteDialogComponent]
            })
                .overrideTemplate(SchoolSchoolYearDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SchoolSchoolYearDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolSchoolYearService);
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
