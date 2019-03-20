/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StaffJobUpdateComponent } from 'app/entities/staff-job/staff-job-update.component';
import { StaffJobService } from 'app/entities/staff-job/staff-job.service';
import { StaffJob } from 'app/shared/model/staff-job.model';

describe('Component Tests', () => {
    describe('StaffJob Management Update Component', () => {
        let comp: StaffJobUpdateComponent;
        let fixture: ComponentFixture<StaffJobUpdateComponent>;
        let service: StaffJobService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StaffJobUpdateComponent]
            })
                .overrideTemplate(StaffJobUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StaffJobUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StaffJobService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StaffJob(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staffJob = entity;
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
                    const entity = new StaffJob();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.staffJob = entity;
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
