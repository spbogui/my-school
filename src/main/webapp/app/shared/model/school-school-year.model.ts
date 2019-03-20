export interface ISchoolSchoolYear {
    id?: number;
    description?: string;
    schoolNameId?: number;
    schoolYearlabelId?: number;
}

export class SchoolSchoolYear implements ISchoolSchoolYear {
    constructor(public id?: number, public description?: string, public schoolNameId?: number, public schoolYearlabelId?: number) {}
}
