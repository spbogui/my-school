/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionTypeUpdateComponent } from 'app/entities/class-session-type/class-session-type-update.component';
import { ClassSessionTypeService } from 'app/entities/class-session-type/class-session-type.service';
import { ClassSessionType } from 'app/shared/model/class-session-type.model';

describe('Component Tests', () => {
    describe('ClassSessionType Management Update Component', () => {
        let comp: ClassSessionTypeUpdateComponent;
        let fixture: ComponentFixture<ClassSessionTypeUpdateComponent>;
        let service: ClassSessionTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionTypeUpdateComponent]
            })
                .overrideTemplate(ClassSessionTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassSessionTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassSessionTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClassSessionType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classSessionType = entity;
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
                    const entity = new ClassSessionType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classSessionType = entity;
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
