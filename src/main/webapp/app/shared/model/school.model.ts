import { Moment } from 'moment';

export interface ISchool {
    id?: number;
    schoolName?: string;
    slogan?: string;
    openningDate?: Moment;
    phoneNumber1?: string;
    phoneNumber2?: string;
    email?: string;
    fax?: string;
    webSiteLink?: string;
    address?: string;
    town?: string;
    region?: string;
    municipality?: string;
}

export class School implements ISchool {
    constructor(
        public id?: number,
        public schoolName?: string,
        public slogan?: string,
        public openningDate?: Moment,
        public phoneNumber1?: string,
        public phoneNumber2?: string,
        public email?: string,
        public fax?: string,
        public webSiteLink?: string,
        public address?: string,
        public town?: string,
        public region?: string,
        public municipality?: string
    ) {}
}
