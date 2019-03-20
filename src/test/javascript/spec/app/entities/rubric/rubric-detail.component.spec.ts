/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RubricDetailComponent } from 'app/entities/rubric/rubric-detail.component';
import { Rubric } from 'app/shared/model/rubric.model';

describe('Component Tests', () => {
    describe('Rubric Management Detail Component', () => {
        let comp: RubricDetailComponent;
        let fixture: ComponentFixture<RubricDetailComponent>;
        const route = ({ data: of({ rubric: new Rubric(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RubricDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RubricDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RubricDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rubric).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
