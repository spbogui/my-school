import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStaffJob } from 'app/shared/model/staff-job.model';
import { StaffJobService } from './staff-job.service';
import { IStaff } from 'app/shared/model/staff.model';
import { StaffService } from 'app/entities/staff';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job';

@Component({
    selector: 'jhi-staff-job-update',
    templateUrl: './staff-job-update.component.html'
})
export class StaffJobUpdateComponent implements OnInit {
    staffJob: IStaffJob;
    isSaving: boolean;

    staff: IStaff[];

    jobs: IJob[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected staffJobService: StaffJobService,
        protected staffService: StaffService,
        protected jobService: JobService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ staffJob }) => {
            this.staffJob = staffJob;
        });
        this.staffService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStaff[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStaff[]>) => response.body)
            )
            .subscribe((res: IStaff[]) => (this.staff = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.jobService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJob[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJob[]>) => response.body)
            )
            .subscribe((res: IJob[]) => (this.jobs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.staffJob.id !== undefined) {
            this.subscribeToSaveResponse(this.staffJobService.update(this.staffJob));
        } else {
            this.subscribeToSaveResponse(this.staffJobService.create(this.staffJob));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStaffJob>>) {
        result.subscribe((res: HttpResponse<IStaffJob>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStaffById(index: number, item: IStaff) {
        return item.id;
    }

    trackJobById(index: number, item: IJob) {
        return item.id;
    }
}
