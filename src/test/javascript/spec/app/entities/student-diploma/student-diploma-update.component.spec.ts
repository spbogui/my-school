/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StudentDiplomaUpdateComponent } from 'app/entities/student-diploma/student-diploma-update.component';
import { StudentDiplomaService } from 'app/entities/student-diploma/student-diploma.service';
import { StudentDiploma } from 'app/shared/model/student-diploma.model';

describe('Component Tests', () => {
    describe('StudentDiploma Management Update Component', () => {
        let comp: StudentDiplomaUpdateComponent;
        let fixture: ComponentFixture<StudentDiplomaUpdateComponent>;
        let service: StudentDiplomaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StudentDiplomaUpdateComponent]
            })
                .overrideTemplate(StudentDiplomaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentDiplomaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentDiplomaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentDiploma(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentDiploma = entity;
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
                    const entity = new StudentDiploma();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentDiploma = entity;
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
