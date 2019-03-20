/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { IdentifierTypeComponent } from 'app/entities/identifier-type/identifier-type.component';
import { IdentifierTypeService } from 'app/entities/identifier-type/identifier-type.service';
import { IdentifierType } from 'app/shared/model/identifier-type.model';

describe('Component Tests', () => {
    describe('IdentifierType Management Component', () => {
        let comp: IdentifierTypeComponent;
        let fixture: ComponentFixture<IdentifierTypeComponent>;
        let service: IdentifierTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [IdentifierTypeComponent],
                providers: []
            })
                .overrideTemplate(IdentifierTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IdentifierTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdentifierTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IdentifierType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.identifierTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
