import { Moment } from 'moment';

export interface IAskingPermission {
    id?: number;
    askingDate?: Moment;
    numberOfDay?: number;
    reason?: string;
    schoolSchoolYearId?: number;
    studentId?: number;
}

export class AskingPermission implements IAskingPermission {
    constructor(
        public id?: number,
        public askingDate?: Moment,
        public numberOfDay?: number,
        public reason?: string,
        public schoolSchoolYearId?: number,
        public studentId?: number
    ) {}
}
