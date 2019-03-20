import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActorName } from 'app/shared/model/actor-name.model';

type EntityResponseType = HttpResponse<IActorName>;
type EntityArrayResponseType = HttpResponse<IActorName[]>;

@Injectable({ providedIn: 'root' })
export class ActorNameService {
    public resourceUrl = SERVER_API_URL + 'api/actor-names';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/actor-names';

    constructor(protected http: HttpClient) {}

    create(actorName: IActorName): Observable<EntityResponseType> {
        return this.http.post<IActorName>(this.resourceUrl, actorName, { observe: 'response' });
    }

    update(actorName: IActorName): Observable<EntityResponseType> {
        return this.http.put<IActorName>(this.resourceUrl, actorName, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActorName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActorName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
