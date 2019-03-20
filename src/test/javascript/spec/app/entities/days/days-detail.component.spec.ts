/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { DaysDetailComponent } from 'app/entities/days/days-detail.component';
import { Days } from 'app/shared/model/days.model';

describe('Component Tests', () => {
    describe('Days Management Detail Component', () => {
        let comp: DaysDetailComponent;
        let fixture: ComponentFixture<DaysDetailComponent>;
        const route = ({ data: of({ days: new Days(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [DaysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DaysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DaysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.days).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
