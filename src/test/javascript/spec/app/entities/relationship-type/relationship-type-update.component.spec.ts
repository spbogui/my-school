/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RelationshipTypeUpdateComponent } from 'app/entities/relationship-type/relationship-type-update.component';
import { RelationshipTypeService } from 'app/entities/relationship-type/relationship-type.service';
import { RelationshipType } from 'app/shared/model/relationship-type.model';

describe('Component Tests', () => {
    describe('RelationshipType Management Update Component', () => {
        let comp: RelationshipTypeUpdateComponent;
        let fixture: ComponentFixture<RelationshipTypeUpdateComponent>;
        let service: RelationshipTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RelationshipTypeUpdateComponent]
            })
                .overrideTemplate(RelationshipTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RelationshipTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RelationshipType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.relationshipType = entity;
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
                    const entity = new RelationshipType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.relationshipType = entity;
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
