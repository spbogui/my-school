/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    PermissionAgreementComponentsPage,
    PermissionAgreementDeleteDialog,
    PermissionAgreementUpdatePage
} from './permission-agreement.page-object';

const expect = chai.expect;

describe('PermissionAgreement e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let permissionAgreementUpdatePage: PermissionAgreementUpdatePage;
    let permissionAgreementComponentsPage: PermissionAgreementComponentsPage;
    let permissionAgreementDeleteDialog: PermissionAgreementDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PermissionAgreements', async () => {
        await navBarPage.goToEntity('permission-agreement');
        permissionAgreementComponentsPage = new PermissionAgreementComponentsPage();
        await browser.wait(ec.visibilityOf(permissionAgreementComponentsPage.title), 5000);
        expect(await permissionAgreementComponentsPage.getTitle()).to.eq('mySchoolApp.permissionAgreement.home.title');
    });

    it('should load create PermissionAgreement page', async () => {
        await permissionAgreementComponentsPage.clickOnCreateButton();
        permissionAgreementUpdatePage = new PermissionAgreementUpdatePage();
        expect(await permissionAgreementUpdatePage.getPageTitle()).to.eq('mySchoolApp.permissionAgreement.home.createOrEditLabel');
        await permissionAgreementUpdatePage.cancel();
    });

    it('should create and save PermissionAgreements', async () => {
        const nbButtonsBeforeCreate = await permissionAgreementComponentsPage.countDeleteButtons();

        await permissionAgreementComponentsPage.clickOnCreateButton();
        await promise.all([
            permissionAgreementUpdatePage.setAllowanceDateInput('2000-12-31'),
            permissionAgreementUpdatePage.setPermissionStartDateInput('2000-12-31'),
            permissionAgreementUpdatePage.setPermissionEndDateInput('2000-12-31'),
            permissionAgreementUpdatePage.setReturnDateInput('2000-12-31'),
            permissionAgreementUpdatePage.setEffectiveReturnDateInput('2000-12-31'),
            permissionAgreementUpdatePage.setMemoInput('memo'),
            permissionAgreementUpdatePage.askingPermissionSelectLastOption()
        ]);
        const selectedPermissionAllowed = permissionAgreementUpdatePage.getPermissionAllowedInput();
        if (await selectedPermissionAllowed.isSelected()) {
            await permissionAgreementUpdatePage.getPermissionAllowedInput().click();
            expect(await permissionAgreementUpdatePage.getPermissionAllowedInput().isSelected()).to.be.false;
        } else {
            await permissionAgreementUpdatePage.getPermissionAllowedInput().click();
            expect(await permissionAgreementUpdatePage.getPermissionAllowedInput().isSelected()).to.be.true;
        }
        expect(await permissionAgreementUpdatePage.getAllowanceDateInput()).to.eq('2000-12-31');
        expect(await permissionAgreementUpdatePage.getPermissionStartDateInput()).to.eq('2000-12-31');
        expect(await permissionAgreementUpdatePage.getPermissionEndDateInput()).to.eq('2000-12-31');
        expect(await permissionAgreementUpdatePage.getReturnDateInput()).to.eq('2000-12-31');
        expect(await permissionAgreementUpdatePage.getEffectiveReturnDateInput()).to.eq('2000-12-31');
        expect(await permissionAgreementUpdatePage.getMemoInput()).to.eq('memo');
        await permissionAgreementUpdatePage.save();
        expect(await permissionAgreementUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await permissionAgreementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PermissionAgreement', async () => {
        const nbButtonsBeforeDelete = await permissionAgreementComponentsPage.countDeleteButtons();
        await permissionAgreementComponentsPage.clickOnLastDeleteButton();

        permissionAgreementDeleteDialog = new PermissionAgreementDeleteDialog();
        expect(await permissionAgreementDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.permissionAgreement.delete.question');
        await permissionAgreementDeleteDialog.clickOnConfirmButton();

        expect(await permissionAgreementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
