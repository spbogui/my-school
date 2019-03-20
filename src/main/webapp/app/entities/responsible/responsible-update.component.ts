import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResponsible } from 'app/shared/model/responsible.model';
import { ResponsibleService } from './responsible.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';

@Component({
    selector: 'jhi-responsible-update',
    templateUrl: './responsible-update.component.html'
})
export class ResponsibleUpdateComponent implements OnInit {
    responsible: IResponsible;
    isSaving: boolean;

    actors: IActor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected responsibleService: ResponsibleService,
        protected actorService: ActorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ responsible }) => {
            this.responsible = responsible;
        });
        this.actorService
            .query({ filter: 'responsible-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe(
                (res: IActor[]) => {
                    if (!this.responsible.actorId) {
                        this.actors = res;
                    } else {
                        this.actorService
                            .find(this.responsible.actorId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IActor>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IActor>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IActor) => (this.actors = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.responsible.id !== undefined) {
            this.subscribeToSaveResponse(this.responsibleService.update(this.responsible));
        } else {
            this.subscribeToSaveResponse(this.responsibleService.create(this.responsible));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsible>>) {
        result.subscribe((res: HttpResponse<IResponsible>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActorById(index: number, item: IActor) {
        return item.id;
    }
}
