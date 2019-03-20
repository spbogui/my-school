/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RegimenComponentsPage, RegimenDeleteDialog, RegimenUpdatePage } from './regimen.page-object';

const expect = chai.expect;

describe('Regimen e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let regimenUpdatePage: RegimenUpdatePage;
    let regimenComponentsPage: RegimenComponentsPage;
    let regimenDeleteDialog: RegimenDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Regimen', async () => {
        await navBarPage.goToEntity('regimen');
        regimenComponentsPage = new RegimenComponentsPage();
        await browser.wait(ec.visibilityOf(regimenComponentsPage.title), 5000);
        expect(await regimenComponentsPage.getTitle()).to.eq('mySchoolApp.regimen.home.title');
    });

    it('should load create Regimen page', async () => {
        await regimenComponentsPage.clickOnCreateButton();
        regimenUpdatePage = new RegimenUpdatePage();
        expect(await regimenUpdatePage.getPageTitle()).to.eq('mySchoolApp.regimen.home.createOrEditLabel');
        await regimenUpdatePage.cancel();
    });

    it('should create and save Regimen', async () => {
        const nbButtonsBeforeCreate = await regimenComponentsPage.countDeleteButtons();

        await regimenComponentsPage.clickOnCreateButton();
        await promise.all([regimenUpdatePage.setRegimenLabelInput('regimenLabel'), regimenUpdatePage.setDescriptionInput('description')]);
        expect(await regimenUpdatePage.getRegimenLabelInput()).to.eq('regimenLabel');
        expect(await regimenUpdatePage.getDescriptionInput()).to.eq('description');
        await regimenUpdatePage.save();
        expect(await regimenUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await regimenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Regimen', async () => {
        const nbButtonsBeforeDelete = await regimenComponentsPage.countDeleteButtons();
        await regimenComponentsPage.clickOnLastDeleteButton();

        regimenDeleteDialog = new RegimenDeleteDialog();
        expect(await regimenDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.regimen.delete.question');
        await regimenDeleteDialog.clickOnConfirmButton();

        expect(await regimenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
