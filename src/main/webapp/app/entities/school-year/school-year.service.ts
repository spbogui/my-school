import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISchoolYear } from 'app/shared/model/school-year.model';

type EntityResponseType = HttpResponse<ISchoolYear>;
type EntityArrayResponseType = HttpResponse<ISchoolYear[]>;

@Injectable({ providedIn: 'root' })
export class SchoolYearService {
    public resourceUrl = SERVER_API_URL + 'api/school-years';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/school-years';

    constructor(protected http: HttpClient) {}

    create(schoolYear: ISchoolYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(schoolYear);
        return this.http
            .post<ISchoolYear>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(schoolYear: ISchoolYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(schoolYear);
        return this.http
            .put<ISchoolYear>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISchoolYear>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISchoolYear[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISchoolYear[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(schoolYear: ISchoolYear): ISchoolYear {
        const copy: ISchoolYear = Object.assign({}, schoolYear, {
            courseStartDate:
                schoolYear.courseStartDate != null && schoolYear.courseStartDate.isValid()
                    ? schoolYear.courseStartDate.format(DATE_FORMAT)
                    : null,
            courseEndDate:
                schoolYear.courseEndDate != null && schoolYear.courseEndDate.isValid()
                    ? schoolYear.courseEndDate.format(DATE_FORMAT)
                    : null,
            startDate: schoolYear.startDate != null && schoolYear.startDate.isValid() ? schoolYear.startDate.format(DATE_FORMAT) : null,
            endDate: schoolYear.endDate != null && schoolYear.endDate.isValid() ? schoolYear.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.courseStartDate = res.body.courseStartDate != null ? moment(res.body.courseStartDate) : null;
            res.body.courseEndDate = res.body.courseEndDate != null ? moment(res.body.courseEndDate) : null;
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((schoolYear: ISchoolYear) => {
                schoolYear.courseStartDate = schoolYear.courseStartDate != null ? moment(schoolYear.courseStartDate) : null;
                schoolYear.courseEndDate = schoolYear.courseEndDate != null ? moment(schoolYear.courseEndDate) : null;
                schoolYear.startDate = schoolYear.startDate != null ? moment(schoolYear.startDate) : null;
                schoolYear.endDate = schoolYear.endDate != null ? moment(schoolYear.endDate) : null;
            });
        }
        return res;
    }
}
