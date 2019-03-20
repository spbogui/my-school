/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudentDiplomaComponentsPage, StudentDiplomaDeleteDialog, StudentDiplomaUpdatePage } from './student-diploma.page-object';

const expect = chai.expect;

describe('StudentDiploma e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let studentDiplomaUpdatePage: StudentDiplomaUpdatePage;
    let studentDiplomaComponentsPage: StudentDiplomaComponentsPage;
    let studentDiplomaDeleteDialog: StudentDiplomaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StudentDiplomas', async () => {
        await navBarPage.goToEntity('student-diploma');
        studentDiplomaComponentsPage = new StudentDiplomaComponentsPage();
        await browser.wait(ec.visibilityOf(studentDiplomaComponentsPage.title), 5000);
        expect(await studentDiplomaComponentsPage.getTitle()).to.eq('mySchoolApp.studentDiploma.home.title');
    });

    it('should load create StudentDiploma page', async () => {
        await studentDiplomaComponentsPage.clickOnCreateButton();
        studentDiplomaUpdatePage = new StudentDiplomaUpdatePage();
        expect(await studentDiplomaUpdatePage.getPageTitle()).to.eq('mySchoolApp.studentDiploma.home.createOrEditLabel');
        await studentDiplomaUpdatePage.cancel();
    });

    it('should create and save StudentDiplomas', async () => {
        const nbButtonsBeforeCreate = await studentDiplomaComponentsPage.countDeleteButtons();

        await studentDiplomaComponentsPage.clickOnCreateButton();
        await promise.all([
            studentDiplomaUpdatePage.setMentionInput('mention'),
            studentDiplomaUpdatePage.setGraduationDateInput('2000-12-31'),
            studentDiplomaUpdatePage.studentSelectLastOption(),
            studentDiplomaUpdatePage.diplomaSelectLastOption(),
            studentDiplomaUpdatePage.schoolSchoolYearSelectLastOption()
        ]);
        expect(await studentDiplomaUpdatePage.getMentionInput()).to.eq('mention');
        expect(await studentDiplomaUpdatePage.getGraduationDateInput()).to.eq('2000-12-31');
        await studentDiplomaUpdatePage.save();
        expect(await studentDiplomaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await studentDiplomaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StudentDiploma', async () => {
        const nbButtonsBeforeDelete = await studentDiplomaComponentsPage.countDeleteButtons();
        await studentDiplomaComponentsPage.clickOnLastDeleteButton();

        studentDiplomaDeleteDialog = new StudentDiplomaDeleteDialog();
        expect(await studentDiplomaDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.studentDiploma.delete.question');
        await studentDiplomaDeleteDialog.clickOnConfirmButton();

        expect(await studentDiplomaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
