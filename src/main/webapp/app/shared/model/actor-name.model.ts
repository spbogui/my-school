export interface IActorName {
    id?: number;
    civility?: string;
    familyName?: string;
    givenName1?: string;
    givenName2?: string;
    givenName3?: string;
    maidenName?: string;
    actorId?: number;
}

export class ActorName implements IActorName {
    constructor(
        public id?: number,
        public civility?: string,
        public familyName?: string,
        public givenName1?: string,
        public givenName2?: string,
        public givenName3?: string,
        public maidenName?: string,
        public actorId?: number
    ) {}
}
