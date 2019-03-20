import { ISubject } from 'app/shared/model/subject.model';

export interface ITeacher {
    id?: number;
    hourlyRate?: number;
    employeeId?: number;
    subjects?: ISubject[];
}

export class Teacher implements ITeacher {
    constructor(public id?: number, public hourlyRate?: number, public employeeId?: number, public subjects?: ISubject[]) {}
}
