import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISchoolSchoolYear } from 'app/shared/model/school-school-year.model';

type EntityResponseType = HttpResponse<ISchoolSchoolYear>;
type EntityArrayResponseType = HttpResponse<ISchoolSchoolYear[]>;

@Injectable({ providedIn: 'root' })
export class SchoolSchoolYearService {
    public resourceUrl = SERVER_API_URL + 'api/school-school-years';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/school-school-years';

    constructor(protected http: HttpClient) {}

    create(schoolSchoolYear: ISchoolSchoolYear): Observable<EntityResponseType> {
        return this.http.post<ISchoolSchoolYear>(this.resourceUrl, schoolSchoolYear, { observe: 'response' });
    }

    update(schoolSchoolYear: ISchoolSchoolYear): Observable<EntityResponseType> {
        return this.http.put<ISchoolSchoolYear>(this.resourceUrl, schoolSchoolYear, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISchoolSchoolYear>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISchoolSchoolYear[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISchoolSchoolYear[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
