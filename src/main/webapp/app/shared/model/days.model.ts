export interface IDays {
    id?: number;
    name?: string;
}

export class Days implements IDays {
    constructor(public id?: number, public name?: string) {}
}
