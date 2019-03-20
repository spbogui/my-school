import { Moment } from 'moment';

export interface IPermissionAgreement {
    id?: number;
    permissionAllowed?: boolean;
    allowanceDate?: Moment;
    permissionStartDate?: Moment;
    permissionEndDate?: Moment;
    returnDate?: Moment;
    effectiveReturnDate?: Moment;
    memo?: string;
    askingPermissionId?: number;
}

export class PermissionAgreement implements IPermissionAgreement {
    constructor(
        public id?: number,
        public permissionAllowed?: boolean,
        public allowanceDate?: Moment,
        public permissionStartDate?: Moment,
        public permissionEndDate?: Moment,
        public returnDate?: Moment,
        public effectiveReturnDate?: Moment,
        public memo?: string,
        public askingPermissionId?: number
    ) {
        this.permissionAllowed = this.permissionAllowed || false;
    }
}
