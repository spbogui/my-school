import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoomType } from 'app/shared/model/room-type.model';

type EntityResponseType = HttpResponse<IRoomType>;
type EntityArrayResponseType = HttpResponse<IRoomType[]>;

@Injectable({ providedIn: 'root' })
export class RoomTypeService {
    public resourceUrl = SERVER_API_URL + 'api/room-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/room-types';

    constructor(protected http: HttpClient) {}

    create(roomType: IRoomType): Observable<EntityResponseType> {
        return this.http.post<IRoomType>(this.resourceUrl, roomType, { observe: 'response' });
    }

    update(roomType: IRoomType): Observable<EntityResponseType> {
        return this.http.put<IRoomType>(this.resourceUrl, roomType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoomType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
