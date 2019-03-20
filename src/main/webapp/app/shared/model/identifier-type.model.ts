export interface IIdentifierType {
    id?: number;
    name?: string;
}

export class IdentifierType implements IIdentifierType {
    constructor(public id?: number, public name?: string) {}
}
