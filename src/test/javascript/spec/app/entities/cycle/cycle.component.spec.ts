/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { CycleComponent } from 'app/entities/cycle/cycle.component';
import { CycleService } from 'app/entities/cycle/cycle.service';
import { Cycle } from 'app/shared/model/cycle.model';

describe('Component Tests', () => {
    describe('Cycle Management Component', () => {
        let comp: CycleComponent;
        let fixture: ComponentFixture<CycleComponent>;
        let service: CycleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [CycleComponent],
                providers: []
            })
                .overrideTemplate(CycleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CycleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CycleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Cycle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cycles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
