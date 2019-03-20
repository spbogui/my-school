/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    StudentMissingSessionComponentsPage,
    StudentMissingSessionDeleteDialog,
    StudentMissingSessionUpdatePage
} from './student-missing-session.page-object';

const expect = chai.expect;

describe('StudentMissingSession e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let studentMissingSessionUpdatePage: StudentMissingSessionUpdatePage;
    let studentMissingSessionComponentsPage: StudentMissingSessionComponentsPage;
    let studentMissingSessionDeleteDialog: StudentMissingSessionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StudentMissingSessions', async () => {
        await navBarPage.goToEntity('student-missing-session');
        studentMissingSessionComponentsPage = new StudentMissingSessionComponentsPage();
        await browser.wait(ec.visibilityOf(studentMissingSessionComponentsPage.title), 5000);
        expect(await studentMissingSessionComponentsPage.getTitle()).to.eq('mySchoolApp.studentMissingSession.home.title');
    });

    it('should load create StudentMissingSession page', async () => {
        await studentMissingSessionComponentsPage.clickOnCreateButton();
        studentMissingSessionUpdatePage = new StudentMissingSessionUpdatePage();
        expect(await studentMissingSessionUpdatePage.getPageTitle()).to.eq('mySchoolApp.studentMissingSession.home.createOrEditLabel');
        await studentMissingSessionUpdatePage.cancel();
    });

    it('should create and save StudentMissingSessions', async () => {
        const nbButtonsBeforeCreate = await studentMissingSessionComponentsPage.countDeleteButtons();

        await studentMissingSessionComponentsPage.clickOnCreateButton();
        await promise.all([
            studentMissingSessionUpdatePage.classSessionSelectLastOption(),
            studentMissingSessionUpdatePage.studentSelectLastOption()
        ]);
        const selectedIsJustified = studentMissingSessionUpdatePage.getIsJustifiedInput();
        if (await selectedIsJustified.isSelected()) {
            await studentMissingSessionUpdatePage.getIsJustifiedInput().click();
            expect(await studentMissingSessionUpdatePage.getIsJustifiedInput().isSelected()).to.be.false;
        } else {
            await studentMissingSessionUpdatePage.getIsJustifiedInput().click();
            expect(await studentMissingSessionUpdatePage.getIsJustifiedInput().isSelected()).to.be.true;
        }
        await studentMissingSessionUpdatePage.save();
        expect(await studentMissingSessionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await studentMissingSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StudentMissingSession', async () => {
        const nbButtonsBeforeDelete = await studentMissingSessionComponentsPage.countDeleteButtons();
        await studentMissingSessionComponentsPage.clickOnLastDeleteButton();

        studentMissingSessionDeleteDialog = new StudentMissingSessionDeleteDialog();
        expect(await studentMissingSessionDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.studentMissingSession.delete.question');
        await studentMissingSessionDeleteDialog.clickOnConfirmButton();

        expect(await studentMissingSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
