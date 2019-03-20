export interface IJob {
    id?: number;
    name?: string;
    description?: string;
}

export class Job implements IJob {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
