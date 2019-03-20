/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { SchoolYearUpdateComponent } from 'app/entities/school-year/school-year-update.component';
import { SchoolYearService } from 'app/entities/school-year/school-year.service';
import { SchoolYear } from 'app/shared/model/school-year.model';

describe('Component Tests', () => {
    describe('SchoolYear Management Update Component', () => {
        let comp: SchoolYearUpdateComponent;
        let fixture: ComponentFixture<SchoolYearUpdateComponent>;
        let service: SchoolYearService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [SchoolYearUpdateComponent]
            })
                .overrideTemplate(SchoolYearUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SchoolYearUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SchoolYear(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.schoolYear = entity;
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
                    const entity = new SchoolYear();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.schoolYear = entity;
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
