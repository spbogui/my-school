import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';

@Component({
    selector: 'jhi-cycle-update',
    templateUrl: './cycle-update.component.html'
})
export class CycleUpdateComponent implements OnInit {
    cycle: ICycle;
    isSaving: boolean;

    constructor(protected cycleService: CycleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cycle }) => {
            this.cycle = cycle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cycle.id !== undefined) {
            this.subscribeToSaveResponse(this.cycleService.update(this.cycle));
        } else {
            this.subscribeToSaveResponse(this.cycleService.create(this.cycle));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycle>>) {
        result.subscribe((res: HttpResponse<ICycle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
