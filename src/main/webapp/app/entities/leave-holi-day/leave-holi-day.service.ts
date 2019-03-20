import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeaveHoliDay } from 'app/shared/model/leave-holi-day.model';

type EntityResponseType = HttpResponse<ILeaveHoliDay>;
type EntityArrayResponseType = HttpResponse<ILeaveHoliDay[]>;

@Injectable({ providedIn: 'root' })
export class LeaveHoliDayService {
    public resourceUrl = SERVER_API_URL + 'api/leave-holi-days';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-holi-days';

    constructor(protected http: HttpClient) {}

    create(leaveHoliDay: ILeaveHoliDay): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveHoliDay);
        return this.http
            .post<ILeaveHoliDay>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(leaveHoliDay: ILeaveHoliDay): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveHoliDay);
        return this.http
            .put<ILeaveHoliDay>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILeaveHoliDay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveHoliDay[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveHoliDay[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(leaveHoliDay: ILeaveHoliDay): ILeaveHoliDay {
        const copy: ILeaveHoliDay = Object.assign({}, leaveHoliDay, {
            startDate:
                leaveHoliDay.startDate != null && leaveHoliDay.startDate.isValid() ? leaveHoliDay.startDate.format(DATE_FORMAT) : null,
            endDate: leaveHoliDay.endDate != null && leaveHoliDay.endDate.isValid() ? leaveHoliDay.endDate.format(DATE_FORMAT) : null,
            createdAt:
                leaveHoliDay.createdAt != null && leaveHoliDay.createdAt.isValid() ? leaveHoliDay.createdAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((leaveHoliDay: ILeaveHoliDay) => {
                leaveHoliDay.startDate = leaveHoliDay.startDate != null ? moment(leaveHoliDay.startDate) : null;
                leaveHoliDay.endDate = leaveHoliDay.endDate != null ? moment(leaveHoliDay.endDate) : null;
                leaveHoliDay.createdAt = leaveHoliDay.createdAt != null ? moment(leaveHoliDay.createdAt) : null;
            });
        }
        return res;
    }
}
