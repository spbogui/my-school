/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MySchoolTestModule } from '../../../test.module';
import { ActorRelationshipDeleteDialogComponent } from 'app/entities/actor-relationship/actor-relationship-delete-dialog.component';
import { ActorRelationshipService } from 'app/entities/actor-relationship/actor-relationship.service';

describe('Component Tests', () => {
    describe('ActorRelationship Management Delete Component', () => {
        let comp: ActorRelationshipDeleteDialogComponent;
        let fixture: ComponentFixture<ActorRelationshipDeleteDialogComponent>;
        let service: ActorRelationshipService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MySchoolTestModule],
                declarations: [ActorRelationshipDeleteDialogComponent]
            })
                .overrideTemplate(ActorRelationshipDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActorRelationshipDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActorRelationshipService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
