/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentEvaluationDetailComponent } from 'app/entities/student-evaluation/student-evaluation-detail.component';
import { StudentEvaluation } from 'app/shared/model/student-evaluation.model';

describe('Component Tests', () => {
    describe('StudentEvaluation Management Detail Component', () => {
        let comp: StudentEvaluationDetailComponent;
        let fixture: ComponentFixture<StudentEvaluationDetailComponent>;
        const route = ({ data: of({ studentEvaluation: new StudentEvaluation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentEvaluationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentEvaluationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentEvaluationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentEvaluation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
