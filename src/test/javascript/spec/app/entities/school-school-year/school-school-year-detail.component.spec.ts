/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { SchoolSchoolYearDetailComponent } from 'app/entities/school-school-year/school-school-year-detail.component';
import { SchoolSchoolYear } from 'app/shared/model/school-school-year.model';

describe('Component Tests', () => {
    describe('SchoolSchoolYear Management Detail Component', () => {
        let comp: SchoolSchoolYearDetailComponent;
        let fixture: ComponentFixture<SchoolSchoolYearDetailComponent>;
        const route = ({ data: of({ schoolSchoolYear: new SchoolSchoolYear(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [SchoolSchoolYearDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SchoolSchoolYearDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SchoolSchoolYearDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.schoolSchoolYear).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
