/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { PermissionAgreementUpdateComponent } from 'app/entities/permission-agreement/permission-agreement-update.component';
import { PermissionAgreementService } from 'app/entities/permission-agreement/permission-agreement.service';
import { PermissionAgreement } from 'app/shared/model/permission-agreement.model';

describe('Component Tests', () => {
    describe('PermissionAgreement Management Update Component', () => {
        let comp: PermissionAgreementUpdateComponent;
        let fixture: ComponentFixture<PermissionAgreementUpdateComponent>;
        let service: PermissionAgreementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [PermissionAgreementUpdateComponent]
            })
                .overrideTemplate(PermissionAgreementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PermissionAgreementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionAgreementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PermissionAgreement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.permissionAgreement = entity;
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
                    const entity = new PermissionAgreement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.permissionAgreement = entity;
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
