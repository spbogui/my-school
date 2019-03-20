export interface IAddress {
    id?: number;
    mobilePhone?: string;
    officePhone?: string;
    homePhone?: string;
    email?: string;
    postalAddress?: string;
    town?: string;
    region?: string;
    village?: string;
    isPreferred?: boolean;
    actorId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public mobilePhone?: string,
        public officePhone?: string,
        public homePhone?: string,
        public email?: string,
        public postalAddress?: string,
        public town?: string,
        public region?: string,
        public village?: string,
        public isPreferred?: boolean,
        public actorId?: number
    ) {
        this.isPreferred = this.isPreferred || false;
    }
}
