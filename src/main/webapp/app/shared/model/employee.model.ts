import { Moment } from 'moment';

export interface IEmployee {
    id?: number;
    hiringDate?: Moment;
    employmentStartDate?: Moment;
    actorId?: number;
}

export class Employee implements IEmployee {
    constructor(public id?: number, public hiringDate?: Moment, public employmentStartDate?: Moment, public actorId?: number) {}
}
