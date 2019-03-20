/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionTypeDetailComponent } from 'app/entities/class-session-type/class-session-type-detail.component';
import { ClassSessionType } from 'app/shared/model/class-session-type.model';

describe('Component Tests', () => {
    describe('ClassSessionType Management Detail Component', () => {
        let comp: ClassSessionTypeDetailComponent;
        let fixture: ComponentFixture<ClassSessionTypeDetailComponent>;
        const route = ({ data: of({ classSessionType: new ClassSessionType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClassSessionTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassSessionTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.classSessionType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
