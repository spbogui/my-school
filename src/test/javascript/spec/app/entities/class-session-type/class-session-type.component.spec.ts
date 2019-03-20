/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionTypeComponent } from 'app/entities/class-session-type/class-session-type.component';
import { ClassSessionTypeService } from 'app/entities/class-session-type/class-session-type.service';
import { ClassSessionType } from 'app/shared/model/class-session-type.model';

describe('Component Tests', () => {
    describe('ClassSessionType Management Component', () => {
        let comp: ClassSessionTypeComponent;
        let fixture: ComponentFixture<ClassSessionTypeComponent>;
        let service: ClassSessionTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionTypeComponent],
                providers: []
            })
                .overrideTemplate(ClassSessionTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassSessionTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassSessionTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ClassSessionType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.classSessionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
