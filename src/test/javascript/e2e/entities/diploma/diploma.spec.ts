/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiplomaComponentsPage, DiplomaDeleteDialog, DiplomaUpdatePage } from './diploma.page-object';

const expect = chai.expect;

describe('Diploma e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let diplomaUpdatePage: DiplomaUpdatePage;
    let diplomaComponentsPage: DiplomaComponentsPage;
    let diplomaDeleteDialog: DiplomaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Diplomas', async () => {
        await navBarPage.goToEntity('diploma');
        diplomaComponentsPage = new DiplomaComponentsPage();
        await browser.wait(ec.visibilityOf(diplomaComponentsPage.title), 5000);
        expect(await diplomaComponentsPage.getTitle()).to.eq('mySchoolApp.diploma.home.title');
    });

    it('should load create Diploma page', async () => {
        await diplomaComponentsPage.clickOnCreateButton();
        diplomaUpdatePage = new DiplomaUpdatePage();
        expect(await diplomaUpdatePage.getPageTitle()).to.eq('mySchoolApp.diploma.home.createOrEditLabel');
        await diplomaUpdatePage.cancel();
    });

    it('should create and save Diplomas', async () => {
        const nbButtonsBeforeCreate = await diplomaComponentsPage.countDeleteButtons();

        await diplomaComponentsPage.clickOnCreateButton();
        await promise.all([
            diplomaUpdatePage.setDiplomaLabelInput('diplomaLabel'),
            diplomaUpdatePage.setDescriptionInput('description'),
            diplomaUpdatePage.cycleSelectLastOption(),
            diplomaUpdatePage.parentDiplomaSelectLastOption()
        ]);
        expect(await diplomaUpdatePage.getDiplomaLabelInput()).to.eq('diplomaLabel');
        expect(await diplomaUpdatePage.getDescriptionInput()).to.eq('description');
        await diplomaUpdatePage.save();
        expect(await diplomaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await diplomaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Diploma', async () => {
        const nbButtonsBeforeDelete = await diplomaComponentsPage.countDeleteButtons();
        await diplomaComponentsPage.clickOnLastDeleteButton();

        diplomaDeleteDialog = new DiplomaDeleteDialog();
        expect(await diplomaDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.diploma.delete.question');
        await diplomaDeleteDialog.clickOnConfirmButton();

        expect(await diplomaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
