/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { RelationshipTypeComponent } from 'app/entities/relationship-type/relationship-type.component';
import { RelationshipTypeService } from 'app/entities/relationship-type/relationship-type.service';
import { RelationshipType } from 'app/shared/model/relationship-type.model';

describe('Component Tests', () => {
    describe('RelationshipType Management Component', () => {
        let comp: RelationshipTypeComponent;
        let fixture: ComponentFixture<RelationshipTypeComponent>;
        let service: RelationshipTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RelationshipTypeComponent],
                providers: []
            })
                .overrideTemplate(RelationshipTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RelationshipTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RelationshipTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RelationshipType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.relationshipTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
