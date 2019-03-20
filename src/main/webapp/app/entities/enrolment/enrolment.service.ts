import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnrolment } from 'app/shared/model/enrolment.model';

type EntityResponseType = HttpResponse<IEnrolment>;
type EntityArrayResponseType = HttpResponse<IEnrolment[]>;

@Injectable({ providedIn: 'root' })
export class EnrolmentService {
    public resourceUrl = SERVER_API_URL + 'api/enrolments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/enrolments';

    constructor(protected http: HttpClient) {}

    create(enrolment: IEnrolment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(enrolment);
        return this.http
            .post<IEnrolment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(enrolment: IEnrolment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(enrolment);
        return this.http
            .put<IEnrolment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEnrolment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEnrolment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEnrolment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(enrolment: IEnrolment): IEnrolment {
        const copy: IEnrolment = Object.assign({}, enrolment, {
            enrolmentDate:
                enrolment.enrolmentDate != null && enrolment.enrolmentDate.isValid() ? enrolment.enrolmentDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.enrolmentDate = res.body.enrolmentDate != null ? moment(res.body.enrolmentDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((enrolment: IEnrolment) => {
                enrolment.enrolmentDate = enrolment.enrolmentDate != null ? moment(enrolment.enrolmentDate) : null;
            });
        }
        return res;
    }
}
