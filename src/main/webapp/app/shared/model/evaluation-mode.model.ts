export interface IEvaluationMode {
    id?: number;
    name?: string;
    description?: string;
}

export class EvaluationMode implements IEvaluationMode {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
