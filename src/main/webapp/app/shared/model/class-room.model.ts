export interface IClassRoom {
    id?: number;
    name?: string;
    levelId?: number;
}

export class ClassRoom implements IClassRoom {
    constructor(public id?: number, public name?: string, public levelId?: number) {}
}
