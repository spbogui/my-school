/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AskingPermissionComponentsPage, AskingPermissionDeleteDialog, AskingPermissionUpdatePage } from './asking-permission.page-object';

const expect = chai.expect;

describe('AskingPermission e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let askingPermissionUpdatePage: AskingPermissionUpdatePage;
    let askingPermissionComponentsPage: AskingPermissionComponentsPage;
    let askingPermissionDeleteDialog: AskingPermissionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AskingPermissions', async () => {
        await navBarPage.goToEntity('asking-permission');
        askingPermissionComponentsPage = new AskingPermissionComponentsPage();
        await browser.wait(ec.visibilityOf(askingPermissionComponentsPage.title), 5000);
        expect(await askingPermissionComponentsPage.getTitle()).to.eq('mySchoolApp.askingPermission.home.title');
    });

    it('should load create AskingPermission page', async () => {
        await askingPermissionComponentsPage.clickOnCreateButton();
        askingPermissionUpdatePage = new AskingPermissionUpdatePage();
        expect(await askingPermissionUpdatePage.getPageTitle()).to.eq('mySchoolApp.askingPermission.home.createOrEditLabel');
        await askingPermissionUpdatePage.cancel();
    });

    it('should create and save AskingPermissions', async () => {
        const nbButtonsBeforeCreate = await askingPermissionComponentsPage.countDeleteButtons();

        await askingPermissionComponentsPage.clickOnCreateButton();
        await promise.all([
            askingPermissionUpdatePage.setAskingDateInput('2000-12-31'),
            askingPermissionUpdatePage.setNumberOfDayInput('5'),
            askingPermissionUpdatePage.setReasonInput('reason'),
            askingPermissionUpdatePage.schoolSchoolYearSelectLastOption(),
            askingPermissionUpdatePage.studentSelectLastOption()
        ]);
        expect(await askingPermissionUpdatePage.getAskingDateInput()).to.eq('2000-12-31');
        expect(await askingPermissionUpdatePage.getNumberOfDayInput()).to.eq('5');
        expect(await askingPermissionUpdatePage.getReasonInput()).to.eq('reason');
        await askingPermissionUpdatePage.save();
        expect(await askingPermissionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await askingPermissionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AskingPermission', async () => {
        const nbButtonsBeforeDelete = await askingPermissionComponentsPage.countDeleteButtons();
        await askingPermissionComponentsPage.clickOnLastDeleteButton();

        askingPermissionDeleteDialog = new AskingPermissionDeleteDialog();
        expect(await askingPermissionDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.askingPermission.delete.question');
        await askingPermissionDeleteDialog.clickOnConfirmButton();

        expect(await askingPermissionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
