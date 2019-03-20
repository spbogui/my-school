/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentMissingSessionUpdateComponent } from 'app/entities/student-missing-session/student-missing-session-update.component';
import { StudentMissingSessionService } from 'app/entities/student-missing-session/student-missing-session.service';
import { StudentMissingSession } from 'app/shared/model/student-missing-session.model';

describe('Component Tests', () => {
    describe('StudentMissingSession Management Update Component', () => {
        let comp: StudentMissingSessionUpdateComponent;
        let fixture: ComponentFixture<StudentMissingSessionUpdateComponent>;
        let service: StudentMissingSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentMissingSessionUpdateComponent]
            })
                .overrideTemplate(StudentMissingSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentMissingSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentMissingSessionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentMissingSession(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentMissingSession = entity;
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
                    const entity = new StudentMissingSession();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentMissingSession = entity;
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
