/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorIdentifierNumberUpdateComponent } from 'app/entities/actor-identifier-number/actor-identifier-number-update.component';
import { ActorIdentifierNumberService } from 'app/entities/actor-identifier-number/actor-identifier-number.service';
import { ActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';

describe('Component Tests', () => {
    describe('ActorIdentifierNumber Management Update Component', () => {
        let comp: ActorIdentifierNumberUpdateComponent;
        let fixture: ComponentFixture<ActorIdentifierNumberUpdateComponent>;
        let service: ActorIdentifierNumberService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorIdentifierNumberUpdateComponent]
            })
                .overrideTemplate(ActorIdentifierNumberUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActorIdentifierNumberUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActorIdentifierNumberService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActorIdentifierNumber(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorIdentifierNumber = entity;
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
                    const entity = new ActorIdentifierNumber();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorIdentifierNumber = entity;
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
