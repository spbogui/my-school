/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { TeacherSubjectSchoolYearDetailComponent } from 'app/entities/teacher-subject-school-year/teacher-subject-school-year-detail.component';
import { TeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';

describe('Component Tests', () => {
    describe('TeacherSubjectSchoolYear Management Detail Component', () => {
        let comp: TeacherSubjectSchoolYearDetailComponent;
        let fixture: ComponentFixture<TeacherSubjectSchoolYearDetailComponent>;
        const route = ({ data: of({ teacherSubjectSchoolYear: new TeacherSubjectSchoolYear(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [TeacherSubjectSchoolYearDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeacherSubjectSchoolYearDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeacherSubjectSchoolYearDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teacherSubjectSchoolYear).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
