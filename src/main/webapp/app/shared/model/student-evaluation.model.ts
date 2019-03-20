export interface IStudentEvaluation {
    id?: number;
    grade?: number;
    actorId?: number;
    evaluationId?: number;
    evaluationModeId?: number;
    subjectId?: number;
}

export class StudentEvaluation implements IStudentEvaluation {
    constructor(
        public id?: number,
        public grade?: number,
        public actorId?: number,
        public evaluationId?: number,
        public evaluationModeId?: number,
        public subjectId?: number
    ) {}
}
