/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClassSessionComponentsPage, ClassSessionDeleteDialog, ClassSessionUpdatePage } from './class-session.page-object';

const expect = chai.expect;

describe('ClassSession e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let classSessionUpdatePage: ClassSessionUpdatePage;
    let classSessionComponentsPage: ClassSessionComponentsPage;
    let classSessionDeleteDialog: ClassSessionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ClassSessions', async () => {
        await navBarPage.goToEntity('class-session');
        classSessionComponentsPage = new ClassSessionComponentsPage();
        await browser.wait(ec.visibilityOf(classSessionComponentsPage.title), 5000);
        expect(await classSessionComponentsPage.getTitle()).to.eq('mySchoolApp.classSession.home.title');
    });

    it('should load create ClassSession page', async () => {
        await classSessionComponentsPage.clickOnCreateButton();
        classSessionUpdatePage = new ClassSessionUpdatePage();
        expect(await classSessionUpdatePage.getPageTitle()).to.eq('mySchoolApp.classSession.home.createOrEditLabel');
        await classSessionUpdatePage.cancel();
    });

    it('should create and save ClassSessions', async () => {
        const nbButtonsBeforeCreate = await classSessionComponentsPage.countDeleteButtons();

        await classSessionComponentsPage.clickOnCreateButton();
        await promise.all([
            classSessionUpdatePage.setStartHourInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            classSessionUpdatePage.setEndHourInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            classSessionUpdatePage.setDetailInput('detail'),
            classSessionUpdatePage.setCreatedAtInput('2000-12-31'),
            classSessionUpdatePage.classSessionTypeSelectLastOption(),
            classSessionUpdatePage.programSelectLastOption()
        ]);
        expect(await classSessionUpdatePage.getStartHourInput()).to.contain('2001-01-01T02:30');
        expect(await classSessionUpdatePage.getEndHourInput()).to.contain('2001-01-01T02:30');
        expect(await classSessionUpdatePage.getDetailInput()).to.eq('detail');
        expect(await classSessionUpdatePage.getCreatedAtInput()).to.eq('2000-12-31');
        await classSessionUpdatePage.save();
        expect(await classSessionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await classSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ClassSession', async () => {
        const nbButtonsBeforeDelete = await classSessionComponentsPage.countDeleteButtons();
        await classSessionComponentsPage.clickOnLastDeleteButton();

        classSessionDeleteDialog = new ClassSessionDeleteDialog();
        expect(await classSessionDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.classSession.delete.question');
        await classSessionDeleteDialog.clickOnConfirmButton();

        expect(await classSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
