export interface IResponsible {
    id?: number;
    profession?: string;
    actorId?: number;
}

export class Responsible implements IResponsible {
    constructor(public id?: number, public profession?: string, public actorId?: number) {}
}
