export interface IRubricAmount {
    id?: number;
    amount?: number;
    paymentMethod?: string;
    rubricId?: number;
    levelId?: number;
    schoolYearId?: number;
}

export class RubricAmount implements IRubricAmount {
    constructor(
        public id?: number,
        public amount?: number,
        public paymentMethod?: string,
        public rubricId?: number,
        public levelId?: number,
        public schoolYearId?: number
    ) {}
}
