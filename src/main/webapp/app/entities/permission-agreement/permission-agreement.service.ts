import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPermissionAgreement } from 'app/shared/model/permission-agreement.model';

type EntityResponseType = HttpResponse<IPermissionAgreement>;
type EntityArrayResponseType = HttpResponse<IPermissionAgreement[]>;

@Injectable({ providedIn: 'root' })
export class PermissionAgreementService {
    public resourceUrl = SERVER_API_URL + 'api/permission-agreements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/permission-agreements';

    constructor(protected http: HttpClient) {}

    create(permissionAgreement: IPermissionAgreement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(permissionAgreement);
        return this.http
            .post<IPermissionAgreement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(permissionAgreement: IPermissionAgreement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(permissionAgreement);
        return this.http
            .put<IPermissionAgreement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPermissionAgreement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPermissionAgreement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPermissionAgreement[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(permissionAgreement: IPermissionAgreement): IPermissionAgreement {
        const copy: IPermissionAgreement = Object.assign({}, permissionAgreement, {
            allowanceDate:
                permissionAgreement.allowanceDate != null && permissionAgreement.allowanceDate.isValid()
                    ? permissionAgreement.allowanceDate.format(DATE_FORMAT)
                    : null,
            permissionStartDate:
                permissionAgreement.permissionStartDate != null && permissionAgreement.permissionStartDate.isValid()
                    ? permissionAgreement.permissionStartDate.format(DATE_FORMAT)
                    : null,
            permissionEndDate:
                permissionAgreement.permissionEndDate != null && permissionAgreement.permissionEndDate.isValid()
                    ? permissionAgreement.permissionEndDate.format(DATE_FORMAT)
                    : null,
            returnDate:
                permissionAgreement.returnDate != null && permissionAgreement.returnDate.isValid()
                    ? permissionAgreement.returnDate.format(DATE_FORMAT)
                    : null,
            effectiveReturnDate:
                permissionAgreement.effectiveReturnDate != null && permissionAgreement.effectiveReturnDate.isValid()
                    ? permissionAgreement.effectiveReturnDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.allowanceDate = res.body.allowanceDate != null ? moment(res.body.allowanceDate) : null;
            res.body.permissionStartDate = res.body.permissionStartDate != null ? moment(res.body.permissionStartDate) : null;
            res.body.permissionEndDate = res.body.permissionEndDate != null ? moment(res.body.permissionEndDate) : null;
            res.body.returnDate = res.body.returnDate != null ? moment(res.body.returnDate) : null;
            res.body.effectiveReturnDate = res.body.effectiveReturnDate != null ? moment(res.body.effectiveReturnDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((permissionAgreement: IPermissionAgreement) => {
                permissionAgreement.allowanceDate =
                    permissionAgreement.allowanceDate != null ? moment(permissionAgreement.allowanceDate) : null;
                permissionAgreement.permissionStartDate =
                    permissionAgreement.permissionStartDate != null ? moment(permissionAgreement.permissionStartDate) : null;
                permissionAgreement.permissionEndDate =
                    permissionAgreement.permissionEndDate != null ? moment(permissionAgreement.permissionEndDate) : null;
                permissionAgreement.returnDate = permissionAgreement.returnDate != null ? moment(permissionAgreement.returnDate) : null;
                permissionAgreement.effectiveReturnDate =
                    permissionAgreement.effectiveReturnDate != null ? moment(permissionAgreement.effectiveReturnDate) : null;
            });
        }
        return res;
    }
}
