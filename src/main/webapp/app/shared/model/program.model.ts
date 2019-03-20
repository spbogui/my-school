import { Moment } from 'moment';

export interface IProgram {
    id?: number;
    startHour?: Moment;
    endHour?: Moment;
    subjectId?: number;
    classRoomId?: number;
    roomId?: number;
    daysId?: number;
    schoolYearId?: number;
}

export class Program implements IProgram {
    constructor(
        public id?: number,
        public startHour?: Moment,
        public endHour?: Moment,
        public subjectId?: number,
        public classRoomId?: number,
        public roomId?: number,
        public daysId?: number,
        public schoolYearId?: number
    ) {}
}
