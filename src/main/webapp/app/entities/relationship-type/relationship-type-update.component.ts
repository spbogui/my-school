import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type-update',
    templateUrl: './relationship-type-update.component.html'
})
export class RelationshipTypeUpdateComponent implements OnInit {
    relationshipType: IRelationshipType;
    isSaving: boolean;

    constructor(protected relationshipTypeService: RelationshipTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ relationshipType }) => {
            this.relationshipType = relationshipType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.relationshipType.id !== undefined) {
            this.subscribeToSaveResponse(this.relationshipTypeService.update(this.relationshipType));
        } else {
            this.subscribeToSaveResponse(this.relationshipTypeService.create(this.relationshipType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelationshipType>>) {
        result.subscribe((res: HttpResponse<IRelationshipType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
