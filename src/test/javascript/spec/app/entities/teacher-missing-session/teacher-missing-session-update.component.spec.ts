/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { TeacherMissingSessionUpdateComponent } from 'app/entities/teacher-missing-session/teacher-missing-session-update.component';
import { TeacherMissingSessionService } from 'app/entities/teacher-missing-session/teacher-missing-session.service';
import { TeacherMissingSession } from 'app/shared/model/teacher-missing-session.model';

describe('Component Tests', () => {
    describe('TeacherMissingSession Management Update Component', () => {
        let comp: TeacherMissingSessionUpdateComponent;
        let fixture: ComponentFixture<TeacherMissingSessionUpdateComponent>;
        let service: TeacherMissingSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [TeacherMissingSessionUpdateComponent]
            })
                .overrideTemplate(TeacherMissingSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeacherMissingSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherMissingSessionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TeacherMissingSession(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.teacherMissingSession = entity;
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
                    const entity = new TeacherMissingSession();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.teacherMissingSession = entity;
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
