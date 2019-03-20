/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { IdentifierTypeDetailComponent } from 'app/entities/identifier-type/identifier-type-detail.component';
import { IdentifierType } from 'app/shared/model/identifier-type.model';

describe('Component Tests', () => {
    describe('IdentifierType Management Detail Component', () => {
        let comp: IdentifierTypeDetailComponent;
        let fixture: ComponentFixture<IdentifierTypeDetailComponent>;
        const route = ({ data: of({ identifierType: new IdentifierType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [IdentifierTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IdentifierTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IdentifierTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.identifierType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
