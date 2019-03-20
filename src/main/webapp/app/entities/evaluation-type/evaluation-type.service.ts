import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvaluationType } from 'app/shared/model/evaluation-type.model';

type EntityResponseType = HttpResponse<IEvaluationType>;
type EntityArrayResponseType = HttpResponse<IEvaluationType[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationTypeService {
    public resourceUrl = SERVER_API_URL + 'api/evaluation-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/evaluation-types';

    constructor(protected http: HttpClient) {}

    create(evaluationType: IEvaluationType): Observable<EntityResponseType> {
        return this.http.post<IEvaluationType>(this.resourceUrl, evaluationType, { observe: 'response' });
    }

    update(evaluationType: IEvaluationType): Observable<EntityResponseType> {
        return this.http.put<IEvaluationType>(this.resourceUrl, evaluationType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEvaluationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEvaluationType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEvaluationType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
