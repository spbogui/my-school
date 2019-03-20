import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAskingPermission } from 'app/shared/model/asking-permission.model';

type EntityResponseType = HttpResponse<IAskingPermission>;
type EntityArrayResponseType = HttpResponse<IAskingPermission[]>;

@Injectable({ providedIn: 'root' })
export class AskingPermissionService {
    public resourceUrl = SERVER_API_URL + 'api/asking-permissions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/asking-permissions';

    constructor(protected http: HttpClient) {}

    create(askingPermission: IAskingPermission): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(askingPermission);
        return this.http
            .post<IAskingPermission>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(askingPermission: IAskingPermission): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(askingPermission);
        return this.http
            .put<IAskingPermission>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAskingPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAskingPermission[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAskingPermission[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(askingPermission: IAskingPermission): IAskingPermission {
        const copy: IAskingPermission = Object.assign({}, askingPermission, {
            askingDate:
                askingPermission.askingDate != null && askingPermission.askingDate.isValid()
                    ? askingPermission.askingDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.askingDate = res.body.askingDate != null ? moment(res.body.askingDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((askingPermission: IAskingPermission) => {
                askingPermission.askingDate = askingPermission.askingDate != null ? moment(askingPermission.askingDate) : null;
            });
        }
        return res;
    }
}
