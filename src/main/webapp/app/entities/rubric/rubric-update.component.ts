import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRubric } from 'app/shared/model/rubric.model';
import { RubricService } from './rubric.service';

@Component({
    selector: 'jhi-rubric-update',
    templateUrl: './rubric-update.component.html'
})
export class RubricUpdateComponent implements OnInit {
    rubric: IRubric;
    isSaving: boolean;

    constructor(protected rubricService: RubricService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rubric }) => {
            this.rubric = rubric;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rubric.id !== undefined) {
            this.subscribeToSaveResponse(this.rubricService.update(this.rubric));
        } else {
            this.subscribeToSaveResponse(this.rubricService.create(this.rubric));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRubric>>) {
        result.subscribe((res: HttpResponse<IRubric>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
