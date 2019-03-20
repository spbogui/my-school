/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ClassSessionService } from 'app/entities/class-session/class-session.service';
import { IClassSession, ClassSession } from 'app/shared/model/class-session.model';

describe('Service Tests', () => {
    describe('ClassSession Service', () => {
        let injector: TestBed;
        let service: ClassSessionService;
        let httpMock: HttpTestingController;
        let elemDefault: IClassSession;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ClassSessionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ClassSession(0, currentDate, currentDate, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        startHour: currentDate.format(DATE_TIME_FORMAT),
                        endHour: currentDate.format(DATE_TIME_FORMAT),
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ClassSession', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        startHour: currentDate.format(DATE_TIME_FORMAT),
                        endHour: currentDate.format(DATE_TIME_FORMAT),
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startHour: currentDate,
                        endHour: currentDate,
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ClassSession(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ClassSession', async () => {
                const returnedFromService = Object.assign(
                    {
                        startHour: currentDate.format(DATE_TIME_FORMAT),
                        endHour: currentDate.format(DATE_TIME_FORMAT),
                        detail: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        startHour: currentDate,
                        endHour: currentDate,
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ClassSession', async () => {
                const returnedFromService = Object.assign(
                    {
                        startHour: currentDate.format(DATE_TIME_FORMAT),
                        endHour: currentDate.format(DATE_TIME_FORMAT),
                        detail: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startHour: currentDate,
                        endHour: currentDate,
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ClassSession', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
