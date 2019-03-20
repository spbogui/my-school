/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationTypeComponent } from 'app/entities/evaluation-type/evaluation-type.component';
import { EvaluationTypeService } from 'app/entities/evaluation-type/evaluation-type.service';
import { EvaluationType } from 'app/shared/model/evaluation-type.model';

describe('Component Tests', () => {
    describe('EvaluationType Management Component', () => {
        let comp: EvaluationTypeComponent;
        let fixture: ComponentFixture<EvaluationTypeComponent>;
        let service: EvaluationTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationTypeComponent],
                providers: []
            })
                .overrideTemplate(EvaluationTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvaluationTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EvaluationType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.evaluationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
