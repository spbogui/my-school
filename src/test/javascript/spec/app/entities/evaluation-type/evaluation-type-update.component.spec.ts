/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationTypeUpdateComponent } from 'app/entities/evaluation-type/evaluation-type-update.component';
import { EvaluationTypeService } from 'app/entities/evaluation-type/evaluation-type.service';
import { EvaluationType } from 'app/shared/model/evaluation-type.model';

describe('Component Tests', () => {
    describe('EvaluationType Management Update Component', () => {
        let comp: EvaluationTypeUpdateComponent;
        let fixture: ComponentFixture<EvaluationTypeUpdateComponent>;
        let service: EvaluationTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationTypeUpdateComponent]
            })
                .overrideTemplate(EvaluationTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvaluationTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EvaluationType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evaluationType = entity;
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
                    const entity = new EvaluationType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evaluationType = entity;
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
