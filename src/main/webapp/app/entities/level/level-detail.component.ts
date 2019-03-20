import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevel } from 'app/shared/model/level.model';

@Component({
    selector: 'jhi-level-detail',
    templateUrl: './level-detail.component.html'
})
export class LevelDetailComponent implements OnInit {
    level: ILevel;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ level }) => {
            this.level = level;
        });
    }

    previousState() {
        window.history.back();
    }
}
