import { Moment } from 'moment';
import { IClassRoom } from 'app/shared/model/class-room.model';

export interface IEvaluation {
    id?: number;
    plannedDate?: Moment;
    isDone?: boolean;
    evaluationDate?: Moment;
    duration?: number;
    evaluationTypeId?: number;
    schoolSchoolYearId?: number;
    periodId?: number;
    classRooms?: IClassRoom[];
}

export class Evaluation implements IEvaluation {
    constructor(
        public id?: number,
        public plannedDate?: Moment,
        public isDone?: boolean,
        public evaluationDate?: Moment,
        public duration?: number,
        public evaluationTypeId?: number,
        public schoolSchoolYearId?: number,
        public periodId?: number,
        public classRooms?: IClassRoom[]
    ) {
        this.isDone = this.isDone || false;
    }
}
