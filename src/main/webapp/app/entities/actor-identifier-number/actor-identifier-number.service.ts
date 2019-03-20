import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActorIdentifierNumber } from 'app/shared/model/actor-identifier-number.model';

type EntityResponseType = HttpResponse<IActorIdentifierNumber>;
type EntityArrayResponseType = HttpResponse<IActorIdentifierNumber[]>;

@Injectable({ providedIn: 'root' })
export class ActorIdentifierNumberService {
    public resourceUrl = SERVER_API_URL + 'api/actor-identifier-numbers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/actor-identifier-numbers';

    constructor(protected http: HttpClient) {}

    create(actorIdentifierNumber: IActorIdentifierNumber): Observable<EntityResponseType> {
        return this.http.post<IActorIdentifierNumber>(this.resourceUrl, actorIdentifierNumber, { observe: 'response' });
    }

    update(actorIdentifierNumber: IActorIdentifierNumber): Observable<EntityResponseType> {
        return this.http.put<IActorIdentifierNumber>(this.resourceUrl, actorIdentifierNumber, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActorIdentifierNumber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorIdentifierNumber[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorIdentifierNumber[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
