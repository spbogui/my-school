/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionUpdateComponent } from 'app/entities/class-session/class-session-update.component';
import { ClassSessionService } from 'app/entities/class-session/class-session.service';
import { ClassSession } from 'app/shared/model/class-session.model';

describe('Component Tests', () => {
    describe('ClassSession Management Update Component', () => {
        let comp: ClassSessionUpdateComponent;
        let fixture: ComponentFixture<ClassSessionUpdateComponent>;
        let service: ClassSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionUpdateComponent]
            })
                .overrideTemplate(ClassSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassSessionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClassSession(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classSession = entity;
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
                    const entity = new ClassSession();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classSession = entity;
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
