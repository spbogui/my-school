/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { DaysComponent } from 'app/entities/days/days.component';
import { DaysService } from 'app/entities/days/days.service';
import { Days } from 'app/shared/model/days.model';

describe('Component Tests', () => {
    describe('Days Management Component', () => {
        let comp: DaysComponent;
        let fixture: ComponentFixture<DaysComponent>;
        let service: DaysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [DaysComponent],
                providers: []
            })
                .overrideTemplate(DaysComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DaysComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DaysService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Days(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.days[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
