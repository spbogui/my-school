/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationModeUpdateComponent } from 'app/entities/evaluation-mode/evaluation-mode-update.component';
import { EvaluationModeService } from 'app/entities/evaluation-mode/evaluation-mode.service';
import { EvaluationMode } from 'app/shared/model/evaluation-mode.model';

describe('Component Tests', () => {
    describe('EvaluationMode Management Update Component', () => {
        let comp: EvaluationModeUpdateComponent;
        let fixture: ComponentFixture<EvaluationModeUpdateComponent>;
        let service: EvaluationModeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationModeUpdateComponent]
            })
                .overrideTemplate(EvaluationModeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvaluationModeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationModeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EvaluationMode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evaluationMode = entity;
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
                    const entity = new EvaluationMode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evaluationMode = entity;
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
