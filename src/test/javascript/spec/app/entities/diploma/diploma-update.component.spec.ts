/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { DiplomaUpdateComponent } from 'app/entities/diploma/diploma-update.component';
import { DiplomaService } from 'app/entities/diploma/diploma.service';
import { Diploma } from 'app/shared/model/diploma.model';

describe('Component Tests', () => {
    describe('Diploma Management Update Component', () => {
        let comp: DiplomaUpdateComponent;
        let fixture: ComponentFixture<DiplomaUpdateComponent>;
        let service: DiplomaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [DiplomaUpdateComponent]
            })
                .overrideTemplate(DiplomaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DiplomaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiplomaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Diploma(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.diploma = entity;
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
                    const entity = new Diploma();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.diploma = entity;
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