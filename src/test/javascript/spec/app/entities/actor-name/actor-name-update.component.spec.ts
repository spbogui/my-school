/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorNameUpdateComponent } from 'app/entities/actor-name/actor-name-update.component';
import { ActorNameService } from 'app/entities/actor-name/actor-name.service';
import { ActorName } from 'app/shared/model/actor-name.model';

describe('Component Tests', () => {
    describe('ActorName Management Update Component', () => {
        let comp: ActorNameUpdateComponent;
        let fixture: ComponentFixture<ActorNameUpdateComponent>;
        let service: ActorNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorNameUpdateComponent]
            })
                .overrideTemplate(ActorNameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActorNameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActorNameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActorName(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorName = entity;
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
                    const entity = new ActorName();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actorName = entity;
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
