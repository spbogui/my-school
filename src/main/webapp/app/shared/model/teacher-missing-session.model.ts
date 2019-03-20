export interface ITeacherMissingSession {
    id?: number;
    isJustified?: boolean;
    classSessionId?: number;
    studentId?: number;
}

export class TeacherMissingSession implements ITeacherMissingSession {
    constructor(public id?: number, public isJustified?: boolean, public classSessionId?: number, public studentId?: number) {
        this.isJustified = this.isJustified || false;
    }
}
