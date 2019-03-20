import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClassSessionType } from 'app/shared/model/class-session-type.model';

type EntityResponseType = HttpResponse<IClassSessionType>;
type EntityArrayResponseType = HttpResponse<IClassSessionType[]>;

@Injectable({ providedIn: 'root' })
export class ClassSessionTypeService {
    public resourceUrl = SERVER_API_URL + 'api/class-session-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/class-session-types';

    constructor(protected http: HttpClient) {}

    create(classSessionType: IClassSessionType): Observable<EntityResponseType> {
        return this.http.post<IClassSessionType>(this.resourceUrl, classSessionType, { observe: 'response' });
    }

    update(classSessionType: IClassSessionType): Observable<EntityResponseType> {
        return this.http.put<IClassSessionType>(this.resourceUrl, classSessionType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClassSessionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClassSessionType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClassSessionType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
