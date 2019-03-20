/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StaffJobComponentsPage, StaffJobDeleteDialog, StaffJobUpdatePage } from './staff-job.page-object';

const expect = chai.expect;

describe('StaffJob e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let staffJobUpdatePage: StaffJobUpdatePage;
    let staffJobComponentsPage: StaffJobComponentsPage;
    let staffJobDeleteDialog: StaffJobDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StaffJobs', async () => {
        await navBarPage.goToEntity('staff-job');
        staffJobComponentsPage = new StaffJobComponentsPage();
        await browser.wait(ec.visibilityOf(staffJobComponentsPage.title), 5000);
        expect(await staffJobComponentsPage.getTitle()).to.eq('mySchoolApp.staffJob.home.title');
    });

    it('should load create StaffJob page', async () => {
        await staffJobComponentsPage.clickOnCreateButton();
        staffJobUpdatePage = new StaffJobUpdatePage();
        expect(await staffJobUpdatePage.getPageTitle()).to.eq('mySchoolApp.staffJob.home.createOrEditLabel');
        await staffJobUpdatePage.cancel();
    });

    it('should create and save StaffJobs', async () => {
        const nbButtonsBeforeCreate = await staffJobComponentsPage.countDeleteButtons();

        await staffJobComponentsPage.clickOnCreateButton();
        await promise.all([
            staffJobUpdatePage.setStartDateInput('2000-12-31'),
            staffJobUpdatePage.setEndDateInput('2000-12-31'),
            staffJobUpdatePage.staffSelectLastOption(),
            staffJobUpdatePage.jobSelectLastOption()
        ]);
        expect(await staffJobUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await staffJobUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await staffJobUpdatePage.save();
        expect(await staffJobUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await staffJobComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StaffJob', async () => {
        const nbButtonsBeforeDelete = await staffJobComponentsPage.countDeleteButtons();
        await staffJobComponentsPage.clickOnLastDeleteButton();

        staffJobDeleteDialog = new StaffJobDeleteDialog();
        expect(await staffJobDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.staffJob.delete.question');
        await staffJobDeleteDialog.clickOnConfirmButton();

        expect(await staffJobComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
