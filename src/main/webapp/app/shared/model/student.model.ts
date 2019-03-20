export interface IStudent {
    id?: number;
    pareantSeparated?: boolean;
    fatherIsAlive?: boolean;
    livingWithFather?: boolean;
    motherIsAlive?: boolean;
    livingWithMother?: boolean;
    actorId?: number;
}

export class Student implements IStudent {
    constructor(
        public id?: number,
        public pareantSeparated?: boolean,
        public fatherIsAlive?: boolean,
        public livingWithFather?: boolean,
        public motherIsAlive?: boolean,
        public livingWithMother?: boolean,
        public actorId?: number
    ) {
        this.pareantSeparated = this.pareantSeparated || false;
        this.fatherIsAlive = this.fatherIsAlive || false;
        this.livingWithFather = this.livingWithFather || false;
        this.motherIsAlive = this.motherIsAlive || false;
        this.livingWithMother = this.livingWithMother || false;
    }
}
