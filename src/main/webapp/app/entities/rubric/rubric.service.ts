import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRubric } from 'app/shared/model/rubric.model';

type EntityResponseType = HttpResponse<IRubric>;
type EntityArrayResponseType = HttpResponse<IRubric[]>;

@Injectable({ providedIn: 'root' })
export class RubricService {
    public resourceUrl = SERVER_API_URL + 'api/rubrics';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/rubrics';

    constructor(protected http: HttpClient) {}

    create(rubric: IRubric): Observable<EntityResponseType> {
        return this.http.post<IRubric>(this.resourceUrl, rubric, { observe: 'response' });
    }

    update(rubric: IRubric): Observable<EntityResponseType> {
        return this.http.put<IRubric>(this.resourceUrl, rubric, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRubric>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRubric[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRubric[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
