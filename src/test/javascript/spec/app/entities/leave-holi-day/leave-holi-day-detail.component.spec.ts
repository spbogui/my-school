/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { LeaveHoliDayDetailComponent } from 'app/entities/leave-holi-day/leave-holi-day-detail.component';
import { LeaveHoliDay } from 'app/shared/model/leave-holi-day.model';

describe('Component Tests', () => {
    describe('LeaveHoliDay Management Detail Component', () => {
        let comp: LeaveHoliDayDetailComponent;
        let fixture: ComponentFixture<LeaveHoliDayDetailComponent>;
        const route = ({ data: of({ leaveHoliDay: new LeaveHoliDay(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [LeaveHoliDayDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LeaveHoliDayDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LeaveHoliDayDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.leaveHoliDay).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
