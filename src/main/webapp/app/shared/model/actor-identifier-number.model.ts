export interface IActorIdentifierNumber {
    id?: number;
    identifier?: string;
    identifierTypeId?: number;
    actorId?: number;
}

export class ActorIdentifierNumber implements IActorIdentifierNumber {
    constructor(public id?: number, public identifier?: string, public identifierTypeId?: number, public actorId?: number) {}
}
