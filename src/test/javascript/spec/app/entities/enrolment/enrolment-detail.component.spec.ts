/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { EnrolmentDetailComponent } from 'app/entities/enrolment/enrolment-detail.component';
import { Enrolment } from 'app/shared/model/enrolment.model';

describe('Component Tests', () => {
    describe('Enrolment Management Detail Component', () => {
        let comp: EnrolmentDetailComponent;
        let fixture: ComponentFixture<EnrolmentDetailComponent>;
        const route = ({ data: of({ enrolment: new Enrolment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [EnrolmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnrolmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnrolmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.enrolment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
