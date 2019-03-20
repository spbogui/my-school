/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { AskingPermissionDetailComponent } from 'app/entities/asking-permission/asking-permission-detail.component';
import { AskingPermission } from 'app/shared/model/asking-permission.model';

describe('Component Tests', () => {
    describe('AskingPermission Management Detail Component', () => {
        let comp: AskingPermissionDetailComponent;
        let fixture: ComponentFixture<AskingPermissionDetailComponent>;
        const route = ({ data: of({ askingPermission: new AskingPermission(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [AskingPermissionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AskingPermissionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AskingPermissionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.askingPermission).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
