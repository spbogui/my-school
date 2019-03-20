import { Moment } from 'moment';

export interface IPeriod {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    createdAt?: Moment;
    periodLabelId?: number;
    schoolYearlabelId?: number;
}

export class Period implements IPeriod {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public createdAt?: Moment,
        public periodLabelId?: number,
        public schoolYearlabelId?: number
    ) {}
}
