/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { PeriodTypeUpdateComponent } from 'app/entities/period-type/period-type-update.component';
import { PeriodTypeService } from 'app/entities/period-type/period-type.service';
import { PeriodType } from 'app/shared/model/period-type.model';

describe('Component Tests', () => {
    describe('PeriodType Management Update Component', () => {
        let comp: PeriodTypeUpdateComponent;
        let fixture: ComponentFixture<PeriodTypeUpdateComponent>;
        let service: PeriodTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [PeriodTypeUpdateComponent]
            })
                .overrideTemplate(PeriodTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PeriodType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodType = entity;
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
                    const entity = new PeriodType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodType = entity;
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
