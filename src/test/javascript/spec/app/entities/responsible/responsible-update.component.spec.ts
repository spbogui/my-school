/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ResponsibleUpdateComponent } from 'app/entities/responsible/responsible-update.component';
import { ResponsibleService } from 'app/entities/responsible/responsible.service';
import { Responsible } from 'app/shared/model/responsible.model';

describe('Component Tests', () => {
    describe('Responsible Management Update Component', () => {
        let comp: ResponsibleUpdateComponent;
        let fixture: ComponentFixture<ResponsibleUpdateComponent>;
        let service: ResponsibleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ResponsibleUpdateComponent]
            })
                .overrideTemplate(ResponsibleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResponsibleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResponsibleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Responsible(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.responsible = entity;
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
                    const entity = new Responsible();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.responsible = entity;
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
