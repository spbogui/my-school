import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from './period-type.service';

@Component({
    selector: 'jhi-period-type-update',
    templateUrl: './period-type-update.component.html'
})
export class PeriodTypeUpdateComponent implements OnInit {
    periodType: IPeriodType;
    isSaving: boolean;

    constructor(protected periodTypeService: PeriodTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ periodType }) => {
            this.periodType = periodType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.periodType.id !== undefined) {
            this.subscribeToSaveResponse(this.periodTypeService.update(this.periodType));
        } else {
            this.subscribeToSaveResponse(this.periodTypeService.create(this.periodType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodType>>) {
        result.subscribe((res: HttpResponse<IPeriodType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
