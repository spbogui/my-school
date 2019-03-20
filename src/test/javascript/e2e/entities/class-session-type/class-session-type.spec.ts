/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClassSessionTypeComponentsPage, ClassSessionTypeDeleteDialog, ClassSessionTypeUpdatePage } from './class-session-type.page-object';

const expect = chai.expect;

describe('ClassSessionType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let classSessionTypeUpdatePage: ClassSessionTypeUpdatePage;
    let classSessionTypeComponentsPage: ClassSessionTypeComponentsPage;
    let classSessionTypeDeleteDialog: ClassSessionTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ClassSessionTypes', async () => {
        await navBarPage.goToEntity('class-session-type');
        classSessionTypeComponentsPage = new ClassSessionTypeComponentsPage();
        await browser.wait(ec.visibilityOf(classSessionTypeComponentsPage.title), 5000);
        expect(await classSessionTypeComponentsPage.getTitle()).to.eq('mySchoolApp.classSessionType.home.title');
    });

    it('should load create ClassSessionType page', async () => {
        await classSessionTypeComponentsPage.clickOnCreateButton();
        classSessionTypeUpdatePage = new ClassSessionTypeUpdatePage();
        expect(await classSessionTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.classSessionType.home.createOrEditLabel');
        await classSessionTypeUpdatePage.cancel();
    });

    it('should create and save ClassSessionTypes', async () => {
        const nbButtonsBeforeCreate = await classSessionTypeComponentsPage.countDeleteButtons();

        await classSessionTypeComponentsPage.clickOnCreateButton();
        await promise.all([classSessionTypeUpdatePage.setNameInput('name'), classSessionTypeUpdatePage.setDescriptionInput('description')]);
        expect(await classSessionTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await classSessionTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await classSessionTypeUpdatePage.save();
        expect(await classSessionTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await classSessionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ClassSessionType', async () => {
        const nbButtonsBeforeDelete = await classSessionTypeComponentsPage.countDeleteButtons();
        await classSessionTypeComponentsPage.clickOnLastDeleteButton();

        classSessionTypeDeleteDialog = new ClassSessionTypeDeleteDialog();
        expect(await classSessionTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.classSessionType.delete.question');
        await classSessionTypeDeleteDialog.clickOnConfirmButton();

        expect(await classSessionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
