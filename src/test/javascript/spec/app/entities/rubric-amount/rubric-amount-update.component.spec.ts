/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RubricAmountUpdateComponent } from 'app/entities/rubric-amount/rubric-amount-update.component';
import { RubricAmountService } from 'app/entities/rubric-amount/rubric-amount.service';
import { RubricAmount } from 'app/shared/model/rubric-amount.model';

describe('Component Tests', () => {
    describe('RubricAmount Management Update Component', () => {
        let comp: RubricAmountUpdateComponent;
        let fixture: ComponentFixture<RubricAmountUpdateComponent>;
        let service: RubricAmountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricAmountUpdateComponent]
            })
                .overrideTemplate(RubricAmountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RubricAmountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubricAmountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RubricAmount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rubricAmount = entity;
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
                    const entity = new RubricAmount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rubricAmount = entity;
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
