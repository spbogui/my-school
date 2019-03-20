export interface ICountry {
    id?: number;
    name?: string;
    manName?: string;
    womanName?: string;
}

export class Country implements ICountry {
    constructor(public id?: number, public name?: string, public manName?: string, public womanName?: string) {}
}
