/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySchoolTestModule } from '../../../test.module';
import { RoomTypeComponent } from 'app/entities/room-type/room-type.component';
import { RoomTypeService } from 'app/entities/room-type/room-type.service';
import { RoomType } from 'app/shared/model/room-type.model';

describe('Component Tests', () => {
    describe('RoomType Management Component', () => {
        let comp: RoomTypeComponent;
        let fixture: ComponentFixture<RoomTypeComponent>;
        let service: RoomTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [RoomTypeComponent],
                providers: []
            })
                .overrideTemplate(RoomTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoomTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RoomType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.roomTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
