/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationModeDetailComponent } from 'app/entities/evaluation-mode/evaluation-mode-detail.component';
import { EvaluationMode } from 'app/shared/model/evaluation-mode.model';

describe('Component Tests', () => {
    describe('EvaluationMode Management Detail Component', () => {
        let comp: EvaluationModeDetailComponent;
        let fixture: ComponentFixture<EvaluationModeDetailComponent>;
        const route = ({ data: of({ evaluationMode: new EvaluationMode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationModeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EvaluationModeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvaluationModeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.evaluationMode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
