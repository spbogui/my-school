/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DaysComponentsPage, DaysDeleteDialog, DaysUpdatePage } from './days.page-object';

const expect = chai.expect;

describe('Days e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let daysUpdatePage: DaysUpdatePage;
    let daysComponentsPage: DaysComponentsPage;
    let daysDeleteDialog: DaysDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Days', async () => {
        await navBarPage.goToEntity('days');
        daysComponentsPage = new DaysComponentsPage();
        await browser.wait(ec.visibilityOf(daysComponentsPage.title), 5000);
        expect(await daysComponentsPage.getTitle()).to.eq('mySchoolApp.days.home.title');
    });

    it('should load create Days page', async () => {
        await daysComponentsPage.clickOnCreateButton();
        daysUpdatePage = new DaysUpdatePage();
        expect(await daysUpdatePage.getPageTitle()).to.eq('mySchoolApp.days.home.createOrEditLabel');
        await daysUpdatePage.cancel();
    });

    it('should create and save Days', async () => {
        const nbButtonsBeforeCreate = await daysComponentsPage.countDeleteButtons();

        await daysComponentsPage.clickOnCreateButton();
        await promise.all([daysUpdatePage.setNameInput('name')]);
        expect(await daysUpdatePage.getNameInput()).to.eq('name');
        await daysUpdatePage.save();
        expect(await daysUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await daysComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Days', async () => {
        const nbButtonsBeforeDelete = await daysComponentsPage.countDeleteButtons();
        await daysComponentsPage.clickOnLastDeleteButton();

        daysDeleteDialog = new DaysDeleteDialog();
        expect(await daysDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.days.delete.question');
        await daysDeleteDialog.clickOnConfirmButton();

        expect(await daysComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
