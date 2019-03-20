import { IStaffJob } from 'app/shared/model/staff-job.model';

export interface IStaff {
    id?: number;
    employeeId?: number;
    staffJobs?: IStaffJob[];
}

export class Staff implements IStaff {
    constructor(public id?: number, public employeeId?: number, public staffJobs?: IStaffJob[]) {}
}
