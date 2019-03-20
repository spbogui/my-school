/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { RubricComponent } from 'app/entities/rubric/rubric.component';
import { RubricService } from 'app/entities/rubric/rubric.service';
import { Rubric } from 'app/shared/model/rubric.model';

describe('Component Tests', () => {
    describe('Rubric Management Component', () => {
        let comp: RubricComponent;
        let fixture: ComponentFixture<RubricComponent>;
        let service: RubricService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricComponent],
                providers: []
            })
                .overrideTemplate(RubricComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RubricComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubricService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Rubric(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rubrics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
