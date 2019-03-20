import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentDiploma } from 'app/shared/model/student-diploma.model';

type EntityResponseType = HttpResponse<IStudentDiploma>;
type EntityArrayResponseType = HttpResponse<IStudentDiploma[]>;

@Injectable({ providedIn: 'root' })
export class StudentDiplomaService {
    public resourceUrl = SERVER_API_URL + 'api/student-diplomas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-diplomas';

    constructor(protected http: HttpClient) {}

    create(studentDiploma: IStudentDiploma): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentDiploma);
        return this.http
            .post<IStudentDiploma>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(studentDiploma: IStudentDiploma): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(studentDiploma);
        return this.http
            .put<IStudentDiploma>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStudentDiploma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentDiploma[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStudentDiploma[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(studentDiploma: IStudentDiploma): IStudentDiploma {
        const copy: IStudentDiploma = Object.assign({}, studentDiploma, {
            graduationDate:
                studentDiploma.graduationDate != null && studentDiploma.graduationDate.isValid()
                    ? studentDiploma.graduationDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.graduationDate = res.body.graduationDate != null ? moment(res.body.graduationDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((studentDiploma: IStudentDiploma) => {
                studentDiploma.graduationDate = studentDiploma.graduationDate != null ? moment(studentDiploma.graduationDate) : null;
            });
        }
        return res;
    }
}
