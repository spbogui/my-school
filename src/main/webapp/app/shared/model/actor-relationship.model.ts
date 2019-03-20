export interface IActorRelationship {
    id?: number;
    isActive?: boolean;
    responsibleId?: number;
    studentId?: number;
    relationshipTypeId?: number;
}

export class ActorRelationship implements IActorRelationship {
    constructor(
        public id?: number,
        public isActive?: boolean,
        public responsibleId?: number,
        public studentId?: number,
        public relationshipTypeId?: number
    ) {
        this.isActive = this.isActive || false;
    }
}
