import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDiploma } from 'app/shared/model/diploma.model';
import { DiplomaService } from './diploma.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle';

@Component({
    selector: 'jhi-diploma-update',
    templateUrl: './diploma-update.component.html'
})
export class DiplomaUpdateComponent implements OnInit {
    diploma: IDiploma;
    isSaving: boolean;

    cycles: ICycle[];

    diplomas: IDiploma[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected diplomaService: DiplomaService,
        protected cycleService: CycleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ diploma }) => {
            this.diploma = diploma;
        });
        this.cycleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICycle[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICycle[]>) => response.body)
            )
            .subscribe((res: ICycle[]) => (this.cycles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.diplomaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDiploma[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDiploma[]>) => response.body)
            )
            .subscribe((res: IDiploma[]) => (this.diplomas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.diploma.id !== undefined) {
            this.subscribeToSaveResponse(this.diplomaService.update(this.diploma));
        } else {
            this.subscribeToSaveResponse(this.diplomaService.create(this.diploma));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiploma>>) {
        result.subscribe((res: HttpResponse<IDiploma>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCycleById(index: number, item: ICycle) {
        return item.id;
    }

    trackDiplomaById(index: number, item: IDiploma) {
        return item.id;
    }
}
