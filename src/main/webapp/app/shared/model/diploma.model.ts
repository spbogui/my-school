export interface IDiploma {
    id?: number;
    diplomaLabel?: string;
    description?: string;
    cycleId?: number;
    parentDiplomaId?: number;
}

export class Diploma implements IDiploma {
    constructor(
        public id?: number,
        public diplomaLabel?: string,
        public description?: string,
        public cycleId?: number,
        public parentDiplomaId?: number
    ) {}
}
