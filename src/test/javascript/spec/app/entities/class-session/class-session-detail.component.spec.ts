/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ClassSessionDetailComponent } from 'app/entities/class-session/class-session-detail.component';
import { ClassSession } from 'app/shared/model/class-session.model';

describe('Component Tests', () => {
    describe('ClassSession Management Detail Component', () => {
        let comp: ClassSessionDetailComponent;
        let fixture: ComponentFixture<ClassSessionDetailComponent>;
        const route = ({ data: of({ classSession: new ClassSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ClassSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClassSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.classSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
