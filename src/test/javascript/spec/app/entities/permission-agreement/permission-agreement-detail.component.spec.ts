/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { PermissionAgreementDetailComponent } from 'app/entities/permission-agreement/permission-agreement-detail.component';
import { PermissionAgreement } from 'app/shared/model/permission-agreement.model';

describe('Component Tests', () => {
    describe('PermissionAgreement Management Detail Component', () => {
        let comp: PermissionAgreementDetailComponent;
        let fixture: ComponentFixture<PermissionAgreementDetailComponent>;
        const route = ({ data: of({ permissionAgreement: new PermissionAgreement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [PermissionAgreementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PermissionAgreementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PermissionAgreementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.permissionAgreement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
