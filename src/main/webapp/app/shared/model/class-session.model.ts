import { Moment } from 'moment';

export interface IClassSession {
    id?: number;
    startHour?: Moment;
    endHour?: Moment;
    detail?: string;
    createdAt?: Moment;
    classSessionTypeId?: number;
    programId?: number;
}

export class ClassSession implements IClassSession {
    constructor(
        public id?: number,
        public startHour?: Moment,
        public endHour?: Moment,
        public detail?: string,
        public createdAt?: Moment,
        public classSessionTypeId?: number,
        public programId?: number
    ) {}
}
