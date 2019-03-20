export interface IRubric {
    id?: number;
    name?: string;
    description?: string;
}

export class Rubric implements IRubric {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
