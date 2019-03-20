/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StaffComponentsPage, StaffDeleteDialog, StaffUpdatePage } from './staff.page-object';

const expect = chai.expect;

describe('Staff e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let staffUpdatePage: StaffUpdatePage;
    let staffComponentsPage: StaffComponentsPage;
    let staffDeleteDialog: StaffDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Staff', async () => {
        await navBarPage.goToEntity('staff');
        staffComponentsPage = new StaffComponentsPage();
        await browser.wait(ec.visibilityOf(staffComponentsPage.title), 5000);
        expect(await staffComponentsPage.getTitle()).to.eq('mySchoolApp.staff.home.title');
    });

    it('should load create Staff page', async () => {
        await staffComponentsPage.clickOnCreateButton();
        staffUpdatePage = new StaffUpdatePage();
        expect(await staffUpdatePage.getPageTitle()).to.eq('mySchoolApp.staff.home.createOrEditLabel');
        await staffUpdatePage.cancel();
    });

    it('should create and save Staff', async () => {
        const nbButtonsBeforeCreate = await staffComponentsPage.countDeleteButtons();

        await staffComponentsPage.clickOnCreateButton();
        await promise.all([staffUpdatePage.employeeSelectLastOption()]);
        await staffUpdatePage.save();
        expect(await staffUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await staffComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Staff', async () => {
        const nbButtonsBeforeDelete = await staffComponentsPage.countDeleteButtons();
        await staffComponentsPage.clickOnLastDeleteButton();

        staffDeleteDialog = new StaffDeleteDialog();
        expect(await staffDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.staff.delete.question');
        await staffDeleteDialog.clickOnConfirmButton();

        expect(await staffComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
