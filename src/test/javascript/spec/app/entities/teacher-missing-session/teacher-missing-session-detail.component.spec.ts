/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { TeacherMissingSessionDetailComponent } from 'app/entities/teacher-missing-session/teacher-missing-session-detail.component';
import { TeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';

describe('Component Tests', () => {
    describe('TeacherMissingSession Management Detail Component', () => {
        let comp: TeacherMissingSessionDetailComponent;
        let fixture: ComponentFixture<TeacherMissingSessionDetailComponent>;
        const route = ({ data: of({ teacherMissingSession: new TeacherMissingSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [TeacherMissingSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeacherMissingSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeacherMissingSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teacherMissingSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
