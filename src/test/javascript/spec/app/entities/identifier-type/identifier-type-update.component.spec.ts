/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { IdentifierTypeUpdateComponent } from 'app/entities/identifier-type/identifier-type-update.component';
import { IdentifierTypeService } from 'app/entities/identifier-type/identifier-type.service';
import { IdentifierType } from 'app/shared/model/identifier-type.model';

describe('Component Tests', () => {
    describe('IdentifierType Management Update Component', () => {
        let comp: IdentifierTypeUpdateComponent;
        let fixture: ComponentFixture<IdentifierTypeUpdateComponent>;
        let service: IdentifierTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [IdentifierTypeUpdateComponent]
            })
                .overrideTemplate(IdentifierTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IdentifierTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentifierTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IdentifierType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.identifierType = entity;
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
                    const entity = new IdentifierType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.identifierType = entity;
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
