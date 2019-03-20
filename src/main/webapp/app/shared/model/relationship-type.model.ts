export interface IRelationshipType {
    id?: number;
    name?: string;
    description?: string;
}

export class RelationshipType implements IRelationshipType {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
