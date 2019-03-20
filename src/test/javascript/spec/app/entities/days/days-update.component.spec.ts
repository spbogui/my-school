/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { DaysUpdateComponent } from 'app/entities/days/days-update.component';
import { DaysService } from 'app/entities/days/days.service';
import { Days } from 'app/shared/model/days.model';

describe('Component Tests', () => {
    describe('Days Management Update Component', () => {
        let comp: DaysUpdateComponent;
        let fixture: ComponentFixture<DaysUpdateComponent>;
        let service: DaysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [DaysUpdateComponent]
            })
                .overrideTemplate(DaysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DaysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DaysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Days(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.days = entity;
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
                    const entity = new Days();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.days = entity;
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
