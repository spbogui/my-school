import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClassSession } from 'app/shared/model/class-session.model';

type EntityResponseType = HttpResponse<IClassSession>;
type EntityArrayResponseType = HttpResponse<IClassSession[]>;

@Injectable({ providedIn: 'root' })
export class ClassSessionService {
    public resourceUrl = SERVER_API_URL + 'api/class-sessions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/class-sessions';

    constructor(protected http: HttpClient) {}

    create(classSession: IClassSession): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(classSession);
        return this.http
            .post<IClassSession>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(classSession: IClassSession): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(classSession);
        return this.http
            .put<IClassSession>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClassSession>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClassSession[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClassSession[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(classSession: IClassSession): IClassSession {
        const copy: IClassSession = Object.assign({}, classSession, {
            startHour: classSession.startHour != null && classSession.startHour.isValid() ? classSession.startHour.toJSON() : null,
            endHour: classSession.endHour != null && classSession.endHour.isValid() ? classSession.endHour.toJSON() : null,
            createdAt:
                classSession.createdAt != null && classSession.createdAt.isValid() ? classSession.createdAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startHour = res.body.startHour != null ? moment(res.body.startHour) : null;
            res.body.endHour = res.body.endHour != null ? moment(res.body.endHour) : null;
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((classSession: IClassSession) => {
                classSession.startHour = classSession.startHour != null ? moment(classSession.startHour) : null;
                classSession.endHour = classSession.endHour != null ? moment(classSession.endHour) : null;
                classSession.createdAt = classSession.createdAt != null ? moment(classSession.createdAt) : null;
            });
        }
        return res;
    }
}
