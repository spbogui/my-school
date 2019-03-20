export interface ICycle {
    id?: number;
    label?: string;
    description?: string;
}

export class Cycle implements ICycle {
    constructor(public id?: number, public label?: string, public description?: string) {}
}
