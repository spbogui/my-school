/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    StudentEvaluationComponentsPage,
    StudentEvaluationDeleteDialog,
    StudentEvaluationUpdatePage
} from './student-evaluation.page-object';

const expect = chai.expect;

describe('StudentEvaluation e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let studentEvaluationUpdatePage: StudentEvaluationUpdatePage;
    let studentEvaluationComponentsPage: StudentEvaluationComponentsPage;
    let studentEvaluationDeleteDialog: StudentEvaluationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StudentEvaluations', async () => {
        await navBarPage.goToEntity('student-evaluation');
        studentEvaluationComponentsPage = new StudentEvaluationComponentsPage();
        await browser.wait(ec.visibilityOf(studentEvaluationComponentsPage.title), 5000);
        expect(await studentEvaluationComponentsPage.getTitle()).to.eq('mySchoolApp.studentEvaluation.home.title');
    });

    it('should load create StudentEvaluation page', async () => {
        await studentEvaluationComponentsPage.clickOnCreateButton();
        studentEvaluationUpdatePage = new StudentEvaluationUpdatePage();
        expect(await studentEvaluationUpdatePage.getPageTitle()).to.eq('mySchoolApp.studentEvaluation.home.createOrEditLabel');
        await studentEvaluationUpdatePage.cancel();
    });

    it('should create and save StudentEvaluations', async () => {
        const nbButtonsBeforeCreate = await studentEvaluationComponentsPage.countDeleteButtons();

        await studentEvaluationComponentsPage.clickOnCreateButton();
        await promise.all([
            studentEvaluationUpdatePage.setGradeInput('5'),
            studentEvaluationUpdatePage.actorSelectLastOption(),
            studentEvaluationUpdatePage.evaluationSelectLastOption(),
            studentEvaluationUpdatePage.evaluationModeSelectLastOption(),
            studentEvaluationUpdatePage.subjectSelectLastOption()
        ]);
        expect(await studentEvaluationUpdatePage.getGradeInput()).to.eq('5');
        await studentEvaluationUpdatePage.save();
        expect(await studentEvaluationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await studentEvaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StudentEvaluation', async () => {
        const nbButtonsBeforeDelete = await studentEvaluationComponentsPage.countDeleteButtons();
        await studentEvaluationComponentsPage.clickOnLastDeleteButton();

        studentEvaluationDeleteDialog = new StudentEvaluationDeleteDialog();
        expect(await studentEvaluationDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.studentEvaluation.delete.question');
        await studentEvaluationDeleteDialog.clickOnConfirmButton();

        expect(await studentEvaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
