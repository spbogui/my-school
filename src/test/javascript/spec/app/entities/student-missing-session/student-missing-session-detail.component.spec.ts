/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentMissingSessionDetailComponent } from 'app/entities/student-missing-session/student-missing-session-detail.component';
import { StudentMissingSession } from 'app/shared/model/student-missing-session.model';

describe('Component Tests', () => {
    describe('StudentMissingSession Management Detail Component', () => {
        let comp: StudentMissingSessionDetailComponent;
        let fixture: ComponentFixture<StudentMissingSessionDetailComponent>;
        const route = ({ data: of({ studentMissingSession: new StudentMissingSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentMissingSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentMissingSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentMissingSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentMissingSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
