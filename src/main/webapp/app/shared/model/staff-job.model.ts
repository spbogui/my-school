import { Moment } from 'moment';

export interface IStaffJob {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    staffId?: number;
    jobId?: number;
}

export class StaffJob implements IStaffJob {
    constructor(public id?: number, public startDate?: Moment, public endDate?: Moment, public staffId?: number, public jobId?: number) {}
}
