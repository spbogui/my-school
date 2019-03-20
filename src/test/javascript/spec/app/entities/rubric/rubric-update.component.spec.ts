/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RubricUpdateComponent } from 'app/entities/rubric/rubric-update.component';
import { RubricService } from 'app/entities/rubric/rubric.service';
import { Rubric } from 'app/shared/model/rubric.model';

describe('Component Tests', () => {
    describe('Rubric Management Update Component', () => {
        let comp: RubricUpdateComponent;
        let fixture: ComponentFixture<RubricUpdateComponent>;
        let service: RubricService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricUpdateComponent]
            })
                .overrideTemplate(RubricUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RubricUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubricService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Rubric(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rubric = entity;
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
                    const entity = new Rubric();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rubric = entity;
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
