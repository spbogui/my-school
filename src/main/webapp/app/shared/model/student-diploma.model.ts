import { Moment } from 'moment';

export interface IStudentDiploma {
    id?: number;
    mention?: string;
    graduationDate?: Moment;
    studentId?: number;
    diplomaId?: number;
    schoolSchoolYearId?: number;
}

export class StudentDiploma implements IStudentDiploma {
    constructor(
        public id?: number,
        public mention?: string,
        public graduationDate?: Moment,
        public studentId?: number,
        public diplomaId?: number,
        public schoolSchoolYearId?: number
    ) {}
}
