export interface IEvaluationType {
    id?: number;
    name?: string;
    description?: string;
}

export class EvaluationType implements IEvaluationType {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
