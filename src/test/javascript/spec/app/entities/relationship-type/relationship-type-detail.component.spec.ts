/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { RelationshipTypeDetailComponent } from 'app/entities/relationship-type/relationship-type-detail.component';
import { RelationshipType } from 'app/shared/model/relationship-type.model';

describe('Component Tests', () => {
    describe('RelationshipType Management Detail Component', () => {
        let comp: RelationshipTypeDetailComponent;
        let fixture: ComponentFixture<RelationshipTypeDetailComponent>;
        const route = ({ data: of({ relationshipType: new RelationshipType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RelationshipTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RelationshipTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RelationshipTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.relationshipType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
