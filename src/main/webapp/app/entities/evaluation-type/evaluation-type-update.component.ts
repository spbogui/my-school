import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEvaluationType } from 'app/shared/model/evaluation-type.model';
import { EvaluationTypeService } from './evaluation-type.service';

@Component({
    selector: 'jhi-evaluation-type-update',
    templateUrl: './evaluation-type-update.component.html'
})
export class EvaluationTypeUpdateComponent implements OnInit {
    evaluationType: IEvaluationType;
    isSaving: boolean;

    constructor(protected evaluationTypeService: EvaluationTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evaluationType }) => {
            this.evaluationType = evaluationType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.evaluationType.id !== undefined) {
            this.subscribeToSaveResponse(this.evaluationTypeService.update(this.evaluationType));
        } else {
            this.subscribeToSaveResponse(this.evaluationTypeService.create(this.evaluationType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationType>>) {
        result.subscribe((res: HttpResponse<IEvaluationType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
