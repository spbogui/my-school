/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentDiplomaDetailComponent } from 'app/entities/student-diploma/student-diploma-detail.component';
import { StudentDiploma } from 'app/shared/model/student-diploma.model';

describe('Component Tests', () => {
    describe('StudentDiploma Management Detail Component', () => {
        let comp: StudentDiplomaDetailComponent;
        let fixture: ComponentFixture<StudentDiplomaDetailComponent>;
        const route = ({ data: of({ studentDiploma: new StudentDiploma(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentDiplomaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentDiplomaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentDiplomaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentDiploma).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
