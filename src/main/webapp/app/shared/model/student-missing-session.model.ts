export interface IStudentMissingSession {
    id?: number;
    isJustified?: boolean;
    classSessionId?: number;
    studentId?: number;
}

export class StudentMissingSession implements IStudentMissingSession {
    constructor(public id?: number, public isJustified?: boolean, public classSessionId?: number, public studentId?: number) {
        this.isJustified = this.isJustified || false;
    }
}
