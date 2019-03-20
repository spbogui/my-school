import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IActorName } from 'app/shared/model/actor-name.model';
import { ActorNameService } from './actor-name.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';

@Component({
    selector: 'jhi-actor-name-update',
    templateUrl: './actor-name-update.component.html'
})
export class ActorNameUpdateComponent implements OnInit {
    actorName: IActorName;
    isSaving: boolean;

    actors: IActor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actorNameService: ActorNameService,
        protected actorService: ActorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actorName }) => {
            this.actorName = actorName;
        });
        this.actorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe((res: IActor[]) => (this.actors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actorName.id !== undefined) {
            this.subscribeToSaveResponse(this.actorNameService.update(this.actorName));
        } else {
            this.subscribeToSaveResponse(this.actorNameService.create(this.actorName));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActorName>>) {
        result.subscribe((res: HttpResponse<IActorName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
