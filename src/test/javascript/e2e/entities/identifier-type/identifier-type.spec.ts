/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IdentifierTypeComponentsPage, IdentifierTypeDeleteDialog, IdentifierTypeUpdatePage } from './identifier-type.page-object';

const expect = chai.expect;

describe('IdentifierType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let identifierTypeUpdatePage: IdentifierTypeUpdatePage;
    let identifierTypeComponentsPage: IdentifierTypeComponentsPage;
    let identifierTypeDeleteDialog: IdentifierTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IdentifierTypes', async () => {
        await navBarPage.goToEntity('identifier-type');
        identifierTypeComponentsPage = new IdentifierTypeComponentsPage();
        await browser.wait(ec.visibilityOf(identifierTypeComponentsPage.title), 5000);
        expect(await identifierTypeComponentsPage.getTitle()).to.eq('mySchoolApp.identifierType.home.title');
    });

    it('should load create IdentifierType page', async () => {
        await identifierTypeComponentsPage.clickOnCreateButton();
        identifierTypeUpdatePage = new IdentifierTypeUpdatePage();
        expect(await identifierTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.identifierType.home.createOrEditLabel');
        await identifierTypeUpdatePage.cancel();
    });

    it('should create and save IdentifierTypes', async () => {
        const nbButtonsBeforeCreate = await identifierTypeComponentsPage.countDeleteButtons();

        await identifierTypeComponentsPage.clickOnCreateButton();
        await promise.all([identifierTypeUpdatePage.setNameInput('name')]);
        expect(await identifierTypeUpdatePage.getNameInput()).to.eq('name');
        await identifierTypeUpdatePage.save();
        expect(await identifierTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await identifierTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IdentifierType', async () => {
        const nbButtonsBeforeDelete = await identifierTypeComponentsPage.countDeleteButtons();
        await identifierTypeComponentsPage.clickOnLastDeleteButton();

        identifierTypeDeleteDialog = new IdentifierTypeDeleteDialog();
        expect(await identifierTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.identifierType.delete.question');
        await identifierTypeDeleteDialog.clickOnConfirmButton();

        expect(await identifierTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
