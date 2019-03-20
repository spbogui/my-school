/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { DiplomaDetailComponent } from 'app/entities/diploma/diploma-detail.component';
import { Diploma } from 'app/shared/model/diploma.model';

describe('Component Tests', () => {
    describe('Diploma Management Detail Component', () => {
        let comp: DiplomaDetailComponent;
        let fixture: ComponentFixture<DiplomaDetailComponent>;
        const route = ({ data: of({ diploma: new Diploma(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [DiplomaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiplomaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiplomaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.diploma).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
