/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EvaluationService } from 'app/entities/evaluation/evaluation.service';
import { IEvaluation, Evaluation } from 'app/shared/model/evaluation.model';

describe('Service Tests', () => {
    describe('Evaluation Service', () => {
        let injector: TestBed;
        let service: EvaluationService;
        let httpMock: HttpTestingController;
        let elemDefault: IEvaluation;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EvaluationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Evaluation(0, currentDate, false, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        plannedDate: currentDate.format(DATE_FORMAT),
                        evaluationDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Evaluation', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        plannedDate: currentDate.format(DATE_FORMAT),
                        evaluationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        plannedDate: currentDate,
                        evaluationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Evaluation(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Evaluation', async () => {
                const returnedFromService = Object.assign(
                    {
                        plannedDate: currentDate.format(DATE_FORMAT),
                        isDone: true,
                        evaluationDate: currentDate.format(DATE_FORMAT),
                        duration: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        plannedDate: currentDate,
                        evaluationDate: currentDate
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

            it('should return a list of Evaluation', async () => {
                const returnedFromService = Object.assign(
                    {
                        plannedDate: currentDate.format(DATE_FORMAT),
                        isDone: true,
                        evaluationDate: currentDate.format(DATE_FORMAT),
                        duration: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        plannedDate: currentDate,
                        evaluationDate: currentDate
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

            it('should delete a Evaluation', async () => {
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
