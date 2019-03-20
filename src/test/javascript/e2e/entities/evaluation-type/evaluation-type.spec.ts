/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EvaluationTypeComponentsPage, EvaluationTypeDeleteDialog, EvaluationTypeUpdatePage } from './evaluation-type.page-object';

const expect = chai.expect;

describe('EvaluationType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let evaluationTypeUpdatePage: EvaluationTypeUpdatePage;
    let evaluationTypeComponentsPage: EvaluationTypeComponentsPage;
    let evaluationTypeDeleteDialog: EvaluationTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load EvaluationTypes', async () => {
        await navBarPage.goToEntity('evaluation-type');
        evaluationTypeComponentsPage = new EvaluationTypeComponentsPage();
        await browser.wait(ec.visibilityOf(evaluationTypeComponentsPage.title), 5000);
        expect(await evaluationTypeComponentsPage.getTitle()).to.eq('mySchoolApp.evaluationType.home.title');
    });

    it('should load create EvaluationType page', async () => {
        await evaluationTypeComponentsPage.clickOnCreateButton();
        evaluationTypeUpdatePage = new EvaluationTypeUpdatePage();
        expect(await evaluationTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.evaluationType.home.createOrEditLabel');
        await evaluationTypeUpdatePage.cancel();
    });

    it('should create and save EvaluationTypes', async () => {
        const nbButtonsBeforeCreate = await evaluationTypeComponentsPage.countDeleteButtons();

        await evaluationTypeComponentsPage.clickOnCreateButton();
        await promise.all([evaluationTypeUpdatePage.setNameInput('name'), evaluationTypeUpdatePage.setDescriptionInput('description')]);
        expect(await evaluationTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await evaluationTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await evaluationTypeUpdatePage.save();
        expect(await evaluationTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await evaluationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last EvaluationType', async () => {
        const nbButtonsBeforeDelete = await evaluationTypeComponentsPage.countDeleteButtons();
        await evaluationTypeComponentsPage.clickOnLastDeleteButton();

        evaluationTypeDeleteDialog = new EvaluationTypeDeleteDialog();
        expect(await evaluationTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.evaluationType.delete.question');
        await evaluationTypeDeleteDialog.clickOnConfirmButton();

        expect(await evaluationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
