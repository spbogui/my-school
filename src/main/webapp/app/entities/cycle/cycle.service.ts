import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICycle } from 'app/shared/model/cycle.model';

type EntityResponseType = HttpResponse<ICycle>;
type EntityArrayResponseType = HttpResponse<ICycle[]>;

@Injectable({ providedIn: 'root' })
export class CycleService {
    public resourceUrl = SERVER_API_URL + 'api/cycles';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cycles';

    constructor(protected http: HttpClient) {}

    create(cycle: ICycle): Observable<EntityResponseType> {
        return this.http.post<ICycle>(this.resourceUrl, cycle, { observe: 'response' });
    }

    update(cycle: ICycle): Observable<EntityResponseType> {
        return this.http.put<ICycle>(this.resourceUrl, cycle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICycle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICycle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICycle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
