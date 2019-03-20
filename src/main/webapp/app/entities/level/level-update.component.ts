import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from './level.service';
import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from 'app/entities/cycle';

@Component({
    selector: 'jhi-level-update',
    templateUrl: './level-update.component.html'
})
export class LevelUpdateComponent implements OnInit {
    level: ILevel;
    isSaving: boolean;

    levels: ILevel[];

    cycles: ICycle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected levelService: LevelService,
        protected cycleService: CycleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ level }) => {
            this.level = level;
        });
        this.levelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILevel[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILevel[]>) => response.body)
            )
            .subscribe((res: ILevel[]) => (this.levels = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.cycleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICycle[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICycle[]>) => response.body)
            )
            .subscribe((res: ICycle[]) => (this.cycles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.level.id !== undefined) {
            this.subscribeToSaveResponse(this.levelService.update(this.level));
        } else {
            this.subscribeToSaveResponse(this.levelService.create(this.level));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevel>>) {
        result.subscribe((res: HttpResponse<ILevel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLevelById(index: number, item: ILevel) {
        return item.id;
    }

    trackCycleById(index: number, item: ICycle) {
        return item.id;
    }
}
