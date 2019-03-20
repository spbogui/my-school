/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EnrolmentService } from 'app/entities/enrolment/enrolment.service';
import { IEnrolment, Enrolment } from 'app/shared/model/enrolment.model';

describe('Service Tests', () => {
    describe('Enrolment Service', () => {
        let injector: TestBed;
        let service: EnrolmentService;
        let httpMock: HttpTestingController;
        let elemDefault: IEnrolment;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EnrolmentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Enrolment(0, currentDate, 0, false, 'AAAAAAA', 'AAAAAAA', false, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        enrolmentDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Enrolment', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        enrolmentDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        enrolmentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Enrolment(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Enrolment', async () => {
                const returnedFromService = Object.assign(
                    {
                        enrolmentDate: currentDate.format(DATE_FORMAT),
                        regimenRate: 1,
                        repeating: true,
                        modernLanguage1: 'BBBBBB',
                        modernLanguage2: 'BBBBBB',
                        exemption: true,
                        withInsurance: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        enrolmentDate: currentDate
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

            it('should return a list of Enrolment', async () => {
                const returnedFromService = Object.assign(
                    {
                        enrolmentDate: currentDate.format(DATE_FORMAT),
                        regimenRate: 1,
                        repeating: true,
                        modernLanguage1: 'BBBBBB',
                        modernLanguage2: 'BBBBBB',
                        exemption: true,
                        withInsurance: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        enrolmentDate: currentDate
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

            it('should delete a Enrolment', async () => {
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
