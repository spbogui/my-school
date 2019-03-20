import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDays } from 'app/shared/model/days.model';
import { DaysService } from './days.service';

@Component({
    selector: 'jhi-days-update',
    templateUrl: './days-update.component.html'
})
export class DaysUpdateComponent implements OnInit {
    days: IDays;
    isSaving: boolean;

    constructor(protected daysService: DaysService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ days }) => {
            this.days = days;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.days.id !== undefined) {
            this.subscribeToSaveResponse(this.daysService.update(this.days));
        } else {
            this.subscribeToSaveResponse(this.daysService.create(this.days));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDays>>) {
        result.subscribe((res: HttpResponse<IDays>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
