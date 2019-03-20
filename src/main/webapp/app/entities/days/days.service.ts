import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDays } from 'app/shared/model/days.model';

type EntityResponseType = HttpResponse<IDays>;
type EntityArrayResponseType = HttpResponse<IDays[]>;

@Injectable({ providedIn: 'root' })
export class DaysService {
    public resourceUrl = SERVER_API_URL + 'api/days';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/days';

    constructor(protected http: HttpClient) {}

    create(days: IDays): Observable<EntityResponseType> {
        return this.http.post<IDays>(this.resourceUrl, days, { observe: 'response' });
    }

    update(days: IDays): Observable<EntityResponseType> {
        return this.http.put<IDays>(this.resourceUrl, days, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDays>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDays[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDays[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
