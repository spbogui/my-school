import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';
import { EvaluationModeService } from './evaluation-mode.service';

@Component({
    selector: 'jhi-evaluation-mode-update',
    templateUrl: './evaluation-mode-update.component.html'
})
export class EvaluationModeUpdateComponent implements OnInit {
    evaluationMode: IEvaluationMode;
    isSaving: boolean;

    constructor(protected evaluationModeService: EvaluationModeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evaluationMode }) => {
            this.evaluationMode = evaluationMode;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.evaluationMode.id !== undefined) {
            this.subscribeToSaveResponse(this.evaluationModeService.update(this.evaluationMode));
        } else {
            this.subscribeToSaveResponse(this.evaluationModeService.create(this.evaluationMode));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationMode>>) {
        result.subscribe((res: HttpResponse<IEvaluationMode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
