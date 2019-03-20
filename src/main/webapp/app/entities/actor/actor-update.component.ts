import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from './actor.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';

@Component({
    selector: 'jhi-actor-update',
    templateUrl: './actor-update.component.html'
})
export class ActorUpdateComponent implements OnInit {
    actor: IActor;
    isSaving: boolean;

    countries: ICountry[];
    birthDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actorService: ActorService,
        protected countryService: CountryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actor }) => {
            this.actor = actor;
        });
        this.countryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICountry[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICountry[]>) => response.body)
            )
            .subscribe((res: ICountry[]) => (this.countries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actor.id !== undefined) {
            this.subscribeToSaveResponse(this.actorService.update(this.actor));
        } else {
            this.subscribeToSaveResponse(this.actorService.create(this.actor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActor>>) {
        result.subscribe((res: HttpResponse<IActor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCountryById(index: number, item: ICountry) {
        return item.id;
    }
}
