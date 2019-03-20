/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { EvaluationModeComponent } from 'app/entities/evaluation-mode/evaluation-mode.component';
import { EvaluationModeService } from 'app/entities/evaluation-mode/evaluation-mode.service';
import { EvaluationMode } from 'app/shared/model/evaluation-mode.model';

describe('Component Tests', () => {
    describe('EvaluationMode Management Component', () => {
        let comp: EvaluationModeComponent;
        let fixture: ComponentFixture<EvaluationModeComponent>;
        let service: EvaluationModeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EvaluationModeComponent],
                providers: []
            })
                .overrideTemplate(EvaluationModeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvaluationModeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluationModeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EvaluationMode(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.evaluationModes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
