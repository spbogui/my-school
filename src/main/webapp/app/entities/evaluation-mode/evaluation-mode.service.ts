import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';

type EntityResponseType = HttpResponse<IEvaluationMode>;
type EntityArrayResponseType = HttpResponse<IEvaluationMode[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationModeService {
    public resourceUrl = SERVER_API_URL + 'api/evaluation-modes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/evaluation-modes';

    constructor(protected http: HttpClient) {}

    create(evaluationMode: IEvaluationMode): Observable<EntityResponseType> {
        return this.http.post<IEvaluationMode>(this.resourceUrl, evaluationMode, { observe: 'response' });
    }

    update(evaluationMode: IEvaluationMode): Observable<EntityResponseType> {
        return this.http.put<IEvaluationMode>(this.resourceUrl, evaluationMode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEvaluationMode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEvaluationMode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEvaluationMode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
