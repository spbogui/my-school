/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ResponsibleDetailComponent } from 'app/entities/responsible/responsible-detail.component';
import { Responsible } from 'app/shared/model/responsible.model';

describe('Component Tests', () => {
    describe('Responsible Management Detail Component', () => {
        let comp: ResponsibleDetailComponent;
        let fixture: ComponentFixture<ResponsibleDetailComponent>;
        const route = ({ data: of({ responsible: new Responsible(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ResponsibleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResponsibleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResponsibleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.responsible).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
