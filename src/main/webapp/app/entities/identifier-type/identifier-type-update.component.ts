import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IIdentifierType } from 'app/shared/model/identifier-type.model';
import { IdentifierTypeService } from './identifier-type.service';

@Component({
    selector: 'jhi-identifier-type-update',
    templateUrl: './identifier-type-update.component.html'
})
export class IdentifierTypeUpdateComponent implements OnInit {
    identifierType: IIdentifierType;
    isSaving: boolean;

    constructor(protected identifierTypeService: IdentifierTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ identifierType }) => {
            this.identifierType = identifierType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.identifierType.id !== undefined) {
            this.subscribeToSaveResponse(this.identifierTypeService.update(this.identifierType));
        } else {
            this.subscribeToSaveResponse(this.identifierTypeService.create(this.identifierType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentifierType>>) {
        result.subscribe((res: HttpResponse<IIdentifierType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
