export interface IRegimen {
    id?: number;
    regimenLabel?: string;
    description?: string;
}

export class Regimen implements IRegimen {
    constructor(public id?: number, public regimenLabel?: string, public description?: string) {}
}
