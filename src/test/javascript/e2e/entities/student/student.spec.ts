/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudentComponentsPage, StudentDeleteDialog, StudentUpdatePage } from './student.page-object';

const expect = chai.expect;

describe('Student e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let studentUpdatePage: StudentUpdatePage;
    let studentComponentsPage: StudentComponentsPage;
    let studentDeleteDialog: StudentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Students', async () => {
        await navBarPage.goToEntity('student');
        studentComponentsPage = new StudentComponentsPage();
        await browser.wait(ec.visibilityOf(studentComponentsPage.title), 5000);
        expect(await studentComponentsPage.getTitle()).to.eq('mySchoolApp.student.home.title');
    });

    it('should load create Student page', async () => {
        await studentComponentsPage.clickOnCreateButton();
        studentUpdatePage = new StudentUpdatePage();
        expect(await studentUpdatePage.getPageTitle()).to.eq('mySchoolApp.student.home.createOrEditLabel');
        await studentUpdatePage.cancel();
    });

    it('should create and save Students', async () => {
        const nbButtonsBeforeCreate = await studentComponentsPage.countDeleteButtons();

        await studentComponentsPage.clickOnCreateButton();
        await promise.all([studentUpdatePage.actorSelectLastOption()]);
        const selectedPareantSeparated = studentUpdatePage.getPareantSeparatedInput();
        if (await selectedPareantSeparated.isSelected()) {
            await studentUpdatePage.getPareantSeparatedInput().click();
            expect(await studentUpdatePage.getPareantSeparatedInput().isSelected()).to.be.false;
        } else {
            await studentUpdatePage.getPareantSeparatedInput().click();
            expect(await studentUpdatePage.getPareantSeparatedInput().isSelected()).to.be.true;
        }
        const selectedFatherIsAlive = studentUpdatePage.getFatherIsAliveInput();
        if (await selectedFatherIsAlive.isSelected()) {
            await studentUpdatePage.getFatherIsAliveInput().click();
            expect(await studentUpdatePage.getFatherIsAliveInput().isSelected()).to.be.false;
        } else {
            await studentUpdatePage.getFatherIsAliveInput().click();
            expect(await studentUpdatePage.getFatherIsAliveInput().isSelected()).to.be.true;
        }
        const selectedLivingWithFather = studentUpdatePage.getLivingWithFatherInput();
        if (await selectedLivingWithFather.isSelected()) {
            await studentUpdatePage.getLivingWithFatherInput().click();
            expect(await studentUpdatePage.getLivingWithFatherInput().isSelected()).to.be.false;
        } else {
            await studentUpdatePage.getLivingWithFatherInput().click();
            expect(await studentUpdatePage.getLivingWithFatherInput().isSelected()).to.be.true;
        }
        const selectedMotherIsAlive = studentUpdatePage.getMotherIsAliveInput();
        if (await selectedMotherIsAlive.isSelected()) {
            await studentUpdatePage.getMotherIsAliveInput().click();
            expect(await studentUpdatePage.getMotherIsAliveInput().isSelected()).to.be.false;
        } else {
            await studentUpdatePage.getMotherIsAliveInput().click();
            expect(await studentUpdatePage.getMotherIsAliveInput().isSelected()).to.be.true;
        }
        const selectedLivingWithMother = studentUpdatePage.getLivingWithMotherInput();
        if (await selectedLivingWithMother.isSelected()) {
            await studentUpdatePage.getLivingWithMotherInput().click();
            expect(await studentUpdatePage.getLivingWithMotherInput().isSelected()).to.be.false;
        } else {
            await studentUpdatePage.getLivingWithMotherInput().click();
            expect(await studentUpdatePage.getLivingWithMotherInput().isSelected()).to.be.true;
        }
        await studentUpdatePage.save();
        expect(await studentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Student', async () => {
        const nbButtonsBeforeDelete = await studentComponentsPage.countDeleteButtons();
        await studentComponentsPage.clickOnLastDeleteButton();

        studentDeleteDialog = new StudentDeleteDialog();
        expect(await studentDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.student.delete.question');
        await studentDeleteDialog.clickOnConfirmButton();

        expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
