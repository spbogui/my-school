/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProgramComponentsPage, ProgramDeleteDialog, ProgramUpdatePage } from './program.page-object';

const expect = chai.expect;

describe('Program e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let programUpdatePage: ProgramUpdatePage;
    let programComponentsPage: ProgramComponentsPage;
    let programDeleteDialog: ProgramDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Programs', async () => {
        await navBarPage.goToEntity('program');
        programComponentsPage = new ProgramComponentsPage();
        await browser.wait(ec.visibilityOf(programComponentsPage.title), 5000);
        expect(await programComponentsPage.getTitle()).to.eq('mySchoolApp.program.home.title');
    });

    it('should load create Program page', async () => {
        await programComponentsPage.clickOnCreateButton();
        programUpdatePage = new ProgramUpdatePage();
        expect(await programUpdatePage.getPageTitle()).to.eq('mySchoolApp.program.home.createOrEditLabel');
        await programUpdatePage.cancel();
    });

    it('should create and save Programs', async () => {
        const nbButtonsBeforeCreate = await programComponentsPage.countDeleteButtons();

        await programComponentsPage.clickOnCreateButton();
        await promise.all([
            programUpdatePage.setStartHourInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            programUpdatePage.setEndHourInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            programUpdatePage.subjectSelectLastOption(),
            programUpdatePage.classRoomSelectLastOption(),
            programUpdatePage.roomSelectLastOption(),
            programUpdatePage.daysSelectLastOption(),
            programUpdatePage.schoolYearSelectLastOption()
        ]);
        expect(await programUpdatePage.getStartHourInput()).to.contain('2001-01-01T02:30');
        expect(await programUpdatePage.getEndHourInput()).to.contain('2001-01-01T02:30');
        await programUpdatePage.save();
        expect(await programUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await programComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Program', async () => {
        const nbButtonsBeforeDelete = await programComponentsPage.countDeleteButtons();
        await programComponentsPage.clickOnLastDeleteButton();

        programDeleteDialog = new ProgramDeleteDialog();
        expect(await programDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.program.delete.question');
        await programDeleteDialog.clickOnConfirmButton();

        expect(await programComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
