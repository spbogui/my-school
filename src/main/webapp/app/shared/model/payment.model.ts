import { Moment } from 'moment';

export interface IPayment {
    id?: number;
    amount?: number;
    paymentDate?: Moment;
    actorId?: number;
    rubricId?: number;
}

export class Payment implements IPayment {
    constructor(
        public id?: number,
        public amount?: number,
        public paymentDate?: Moment,
        public actorId?: number,
        public rubricId?: number
    ) {}
}
