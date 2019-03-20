import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';
import { ActorIdentifierNumberService } from './actor-identifier-number.service';
import { IIdentifierType } from 'app/shared/model/identifier-type.model';
import { IdentifierTypeService } from 'app/entities/identifier-type';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';

@Component({
    selector: 'jhi-actor-identifier-number-update',
    templateUrl: './actor-identifier-number-update.component.html'
})
export class ActorIdentifierNumberUpdateComponent implements OnInit {
    actorIdentifierNumber: IActorIdentifierNumber;
    isSaving: boolean;

    identifiertypes: IIdentifierType[];

    actors: IActor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actorIdentifierNumberService: ActorIdentifierNumberService,
        protected identifierTypeService: IdentifierTypeService,
        protected actorService: ActorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actorIdentifierNumber }) => {
            this.actorIdentifierNumber = actorIdentifierNumber;
        });
        this.identifierTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIdentifierType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIdentifierType[]>) => response.body)
            )
            .subscribe((res: IIdentifierType[]) => (this.identifiertypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.actorIdentifierNumber.id !== undefined) {
            this.subscribeToSaveResponse(this.actorIdentifierNumberService.update(this.actorIdentifierNumber));
        } else {
            this.subscribeToSaveResponse(this.actorIdentifierNumberService.create(this.actorIdentifierNumber));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActorIdentifierNumber>>) {
        result.subscribe(
            (res: HttpResponse<IActorIdentifierNumber>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackIdentifierTypeById(index: number, item: IIdentifierType) {
        return item.id;
    }

    trackActorById(index: number, item: IActor) {
        return item.id;
    }
}
