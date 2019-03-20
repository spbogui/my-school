/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { LeaveHoliDayUpdateComponent } from 'app/entities/leave-holi-day/leave-holi-day-update.component';
import { LeaveHoliDayService } from 'app/entities/leave-holi-day/leave-holi-day.service';
import { LeaveHoliDay } from 'app/shared/model/leave-holi-day.model';

describe('Component Tests', () => {
    describe('LeaveHoliDay Management Update Component', () => {
        let comp: LeaveHoliDayUpdateComponent;
        let fixture: ComponentFixture<LeaveHoliDayUpdateComponent>;
        let service: LeaveHoliDayService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [LeaveHoliDayUpdateComponent]
            })
                .overrideTemplate(LeaveHoliDayUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LeaveHoliDayUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeaveHoliDayService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LeaveHoliDay(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.leaveHoliDay = entity;
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
                    const entity = new LeaveHoliDay();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.leaveHoliDay = entity;
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
