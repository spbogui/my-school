/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorRelationshipUpdateComponent } from 'app/entities/actor-relationship/actor-relationship-update.component';
import { ActorRelationshipService } from 'app/entities/actor-relationship/actor-relationship.service';
import { ActorRelationship } from 'app/shared/model/actor-relationship.model';

describe('Component Tests', () => {
    describe('ActorRelationship Management Update Component', () => {
        let comp: ActorRelationshipUpdateComponent;
        let fixture: ComponentFixture<ActorRelationshipUpdateComponent>;
        let service: ActorRelationshipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorRelationshipUpdateComponent]
            })
                .overrideTemplate(ActorRelationshipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActorRelationshipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActorRelationshipService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActorRelationship(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorRelationship = entity;
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
                    const entity = new ActorRelationship();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorRelationship = entity;
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
