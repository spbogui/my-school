/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ResponsibleComponentsPage, ResponsibleDeleteDialog, ResponsibleUpdatePage } from './responsible.page-object';

const expect = chai.expect;

describe('Responsible e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let responsibleUpdatePage: ResponsibleUpdatePage;
    let responsibleComponentsPage: ResponsibleComponentsPage;
    let responsibleDeleteDialog: ResponsibleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Responsibles', async () => {
        await navBarPage.goToEntity('responsible');
        responsibleComponentsPage = new ResponsibleComponentsPage();
        await browser.wait(ec.visibilityOf(responsibleComponentsPage.title), 5000);
        expect(await responsibleComponentsPage.getTitle()).to.eq('mySchoolApp.responsible.home.title');
    });

    it('should load create Responsible page', async () => {
        await responsibleComponentsPage.clickOnCreateButton();
        responsibleUpdatePage = new ResponsibleUpdatePage();
        expect(await responsibleUpdatePage.getPageTitle()).to.eq('mySchoolApp.responsible.home.createOrEditLabel');
        await responsibleUpdatePage.cancel();
    });

    it('should create and save Responsibles', async () => {
        const nbButtonsBeforeCreate = await responsibleComponentsPage.countDeleteButtons();

        await responsibleComponentsPage.clickOnCreateButton();
        await promise.all([responsibleUpdatePage.setProfessionInput('profession'), responsibleUpdatePage.actorSelectLastOption()]);
        expect(await responsibleUpdatePage.getProfessionInput()).to.eq('profession');
        await responsibleUpdatePage.save();
        expect(await responsibleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await responsibleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Responsible', async () => {
        const nbButtonsBeforeDelete = await responsibleComponentsPage.countDeleteButtons();
        await responsibleComponentsPage.clickOnLastDeleteButton();

        responsibleDeleteDialog = new ResponsibleDeleteDialog();
        expect(await responsibleDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.responsible.delete.question');
        await responsibleDeleteDialog.clickOnConfirmButton();

        expect(await responsibleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
