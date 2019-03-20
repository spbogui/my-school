/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySchoolTestModule } from '../../../test.module';
import { ActorNameDetailComponent } from 'app/entities/actor-name/actor-name-detail.component';
import { ActorName } from 'app/shared/model/actor-name.model';

describe('Component Tests', () => {
    describe('ActorName Management Detail Component', () => {
        let comp: ActorNameDetailComponent;
        let fixture: ComponentFixture<ActorNameDetailComponent>;
        const route = ({ data: of({ actorName: new ActorName(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorNameDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActorNameDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActorNameDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actorName).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
