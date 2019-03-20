import { Moment } from 'moment';

export interface IEnrolment {
    id?: number;
    enrolmentDate?: Moment;
    regimenRate?: number;
    repeating?: boolean;
    modernLanguage1?: string;
    modernLanguage2?: string;
    exemption?: boolean;
    withInsurance?: boolean;
    schoolYearId?: number;
    actorId?: number;
    classRoomId?: number;
    regimenId?: number;
    previousSchoolId?: number;
    previousClassId?: number;
}

export class Enrolment implements IEnrolment {
    constructor(
        public id?: number,
        public enrolmentDate?: Moment,
        public regimenRate?: number,
        public repeating?: boolean,
        public modernLanguage1?: string,
        public modernLanguage2?: string,
        public exemption?: boolean,
        public withInsurance?: boolean,
        public schoolYearId?: number,
        public actorId?: number,
        public classRoomId?: number,
        public regimenId?: number,
        public previousSchoolId?: number,
        public previousClassId?: number
    ) {
        this.repeating = this.repeating || false;
        this.exemption = this.exemption || false;
        this.withInsurance = this.withInsurance || false;
    }
}
