/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ClassRoomUpdateComponent } from 'app/entities/class-room/class-room-update.component';
import { ClassRoomService } from 'app/entities/class-room/class-room.service';
import { ClassRoom } from 'app/shared/model/class-room.model';

describe('Component Tests', () => {
    describe('ClassRoom Management Update Component', () => {
        let comp: ClassRoomUpdateComponent;
        let fixture: ComponentFixture<ClassRoomUpdateComponent>;
        let service: ClassRoomService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassRoomUpdateComponent]
            })
                .overrideTemplate(ClassRoomUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassRoomUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassRoomService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClassRoom(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classRoom = entity;
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
                    const entity = new ClassRoom();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.classRoom = entity;
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
