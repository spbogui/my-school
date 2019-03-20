/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationTypeDetailComponent } from 'app/entities/evaluation-type/evaluation-type-detail.component';
import { EvaluationType } from 'app/shared/model/evaluation-type.model';

describe('Component Tests', () => {
    describe('EvaluationType Management Detail Component', () => {
        let comp: EvaluationTypeDetailComponent;
        let fixture: ComponentFixture<EvaluationTypeDetailComponent>;
        const route = ({ data: of({ evaluationType: new EvaluationType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EvaluationTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvaluationTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.evaluationType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
