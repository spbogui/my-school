import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRubricAmount } from 'app/shared/model/rubric-amount.model';

type EntityResponseType = HttpResponse<IRubricAmount>;
type EntityArrayResponseType = HttpResponse<IRubricAmount[]>;

@Injectable({ providedIn: 'root' })
export class RubricAmountService {
    public resourceUrl = SERVER_API_URL + 'api/rubric-amounts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/rubric-amounts';

    constructor(protected http: HttpClient) {}

    create(rubricAmount: IRubricAmount): Observable<EntityResponseType> {
        return this.http.post<IRubricAmount>(this.resourceUrl, rubricAmount, { observe: 'response' });
    }

    update(rubricAmount: IRubricAmount): Observable<EntityResponseType> {
        return this.http.put<IRubricAmount>(this.resourceUrl, rubricAmount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRubricAmount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRubricAmount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRubricAmount[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
