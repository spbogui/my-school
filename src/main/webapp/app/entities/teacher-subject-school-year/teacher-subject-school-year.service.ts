import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITeacherSubjectSchoolYear } from 'app/shared/model/teacher-subject-school-year.model';

type EntityResponseType = HttpResponse<ITeacherSubjectSchoolYear>;
type EntityArrayResponseType = HttpResponse<ITeacherSubjectSchoolYear[]>;

@Injectable({ providedIn: 'root' })
export class TeacherSubjectSchoolYearService {
    public resourceUrl = SERVER_API_URL + 'api/teacher-subject-school-years';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/teacher-subject-school-years';

    constructor(protected http: HttpClient) {}

    create(teacherSubjectSchoolYear: ITeacherSubjectSchoolYear): Observable<EntityResponseType> {
        return this.http.post<ITeacherSubjectSchoolYear>(this.resourceUrl, teacherSubjectSchoolYear, { observe: 'response' });
    }

    update(teacherSubjectSchoolYear: ITeacherSubjectSchoolYear): Observable<EntityResponseType> {
        return this.http.put<ITeacherSubjectSchoolYear>(this.resourceUrl, teacherSubjectSchoolYear, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITeacherSubjectSchoolYear>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeacherSubjectSchoolYear[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeacherSubjectSchoolYear[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
