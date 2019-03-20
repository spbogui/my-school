/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EvaluationModeComponentsPage, EvaluationModeDeleteDialog, EvaluationModeUpdatePage } from './evaluation-mode.page-object';

const expect = chai.expect;

describe('EvaluationMode e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let evaluationModeUpdatePage: EvaluationModeUpdatePage;
    let evaluationModeComponentsPage: EvaluationModeComponentsPage;
    let evaluationModeDeleteDialog: EvaluationModeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load EvaluationModes', async () => {
        await navBarPage.goToEntity('evaluation-mode');
        evaluationModeComponentsPage = new EvaluationModeComponentsPage();
        await browser.wait(ec.visibilityOf(evaluationModeComponentsPage.title), 5000);
        expect(await evaluationModeComponentsPage.getTitle()).to.eq('mySchoolApp.evaluationMode.home.title');
    });

    it('should load create EvaluationMode page', async () => {
        await evaluationModeComponentsPage.clickOnCreateButton();
        evaluationModeUpdatePage = new EvaluationModeUpdatePage();
        expect(await evaluationModeUpdatePage.getPageTitle()).to.eq('mySchoolApp.evaluationMode.home.createOrEditLabel');
        await evaluationModeUpdatePage.cancel();
    });

    it('should create and save EvaluationModes', async () => {
        const nbButtonsBeforeCreate = await evaluationModeComponentsPage.countDeleteButtons();

        await evaluationModeComponentsPage.clickOnCreateButton();
        await promise.all([evaluationModeUpdatePage.setNameInput('name'), evaluationModeUpdatePage.setDescriptionInput('description')]);
        expect(await evaluationModeUpdatePage.getNameInput()).to.eq('name');
        expect(await evaluationModeUpdatePage.getDescriptionInput()).to.eq('description');
        await evaluationModeUpdatePage.save();
        expect(await evaluationModeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await evaluationModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last EvaluationMode', async () => {
        const nbButtonsBeforeDelete = await evaluationModeComponentsPage.countDeleteButtons();
        await evaluationModeComponentsPage.clickOnLastDeleteButton();

        evaluationModeDeleteDialog = new EvaluationModeDeleteDialog();
        expect(await evaluationModeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.evaluationMode.delete.question');
        await evaluationModeDeleteDialog.clickOnConfirmButton();

        expect(await evaluationModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
