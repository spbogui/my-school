import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';

type EntityResponseType = HttpResponse<IStudentEvaluation>;
type EntityArrayResponseType = HttpResponse<IStudentEvaluation[]>;

@Injectable({ providedIn: 'root' })
export class StudentEvaluationService {
    public resourceUrl = SERVER_API_URL + 'api/student-evaluations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-evaluations';

    constructor(protected http: HttpClient) {}

    create(studentEvaluation: IStudentEvaluation): Observable<EntityResponseType> {
        return this.http.post<IStudentEvaluation>(this.resourceUrl, studentEvaluation, { observe: 'response' });
    }

    update(studentEvaluation: IStudentEvaluation): Observable<EntityResponseType> {
        return this.http.put<IStudentEvaluation>(this.resourceUrl, studentEvaluation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentEvaluation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentEvaluation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentEvaluation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
