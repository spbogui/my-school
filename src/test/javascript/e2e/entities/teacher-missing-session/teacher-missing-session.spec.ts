/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TeacherMissingSessionComponentsPage,
    TeacherMissingSessionDeleteDialog,
    TeacherMissingSessionUpdatePage
} from './teacher-missing-session.page-object';

const expect = chai.expect;

describe('TeacherMissingSession e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let teacherMissingSessionUpdatePage: TeacherMissingSessionUpdatePage;
    let teacherMissingSessionComponentsPage: TeacherMissingSessionComponentsPage;
    let teacherMissingSessionDeleteDialog: TeacherMissingSessionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TeacherMissingSessions', async () => {
        await navBarPage.goToEntity('teacher-missing-session');
        teacherMissingSessionComponentsPage = new TeacherMissingSessionComponentsPage();
        await browser.wait(ec.visibilityOf(teacherMissingSessionComponentsPage.title), 5000);
        expect(await teacherMissingSessionComponentsPage.getTitle()).to.eq('mySchoolApp.teacherMissingSession.home.title');
    });

    it('should load create TeacherMissingSession page', async () => {
        await teacherMissingSessionComponentsPage.clickOnCreateButton();
        teacherMissingSessionUpdatePage = new TeacherMissingSessionUpdatePage();
        expect(await teacherMissingSessionUpdatePage.getPageTitle()).to.eq('mySchoolApp.teacherMissingSession.home.createOrEditLabel');
        await teacherMissingSessionUpdatePage.cancel();
    });

    it('should create and save TeacherMissingSessions', async () => {
        const nbButtonsBeforeCreate = await teacherMissingSessionComponentsPage.countDeleteButtons();

        await teacherMissingSessionComponentsPage.clickOnCreateButton();
        await promise.all([
            teacherMissingSessionUpdatePage.classSessionSelectLastOption(),
            teacherMissingSessionUpdatePage.studentSelectLastOption()
        ]);
        const selectedIsJustified = teacherMissingSessionUpdatePage.getIsJustifiedInput();
        if (await selectedIsJustified.isSelected()) {
            await teacherMissingSessionUpdatePage.getIsJustifiedInput().click();
            expect(await teacherMissingSessionUpdatePage.getIsJustifiedInput().isSelected()).to.be.false;
        } else {
            await teacherMissingSessionUpdatePage.getIsJustifiedInput().click();
            expect(await teacherMissingSessionUpdatePage.getIsJustifiedInput().isSelected()).to.be.true;
        }
        await teacherMissingSessionUpdatePage.save();
        expect(await teacherMissingSessionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await teacherMissingSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TeacherMissingSession', async () => {
        const nbButtonsBeforeDelete = await teacherMissingSessionComponentsPage.countDeleteButtons();
        await teacherMissingSessionComponentsPage.clickOnLastDeleteButton();

        teacherMissingSessionDeleteDialog = new TeacherMissingSessionDeleteDialog();
        expect(await teacherMissingSessionDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.teacherMissingSession.delete.question');
        await teacherMissingSessionDeleteDialog.clickOnConfirmButton();

        expect(await teacherMissingSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
