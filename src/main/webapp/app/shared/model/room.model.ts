export interface IRoom {
    id?: number;
    label?: string;
    roomTypeId?: number;
}

export class Room implements IRoom {
    constructor(public id?: number, public label?: string, public roomTypeId?: number) {}
}
