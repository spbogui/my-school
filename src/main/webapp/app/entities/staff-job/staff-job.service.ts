import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStaffJob } from 'app/shared/model/staff-job.model';

type EntityResponseType = HttpResponse<IStaffJob>;
type EntityArrayResponseType = HttpResponse<IStaffJob[]>;

@Injectable({ providedIn: 'root' })
export class StaffJobService {
    public resourceUrl = SERVER_API_URL + 'api/staff-jobs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/staff-jobs';

    constructor(protected http: HttpClient) {}

    create(staffJob: IStaffJob): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(staffJob);
        return this.http
            .post<IStaffJob>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(staffJob: IStaffJob): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(staffJob);
        return this.http
            .put<IStaffJob>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStaffJob>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStaffJob[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStaffJob[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(staffJob: IStaffJob): IStaffJob {
        const copy: IStaffJob = Object.assign({}, staffJob, {
            startDate: staffJob.startDate != null && staffJob.startDate.isValid() ? staffJob.startDate.format(DATE_FORMAT) : null,
            endDate: staffJob.endDate != null && staffJob.endDate.isValid() ? staffJob.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((staffJob: IStaffJob) => {
                staffJob.startDate = staffJob.startDate != null ? moment(staffJob.startDate) : null;
                staffJob.endDate = staffJob.endDate != null ? moment(staffJob.endDate) : null;
            });
        }
        return res;
    }
}
