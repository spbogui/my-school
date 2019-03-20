/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PermissionAgreementService } from 'app/entities/permission-agreement/permission-agreement.service';
import { IPermissionAgreement, PermissionAgreement } from 'app/shared/model/permission-agreement.model';

describe('Service Tests', () => {
    describe('PermissionAgreement Service', () => {
        let injector: TestBed;
        let service: PermissionAgreementService;
        let httpMock: HttpTestingController;
        let elemDefault: IPermissionAgreement;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PermissionAgreementService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PermissionAgreement(0, false, currentDate, currentDate, currentDate, currentDate, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        allowanceDate: currentDate.format(DATE_FORMAT),
                        permissionStartDate: currentDate.format(DATE_FORMAT),
                        permissionEndDate: currentDate.format(DATE_FORMAT),
                        returnDate: currentDate.format(DATE_FORMAT),
                        effectiveReturnDate: currentDate.format(DATE_FORMAT)
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

            it('should create a PermissionAgreement', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        allowanceDate: currentDate.format(DATE_FORMAT),
                        permissionStartDate: currentDate.format(DATE_FORMAT),
                        permissionEndDate: currentDate.format(DATE_FORMAT),
                        returnDate: currentDate.format(DATE_FORMAT),
                        effectiveReturnDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        allowanceDate: currentDate,
                        permissionStartDate: currentDate,
                        permissionEndDate: currentDate,
                        returnDate: currentDate,
                        effectiveReturnDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PermissionAgreement(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PermissionAgreement', async () => {
                const returnedFromService = Object.assign(
                    {
                        permissionAllowed: true,
                        allowanceDate: currentDate.format(DATE_FORMAT),
                        permissionStartDate: currentDate.format(DATE_FORMAT),
                        permissionEndDate: currentDate.format(DATE_FORMAT),
                        returnDate: currentDate.format(DATE_FORMAT),
                        effectiveReturnDate: currentDate.format(DATE_FORMAT),
                        memo: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        allowanceDate: currentDate,
                        permissionStartDate: currentDate,
                        permissionEndDate: currentDate,
                        returnDate: currentDate,
                        effectiveReturnDate: currentDate
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

            it('should return a list of PermissionAgreement', async () => {
                const returnedFromService = Object.assign(
                    {
                        permissionAllowed: true,
                        allowanceDate: currentDate.format(DATE_FORMAT),
                        permissionStartDate: currentDate.format(DATE_FORMAT),
                        permissionEndDate: currentDate.format(DATE_FORMAT),
                        returnDate: currentDate.format(DATE_FORMAT),
                        effectiveReturnDate: currentDate.format(DATE_FORMAT),
                        memo: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        allowanceDate: currentDate,
                        permissionStartDate: currentDate,
                        permissionEndDate: currentDate,
                        returnDate: currentDate,
                        effectiveReturnDate: currentDate
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

            it('should delete a PermissionAgreement', async () => {
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
