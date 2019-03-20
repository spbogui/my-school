/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LeaveHoliDayComponentsPage, LeaveHoliDayDeleteDialog, LeaveHoliDayUpdatePage } from './leave-holi-day.page-object';

const expect = chai.expect;

describe('LeaveHoliDay e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let leaveHoliDayUpdatePage: LeaveHoliDayUpdatePage;
    let leaveHoliDayComponentsPage: LeaveHoliDayComponentsPage;
    let leaveHoliDayDeleteDialog: LeaveHoliDayDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load LeaveHoliDays', async () => {
        await navBarPage.goToEntity('leave-holi-day');
        leaveHoliDayComponentsPage = new LeaveHoliDayComponentsPage();
        await browser.wait(ec.visibilityOf(leaveHoliDayComponentsPage.title), 5000);
        expect(await leaveHoliDayComponentsPage.getTitle()).to.eq('mySchoolApp.leaveHoliDay.home.title');
    });

    it('should load create LeaveHoliDay page', async () => {
        await leaveHoliDayComponentsPage.clickOnCreateButton();
        leaveHoliDayUpdatePage = new LeaveHoliDayUpdatePage();
        expect(await leaveHoliDayUpdatePage.getPageTitle()).to.eq('mySchoolApp.leaveHoliDay.home.createOrEditLabel');
        await leaveHoliDayUpdatePage.cancel();
    });

    it('should create and save LeaveHoliDays', async () => {
        const nbButtonsBeforeCreate = await leaveHoliDayComponentsPage.countDeleteButtons();

        await leaveHoliDayComponentsPage.clickOnCreateButton();
        await promise.all([
            leaveHoliDayUpdatePage.setLabelInput('label'),
            leaveHoliDayUpdatePage.setStartDateInput('2000-12-31'),
            leaveHoliDayUpdatePage.setEndDateInput('2000-12-31'),
            leaveHoliDayUpdatePage.setMemoInput('memo'),
            leaveHoliDayUpdatePage.setCreatedAtInput('2000-12-31'),
            leaveHoliDayUpdatePage.schoolYearSelectLastOption()
        ]);
        expect(await leaveHoliDayUpdatePage.getLabelInput()).to.eq('label');
        expect(await leaveHoliDayUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await leaveHoliDayUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        expect(await leaveHoliDayUpdatePage.getMemoInput()).to.eq('memo');
        expect(await leaveHoliDayUpdatePage.getCreatedAtInput()).to.eq('2000-12-31');
        await leaveHoliDayUpdatePage.save();
        expect(await leaveHoliDayUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await leaveHoliDayComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last LeaveHoliDay', async () => {
        const nbButtonsBeforeDelete = await leaveHoliDayComponentsPage.countDeleteButtons();
        await leaveHoliDayComponentsPage.clickOnLastDeleteButton();

        leaveHoliDayDeleteDialog = new LeaveHoliDayDeleteDialog();
        expect(await leaveHoliDayDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.leaveHoliDay.delete.question');
        await leaveHoliDayDeleteDialog.clickOnConfirmButton();

        expect(await leaveHoliDayComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
