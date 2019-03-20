/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorIdentifierNumberDetailComponent } from 'app/entities/actor-identifier-number/actor-identifier-number-detail.component';
import { ActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';

describe('Component Tests', () => {
    describe('ActorIdentifierNumber Management Detail Component', () => {
        let comp: ActorIdentifierNumberDetailComponent;
        let fixture: ComponentFixture<ActorIdentifierNumberDetailComponent>;
        const route = ({ data: of({ actorIdentifierNumber: new ActorIdentifierNumber(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorIdentifierNumberDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActorIdentifierNumberDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActorIdentifierNumberDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actorIdentifierNumber).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
