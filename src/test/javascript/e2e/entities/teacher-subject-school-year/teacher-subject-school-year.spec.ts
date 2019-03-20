/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TeacherSubjectSchoolYearComponentsPage,
    TeacherSubjectSchoolYearDeleteDialog,
    TeacherSubjectSchoolYearUpdatePage
} from './teacher-subject-school-year.page-object';

const expect = chai.expect;

describe('TeacherSubjectSchoolYear e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let teacherSubjectSchoolYearUpdatePage: TeacherSubjectSchoolYearUpdatePage;
    let teacherSubjectSchoolYearComponentsPage: TeacherSubjectSchoolYearComponentsPage;
    let teacherSubjectSchoolYearDeleteDialog: TeacherSubjectSchoolYearDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TeacherSubjectSchoolYears', async () => {
        await navBarPage.goToEntity('teacher-subject-school-year');
        teacherSubjectSchoolYearComponentsPage = new TeacherSubjectSchoolYearComponentsPage();
        await browser.wait(ec.visibilityOf(teacherSubjectSchoolYearComponentsPage.title), 5000);
        expect(await teacherSubjectSchoolYearComponentsPage.getTitle()).to.eq('mySchoolApp.teacherSubjectSchoolYear.home.title');
    });

    it('should load create TeacherSubjectSchoolYear page', async () => {
        await teacherSubjectSchoolYearComponentsPage.clickOnCreateButton();
        teacherSubjectSchoolYearUpdatePage = new TeacherSubjectSchoolYearUpdatePage();
        expect(await teacherSubjectSchoolYearUpdatePage.getPageTitle()).to.eq(
            'mySchoolApp.teacherSubjectSchoolYear.home.createOrEditLabel'
        );
        await teacherSubjectSchoolYearUpdatePage.cancel();
    });

    it('should create and save TeacherSubjectSchoolYears', async () => {
        const nbButtonsBeforeCreate = await teacherSubjectSchoolYearComponentsPage.countDeleteButtons();

        await teacherSubjectSchoolYearComponentsPage.clickOnCreateButton();
        await promise.all([
            teacherSubjectSchoolYearUpdatePage.actorSelectLastOption(),
            teacherSubjectSchoolYearUpdatePage.teacherSelectLastOption(),
            teacherSubjectSchoolYearUpdatePage.schoolSchoolYearSelectLastOption()
        ]);
        const selectedIsPrincipal = teacherSubjectSchoolYearUpdatePage.getIsPrincipalInput();
        if (await selectedIsPrincipal.isSelected()) {
            await teacherSubjectSchoolYearUpdatePage.getIsPrincipalInput().click();
            expect(await teacherSubjectSchoolYearUpdatePage.getIsPrincipalInput().isSelected()).to.be.false;
        } else {
            await teacherSubjectSchoolYearUpdatePage.getIsPrincipalInput().click();
            expect(await teacherSubjectSchoolYearUpdatePage.getIsPrincipalInput().isSelected()).to.be.true;
        }
        await teacherSubjectSchoolYearUpdatePage.save();
        expect(await teacherSubjectSchoolYearUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await teacherSubjectSchoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TeacherSubjectSchoolYear', async () => {
        const nbButtonsBeforeDelete = await teacherSubjectSchoolYearComponentsPage.countDeleteButtons();
        await teacherSubjectSchoolYearComponentsPage.clickOnLastDeleteButton();

        teacherSubjectSchoolYearDeleteDialog = new TeacherSubjectSchoolYearDeleteDialog();
        expect(await teacherSubjectSchoolYearDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.teacherSubjectSchoolYear.delete.question');
        await teacherSubjectSchoolYearDeleteDialog.clickOnConfirmButton();

        expect(await teacherSubjectSchoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
