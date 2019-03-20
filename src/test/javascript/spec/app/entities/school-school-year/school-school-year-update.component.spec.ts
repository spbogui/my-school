/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { SchoolSchoolYearUpdateComponent } from 'app/entities/school-school-year/school-school-year-update.component';
import { SchoolSchoolYearService } from 'app/entities/school-school-year/school-school-year.service';
import { SchoolSchoolYear } from 'app/shared/model/school-school-year.model';

describe('Component Tests', () => {
    describe('SchoolSchoolYear Management Update Component', () => {
        let comp: SchoolSchoolYearUpdateComponent;
        let fixture: ComponentFixture<SchoolSchoolYearUpdateComponent>;
        let service: SchoolSchoolYearService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [SchoolSchoolYearUpdateComponent]
            })
                .overrideTemplate(SchoolSchoolYearUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SchoolSchoolYearUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolSchoolYearService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SchoolSchoolYear(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.schoolSchoolYear = entity;
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
                    const entity = new SchoolSchoolYear();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.schoolSchoolYear = entity;
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
