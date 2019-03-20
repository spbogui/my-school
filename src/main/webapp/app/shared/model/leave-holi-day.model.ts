import { Moment } from 'moment';

export interface ILeaveHoliDay {
    id?: number;
    label?: string;
    startDate?: Moment;
    endDate?: Moment;
    memo?: string;
    createdAt?: Moment;
    schoolYearId?: number;
}

export class LeaveHoliDay implements ILeaveHoliDay {
    constructor(
        public id?: number,
        public label?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public memo?: string,
        public createdAt?: Moment,
        public schoolYearId?: number
    ) {}
}
