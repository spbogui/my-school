/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentEvaluationUpdateComponent } from 'app/entities/student-evaluation/student-evaluation-update.component';
import { StudentEvaluationService } from 'app/entities/student-evaluation/student-evaluation.service';
import { StudentEvaluation } from 'app/shared/model/student-evaluation.model';

describe('Component Tests', () => {
    describe('StudentEvaluation Management Update Component', () => {
        let comp: StudentEvaluationUpdateComponent;
        let fixture: ComponentFixture<StudentEvaluationUpdateComponent>;
        let service: StudentEvaluationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentEvaluationUpdateComponent]
            })
                .overrideTemplate(StudentEvaluationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentEvaluationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentEvaluationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentEvaluation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentEvaluation = entity;
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
                    const entity = new StudentEvaluation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentEvaluation = entity;
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
