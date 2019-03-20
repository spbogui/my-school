export interface IImage {
    id?: number;
    contentContentType?: string;
    content?: any;
    name?: string;
    format?: string;
    imageSize?: string;
    isUsed?: boolean;
    actorId?: number;
}

export class Image implements IImage {
    constructor(
        public id?: number,
        public contentContentType?: string,
        public content?: any,
        public name?: string,
        public format?: string,
        public imageSize?: string,
        public isUsed?: boolean,
        public actorId?: number
    ) {
        this.isUsed = this.isUsed || false;
    }
}
