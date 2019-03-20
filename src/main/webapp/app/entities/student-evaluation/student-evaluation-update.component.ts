import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';
import { StudentEvaluationService } from './student-evaluation.service';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from 'app/entities/evaluation';
import { IEvaluationMode } from 'app/shared/model/evaluation-mode.model';
import { EvaluationModeService } from 'app/entities/evaluation-mode';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';

@Component({
    selector: 'jhi-student-evaluation-update',
    templateUrl: './student-evaluation-update.component.html'
})
export class StudentEvaluationUpdateComponent implements OnInit {
    studentEvaluation: IStudentEvaluation;
    isSaving: boolean;

    actors: IActor[];

    evaluations: IEvaluation[];

    evaluationmodes: IEvaluationMode[];

    subjects: ISubject[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentEvaluationService: StudentEvaluationService,
        protected actorService: ActorService,
        protected evaluationService: EvaluationService,
        protected evaluationModeService: EvaluationModeService,
        protected subjectService: SubjectService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentEvaluation }) => {
            this.studentEvaluation = studentEvaluation;
        });
        this.actorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IActor[]>) => response.body)
            )
            .subscribe((res: IActor[]) => (this.actors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.evaluationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEvaluation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEvaluation[]>) => response.body)
            )
            .subscribe((res: IEvaluation[]) => (this.evaluations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.evaluationModeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEvaluationMode[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEvaluationMode[]>) => response.body)
            )
            .subscribe((res: IEvaluationMode[]) => (this.evaluationmodes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.subjectService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISubject[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISubject[]>) => response.body)
            )
            .subscribe((res: ISubject[]) => (this.subjects = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentEvaluation.id !== undefined) {
            this.subscribeToSaveResponse(this.studentEvaluationService.update(this.studentEvaluation));
        } else {
            this.subscribeToSaveResponse(this.studentEvaluationService.create(this.studentEvaluation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentEvaluation>>) {
        result.subscribe((res: HttpResponse<IStudentEvaluation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEvaluationById(index: number, item: IEvaluation) {
        return item.id;
    }

    trackEvaluationModeById(index: number, item: IEvaluationMode) {
        return item.id;
    }

    trackSubjectById(index: number, item: ISubject) {
        return item.id;
    }
}
