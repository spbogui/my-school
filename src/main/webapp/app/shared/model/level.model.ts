import { ILevel } from 'app/shared/model/level.model';
import { ICycle } from 'app/shared/model/cycle.model';

export interface ILevel {
    id?: number;
    label?: string;
    shortForm?: string;
    parentLevel?: ILevel;
    cycle?: ICycle;
}

export class Level implements ILevel {
    constructor(public id?: number, public label?: string, public shortForm?: string, public parentLevel?: ILevel, public cycle?: ICycle) {}
}
