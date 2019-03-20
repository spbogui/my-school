/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { TeacherSubjectSchoolYearUpdateComponent } from 'app/entities/teacher-subject-school-year/teacher-subject-school-year-update.component';
import { TeacherSubjectSchoolYearService } from 'app/entities/teacher-subject-school-year/teacher-subject-school-year.service';
import { TeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';

describe('Component Tests', () => {
    describe('TeacherSubjectSchoolYear Management Update Component', () => {
        let comp: TeacherSubjectSchoolYearUpdateComponent;
        let fixture: ComponentFixture<TeacherSubjectSchoolYearUpdateComponent>;
        let service: TeacherSubjectSchoolYearService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [TeacherSubjectSchoolYearUpdateComponent]
            })
                .overrideTemplate(TeacherSubjectSchoolYearUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeacherSubjectSchoolYearUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherSubjectSchoolYearService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TeacherSubjectSchoolYear(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.teacherSubjectSchoolYear = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TeacherSubjectSchoolYear();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.teacherSubjectSchoolYear = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
