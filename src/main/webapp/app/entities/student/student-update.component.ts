import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';

@Component({
    selector: 'jhi-student-update',
    templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
    student: IStudent;
    isSaving: boolean;

    actors: IActor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentService: StudentService,
        protected actorService: ActorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ student }) => {
            this.student = student;
        });
        this.actorService
            .query({ filter: 'student-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe(
                (res: IActor[]) => {
                    if (!this.student.actorId) {
                        this.actors = res;
                    } else {
                        this.actorService
                            .find(this.student.actorId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IActor>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IActor>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IActor) => (this.actors = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.student.id !== undefined) {
            this.subscribeToSaveResponse(this.studentService.update(this.student));
        } else {
            this.subscribeToSaveResponse(this.studentService.create(this.student));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
        result.subscribe((res: HttpResponse<IStudent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackActorById(index: number, item: IActor) {
        return item.id;
    }
}
