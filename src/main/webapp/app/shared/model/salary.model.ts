import { Moment } from 'moment';

export interface ISalary {
    id?: number;
    amount?: number;
    paymentDate?: Moment;
    createdAt?: Moment;
    memo?: string;
    employeeId?: number;
}

export class Salary implements ISalary {
    constructor(
        public id?: number,
        public amount?: number,
        public paymentDate?: Moment,
        public createdAt?: Moment,
        public memo?: string,
        public employeeId?: number
    ) {}
}
