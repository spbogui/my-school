/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { SchoolYearDetailComponent } from 'app/entities/school-year/school-year-detail.component';
import { SchoolYear } from 'app/shared/model/school-year.model';

describe('Component Tests', () => {
    describe('SchoolYear Management Detail Component', () => {
        let comp: SchoolYearDetailComponent;
        let fixture: ComponentFixture<SchoolYearDetailComponent>;
        const route = ({ data: of({ schoolYear: new SchoolYear(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [SchoolYearDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SchoolYearDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SchoolYearDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.schoolYear).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
