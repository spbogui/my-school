import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResponsible } from 'app/shared/model/responsible.model';

type EntityResponseType = HttpResponse<IResponsible>;
type EntityArrayResponseType = HttpResponse<IResponsible[]>;

@Injectable({ providedIn: 'root' })
export class ResponsibleService {
    public resourceUrl = SERVER_API_URL + 'api/responsibles';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/responsibles';

    constructor(protected http: HttpClient) {}

    create(responsible: IResponsible): Observable<EntityResponseType> {
        return this.http.post<IResponsible>(this.resourceUrl, responsible, { observe: 'response' });
    }

    update(responsible: IResponsible): Observable<EntityResponseType> {
        return this.http.put<IResponsible>(this.resourceUrl, responsible, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResponsible>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResponsible[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResponsible[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
