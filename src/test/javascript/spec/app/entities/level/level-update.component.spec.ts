/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { LevelUpdateComponent } from 'app/entities/level/level-update.component';
import { LevelService } from 'app/entities/level/level.service';
import { Level } from 'app/shared/model/level.model';

describe('Component Tests', () => {
    describe('Level Management Update Component', () => {
        let comp: LevelUpdateComponent;
        let fixture: ComponentFixture<LevelUpdateComponent>;
        let service: LevelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [LevelUpdateComponent]
            })
                .overrideTemplate(LevelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LevelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LevelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Level(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.level = entity;
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
                    const entity = new Level();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.level = entity;
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
