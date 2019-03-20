/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { StaffJobDetailComponent } from 'app/entities/staff-job/staff-job-detail.component';
import { StaffJob } from 'app/shared/model/staff-job.model';

describe('Component Tests', () => {
    describe('StaffJob Management Detail Component', () => {
        let comp: StaffJobDetailComponent;
        let fixture: ComponentFixture<StaffJobDetailComponent>;
        const route = ({ data: of({ staffJob: new StaffJob(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [StaffJobDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StaffJobDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StaffJobDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.staffJob).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
