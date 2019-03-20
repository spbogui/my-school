export interface IClassSessionType {
    id?: number;
    name?: string;
    description?: string;
}

export class ClassSessionType implements IClassSessionType {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
