export interface IRoomType {
    id?: number;
    label?: string;
    description?: string;
}

export class RoomType implements IRoomType {
    constructor(public id?: number, public label?: string, public description?: string) {}
}
