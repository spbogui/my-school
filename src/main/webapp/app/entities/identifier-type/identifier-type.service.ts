import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIdentifierType } from 'app/shared/model/identifier-type.model';

type EntityResponseType = HttpResponse<IIdentifierType>;
type EntityArrayResponseType = HttpResponse<IIdentifierType[]>;

@Injectable({ providedIn: 'root' })
export class IdentifierTypeService {
    public resourceUrl = SERVER_API_URL + 'api/identifier-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/identifier-types';

    constructor(protected http: HttpClient) {}

    create(identifierType: IIdentifierType): Observable<EntityResponseType> {
        return this.http.post<IIdentifierType>(this.resourceUrl, identifierType, { observe: 'response' });
    }

    update(identifierType: IIdentifierType): Observable<EntityResponseType> {
        return this.http.put<IIdentifierType>(this.resourceUrl, identifierType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIdentifierType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIdentifierType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIdentifierType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
