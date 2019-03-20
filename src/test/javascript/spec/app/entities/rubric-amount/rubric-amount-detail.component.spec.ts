/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RubricAmountDetailComponent } from 'app/entities/rubric-amount/rubric-amount-detail.component';
import { RubricAmount } from 'app/shared/model/rubric-amount.model';

describe('Component Tests', () => {
    describe('RubricAmount Management Detail Component', () => {
        let comp: RubricAmountDetailComponent;
        let fixture: ComponentFixture<RubricAmountDetailComponent>;
        const route = ({ data: of({ rubricAmount: new RubricAmount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricAmountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RubricAmountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RubricAmountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rubricAmount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
