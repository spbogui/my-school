export interface ITeacherSubjectSchoolYear {
    id?: number;
    isPrincipal?: boolean;
    actorId?: number;
    teacherId?: number;
    schoolSchoolYearId?: number;
}

export class TeacherSubjectSchoolYear implements ITeacherSubjectSchoolYear {
    constructor(
        public id?: number,
        public isPrincipal?: boolean,
        public actorId?: number,
        public teacherId?: number,
        public schoolSchoolYearId?: number
    ) {
        this.isPrincipal = this.isPrincipal || false;
    }
}
