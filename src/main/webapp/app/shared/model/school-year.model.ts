import { Moment } from 'moment';

export interface ISchoolYear {
    id?: number;
    schoolYearlabel?: string;
    courseStartDate?: Moment;
    courseEndDate?: Moment;
    startDate?: Moment;
    endDate?: Moment;
    isBlankYear?: boolean;
}

export class SchoolYear implements ISchoolYear {
    constructor(
        public id?: number,
        public schoolYearlabel?: string,
        public courseStartDate?: Moment,
        public courseEndDate?: Moment,
        public startDate?: Moment,
        public endDate?: Moment,
        public isBlankYear?: boolean
    ) {
        this.isBlankYear = this.isBlankYear || false;
    }
}
