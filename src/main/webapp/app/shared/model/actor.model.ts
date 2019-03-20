import { Moment } from 'moment';

export interface IActor {
    id?: number;
    birthDate?: Moment;
    birthPlace?: string;
    gender?: string;
    countryId?: number;
}

export class Actor implements IActor {
    constructor(
        public id?: number,
        public birthDate?: Moment,
        public birthPlace?: string,
        public gender?: string,
        public countryId?: number
    ) {}
}
