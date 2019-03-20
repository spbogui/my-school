import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISchool } from 'app/shared/model/school.model';

type EntityResponseType = HttpResponse<ISchool>;
type EntityArrayResponseType = HttpResponse<ISchool[]>;

@Injectable({ providedIn: 'root' })
export class SchoolService {
    public resourceUrl = SERVER_API_URL + 'api/schools';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/schools';

    constructor(protected http: HttpClient) {}

    create(school: ISchool): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(school);
        return this.http
            .post<ISchool>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(school: ISchool): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(school);
        return this.http
            .put<ISchool>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISchool>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISchool[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISchool[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(school: ISchool): ISchool {
        const copy: ISchool = Object.assign({}, school, {
            openningDate: school.openningDate != null && school.openningDate.isValid() ? school.openningDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.openningDate = res.body.openningDate != null ? moment(res.body.openningDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((school: ISchool) => {
                school.openningDate = school.openningDate != null ? moment(school.openningDate) : null;
            });
        }
        return res;
    }
}
