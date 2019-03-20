/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EvaluationComponentsPage, EvaluationDeleteDialog, EvaluationUpdatePage } from './evaluation.page-object';

const expect = chai.expect;

describe('Evaluation e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let evaluationUpdatePage: EvaluationUpdatePage;
    let evaluationComponentsPage: EvaluationComponentsPage;
    let evaluationDeleteDialog: EvaluationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Evaluations', async () => {
        await navBarPage.goToEntity('evaluation');
        evaluationComponentsPage = new EvaluationComponentsPage();
        await browser.wait(ec.visibilityOf(evaluationComponentsPage.title), 5000);
        expect(await evaluationComponentsPage.getTitle()).to.eq('mySchoolApp.evaluation.home.title');
    });

    it('should load create Evaluation page', async () => {
        await evaluationComponentsPage.clickOnCreateButton();
        evaluationUpdatePage = new EvaluationUpdatePage();
        expect(await evaluationUpdatePage.getPageTitle()).to.eq('mySchoolApp.evaluation.home.createOrEditLabel');
        await evaluationUpdatePage.cancel();
    });

    it('should create and save Evaluations', async () => {
        const nbButtonsBeforeCreate = await evaluationComponentsPage.countDeleteButtons();

        await evaluationComponentsPage.clickOnCreateButton();
        await promise.all([
            evaluationUpdatePage.setPlannedDateInput('2000-12-31'),
            evaluationUpdatePage.setEvaluationDateInput('2000-12-31'),
            evaluationUpdatePage.setDurationInput('5'),
            evaluationUpdatePage.evaluationTypeSelectLastOption(),
            evaluationUpdatePage.schoolSchoolYearSelectLastOption(),
            evaluationUpdatePage.periodSelectLastOption()
            // evaluationUpdatePage.classRoomSelectLastOption(),
        ]);
        expect(await evaluationUpdatePage.getPlannedDateInput()).to.eq('2000-12-31');
        const selectedIsDone = evaluationUpdatePage.getIsDoneInput();
        if (await selectedIsDone.isSelected()) {
            await evaluationUpdatePage.getIsDoneInput().click();
            expect(await evaluationUpdatePage.getIsDoneInput().isSelected()).to.be.false;
        } else {
            await evaluationUpdatePage.getIsDoneInput().click();
            expect(await evaluationUpdatePage.getIsDoneInput().isSelected()).to.be.true;
        }
        expect(await evaluationUpdatePage.getEvaluationDateInput()).to.eq('2000-12-31');
        expect(await evaluationUpdatePage.getDurationInput()).to.eq('5');
        await evaluationUpdatePage.save();
        expect(await evaluationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await evaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Evaluation', async () => {
        const nbButtonsBeforeDelete = await evaluationComponentsPage.countDeleteButtons();
        await evaluationComponentsPage.clickOnLastDeleteButton();

        evaluationDeleteDialog = new EvaluationDeleteDialog();
        expect(await evaluationDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.evaluation.delete.question');
        await evaluationDeleteDialog.clickOnConfirmButton();

        expect(await evaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
