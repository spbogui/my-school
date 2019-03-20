/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EnrolmentUpdateComponent } from 'app/entities/enrolment/enrolment-update.component';
import { EnrolmentService } from 'app/entities/enrolment/enrolment.service';
import { Enrolment } from 'app/shared/model/enrolment.model';

describe('Component Tests', () => {
    describe('Enrolment Management Update Component', () => {
        let comp: EnrolmentUpdateComponent;
        let fixture: ComponentFixture<EnrolmentUpdateComponent>;
        let service: EnrolmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EnrolmentUpdateComponent]
            })
                .overrideTemplate(EnrolmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnrolmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnrolmentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Enrolment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enrolment = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Enrolment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enrolment = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
