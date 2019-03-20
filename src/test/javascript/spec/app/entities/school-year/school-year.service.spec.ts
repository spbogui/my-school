/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SchoolYearService } from 'app/entities/school-year/school-year.service';
import { ISchoolYear, SchoolYear } from 'app/shared/model/school-year.model';

describe('Service Tests', () => {
    describe('SchoolYear Service', () => {
        let injector: TestBed;
        let service: SchoolYearService;
        let httpMock: HttpTestingController;
        let elemDefault: ISchoolYear;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SchoolYearService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new SchoolYear(0, 'AAAAAAA', currentDate, currentDate, currentDate, currentDate, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        courseStartDate: currentDate.format(DATE_FORMAT),
                        courseEndDate: currentDate.format(DATE_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT)
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

            it('should create a SchoolYear', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        courseStartDate: currentDate.format(DATE_FORMAT),
                        courseEndDate: currentDate.format(DATE_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        courseStartDate: currentDate,
                        courseEndDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new SchoolYear(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SchoolYear', async () => {
                const returnedFromService = Object.assign(
                    {
                        schoolYearlabel: 'BBBBBB',
                        courseStartDate: currentDate.format(DATE_FORMAT),
                        courseEndDate: currentDate.format(DATE_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        isBlankYear: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        courseStartDate: currentDate,
                        courseEndDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
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

            it('should return a list of SchoolYear', async () => {
                const returnedFromService = Object.assign(
                    {
                        schoolYearlabel: 'BBBBBB',
                        courseStartDate: currentDate.format(DATE_FORMAT),
                        courseEndDate: currentDate.format(DATE_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        isBlankYear: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        courseStartDate: currentDate,
                        courseEndDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
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

            it('should delete a SchoolYear', async () => {
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
