/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SubjectComponentsPage, SubjectDeleteDialog, SubjectUpdatePage } from './subject.page-object';

const expect = chai.expect;

describe('Subject e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let subjectUpdatePage: SubjectUpdatePage;
    let subjectComponentsPage: SubjectComponentsPage;
    let subjectDeleteDialog: SubjectDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Subjects', async () => {
        await navBarPage.goToEntity('subject');
        subjectComponentsPage = new SubjectComponentsPage();
        await browser.wait(ec.visibilityOf(subjectComponentsPage.title), 5000);
        expect(await subjectComponentsPage.getTitle()).to.eq('mySchoolApp.subject.home.title');
    });

    it('should load create Subject page', async () => {
        await subjectComponentsPage.clickOnCreateButton();
        subjectUpdatePage = new SubjectUpdatePage();
        expect(await subjectUpdatePage.getPageTitle()).to.eq('mySchoolApp.subject.home.createOrEditLabel');
        await subjectUpdatePage.cancel();
    });

    it('should create and save Subjects', async () => {
        const nbButtonsBeforeCreate = await subjectComponentsPage.countDeleteButtons();

        await subjectComponentsPage.clickOnCreateButton();
        await promise.all([subjectUpdatePage.setLabelInput('label'), subjectUpdatePage.setDescriptionInput('description')]);
        expect(await subjectUpdatePage.getLabelInput()).to.eq('label');
        expect(await subjectUpdatePage.getDescriptionInput()).to.eq('description');
        await subjectUpdatePage.save();
        expect(await subjectUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await subjectComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Subject', async () => {
        const nbButtonsBeforeDelete = await subjectComponentsPage.countDeleteButtons();
        await subjectComponentsPage.clickOnLastDeleteButton();

        subjectDeleteDialog = new SubjectDeleteDialog();
        expect(await subjectDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.subject.delete.question');
        await subjectDeleteDialog.clickOnConfirmButton();

        expect(await subjectComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
