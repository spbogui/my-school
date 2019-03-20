/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { AskingPermissionUpdateComponent } from 'app/entities/asking-permission/asking-permission-update.component';
import { AskingPermissionService } from 'app/entities/asking-permission/asking-permission.service';
import { AskingPermission } from 'app/shared/model/asking-permission.model';

describe('Component Tests', () => {
    describe('AskingPermission Management Update Component', () => {
        let comp: AskingPermissionUpdateComponent;
        let fixture: ComponentFixture<AskingPermissionUpdateComponent>;
        let service: AskingPermissionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [AskingPermissionUpdateComponent]
            })
                .overrideTemplate(AskingPermissionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AskingPermissionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AskingPermissionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AskingPermission(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.askingPermission = entity;
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
                    const entity = new AskingPermission();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.askingPermission = entity;
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
