import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentMissingSession } from 'app/shared/model/student-missing-session.model';

type EntityResponseType = HttpResponse<IStudentMissingSession>;
type EntityArrayResponseType = HttpResponse<IStudentMissingSession[]>;

@Injectable({ providedIn: 'root' })
export class StudentMissingSessionService {
    public resourceUrl = SERVER_API_URL + 'api/student-missing-sessions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-missing-sessions';

    constructor(protected http: HttpClient) {}

    create(studentMissingSession: IStudentMissingSession): Observable<EntityResponseType> {
        return this.http.post<IStudentMissingSession>(this.resourceUrl, studentMissingSession, { observe: 'response' });
    }

    update(studentMissingSession: IStudentMissingSession): Observable<EntityResponseType> {
        return this.http.put<IStudentMissingSession>(this.resourceUrl, studentMissingSession, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentMissingSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentMissingSession[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentMissingSession[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
