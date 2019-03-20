import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRelationshipType } from 'app/shared/model/relationship-type.model';

type EntityResponseType = HttpResponse<IRelationshipType>;
type EntityArrayResponseType = HttpResponse<IRelationshipType[]>;

@Injectable({ providedIn: 'root' })
export class RelationshipTypeService {
    public resourceUrl = SERVER_API_URL + 'api/relationship-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/relationship-types';

    constructor(protected http: HttpClient) {}

    create(relationshipType: IRelationshipType): Observable<EntityResponseType> {
        return this.http.post<IRelationshipType>(this.resourceUrl, relationshipType, { observe: 'response' });
    }

    update(relationshipType: IRelationshipType): Observable<EntityResponseType> {
        return this.http.put<IRelationshipType>(this.resourceUrl, relationshipType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRelationshipType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRelationshipType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRelationshipType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
