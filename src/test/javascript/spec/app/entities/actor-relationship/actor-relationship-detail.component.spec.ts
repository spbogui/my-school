/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorRelationshipDetailComponent } from 'app/entities/actor-relationship/actor-relationship-detail.component';
import { ActorRelationship } from 'app/shared/model/actor-relationship.model';

describe('Component Tests', () => {
    describe('ActorRelationship Management Detail Component', () => {
        let comp: ActorRelationshipDetailComponent;
        let fixture: ComponentFixture<ActorRelationshipDetailComponent>;
        const route = ({ data: of({ actorRelationship: new ActorRelationship(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorRelationshipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActorRelationshipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActorRelationshipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actorRelationship).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
