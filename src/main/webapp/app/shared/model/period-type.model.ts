export interface IPeriodType {
    id?: number;
    periodLabel?: string;
    description?: string;
}

export class PeriodType implements IPeriodType {
    constructor(public id?: number, public periodLabel?: string, public description?: string) {}
}
