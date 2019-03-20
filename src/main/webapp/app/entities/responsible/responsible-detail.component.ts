import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsible } from 'app/shared/model/responsible.model';

@Component({
    selector: 'jhi-responsible-detail',
    templateUrl: './responsible-detail.component.html'
})
export class ResponsibleDetailComponent implements OnInit {
    responsible: IResponsible;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ responsible }) => {
            this.responsible = responsible;
        });
    }

    previousState() {
        window.history.back();
    }
}
