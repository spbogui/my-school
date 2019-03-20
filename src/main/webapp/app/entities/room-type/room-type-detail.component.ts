import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoomType } from 'app/shared/model/room-type.model';

@Component({
    selector: 'jhi-room-type-detail',
    templateUrl: './room-type-detail.component.html'
})
export class RoomTypeDetailComponent implements OnInit {
    roomType: IRoomType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomType }) => {
            this.roomType = roomType;
        });
    }

    previousState() {
        window.history.back();
    }
}
