export interface ISubject {
    id?: number;
    label?: string;
    description?: string;
}

export class Subject implements ISubject {
    constructor(public id?: number, public label?: string, public description?: string) {}
}
