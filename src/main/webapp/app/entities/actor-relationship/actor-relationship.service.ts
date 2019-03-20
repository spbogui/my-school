import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActorRelationship } from 'app/shared/model/actor-relationship.model';

type EntityResponseType = HttpResponse<IActorRelationship>;
type EntityArrayResponseType = HttpResponse<IActorRelationship[]>;

@Injectable({ providedIn: 'root' })
export class ActorRelationshipService {
    public resourceUrl = SERVER_API_URL + 'api/actor-relationships';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/actor-relationships';

    constructor(protected http: HttpClient) {}

    create(actorRelationship: IActorRelationship): Observable<EntityResponseType> {
        return this.http.post<IActorRelationship>(this.resourceUrl, actorRelationship, { observe: 'response' });
    }

    update(actorRelationship: IActorRelationship): Observable<EntityResponseType> {
        return this.http.put<IActorRelationship>(this.resourceUrl, actorRelationship, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActorRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorRelationship[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorRelationship[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
