import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IActorRelationship } from 'app/shared/model/actor-relationship.model';
import { ActorRelationshipService } from './actor-relationship.service';
import { IResponsible } from 'app/shared/model/responsible.model';
import { ResponsibleService } from 'app/entities/responsible';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';
import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { RelationshipTypeService } from 'app/entities/relationship-type';

@Component({
    selector: 'jhi-actor-relationship-update',
    templateUrl: './actor-relationship-update.component.html'
})
export class ActorRelationshipUpdateComponent implements OnInit {
    actorRelationship: IActorRelationship;
    isSaving: boolean;

    responsibles: IResponsible[];

    students: IStudent[];

    relationshiptypes: IRelationshipType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actorRelationshipService: ActorRelationshipService,
        protected responsibleService: ResponsibleService,
        protected studentService: StudentService,
        protected relationshipTypeService: RelationshipTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actorRelationship }) => {
            this.actorRelationship = actorRelationship;
        });
        this.responsibleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IResponsible[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResponsible[]>) => response.body)
            )
            .subscribe((res: IResponsible[]) => (this.responsibles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.studentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStudent[]>) => response.body)
            )
            .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.relationshipTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRelationshipType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRelationshipType[]>) => response.body)
            )
            .subscribe((res: IRelationshipType[]) => (this.relationshiptypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actorRelationship.id !== undefined) {
            this.subscribeToSaveResponse(this.actorRelationshipService.update(this.actorRelationship));
        } else {
            this.subscribeToSaveResponse(this.actorRelationshipService.create(this.actorRelationship));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActorRelationship>>) {
        result.subscribe((res: HttpResponse<IActorRelationship>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackResponsibleById(index: number, item: IResponsible) {
        return item.id;
    }

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }

    trackRelationshipTypeById(index: number, item: IRelationshipType) {
        return item.id;
    }
}
