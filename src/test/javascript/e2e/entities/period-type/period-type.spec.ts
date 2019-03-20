/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PeriodTypeComponentsPage, PeriodTypeDeleteDialog, PeriodTypeUpdatePage } from './period-type.page-object';

const expect = chai.expect;

describe('PeriodType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let periodTypeUpdatePage: PeriodTypeUpdatePage;
    let periodTypeComponentsPage: PeriodTypeComponentsPage;
    let periodTypeDeleteDialog: PeriodTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PeriodTypes', async () => {
        await navBarPage.goToEntity('period-type');
        periodTypeComponentsPage = new PeriodTypeComponentsPage();
        await browser.wait(ec.visibilityOf(periodTypeComponentsPage.title), 5000);
        expect(await periodTypeComponentsPage.getTitle()).to.eq('mySchoolApp.periodType.home.title');
    });

    it('should load create PeriodType page', async () => {
        await periodTypeComponentsPage.clickOnCreateButton();
        periodTypeUpdatePage = new PeriodTypeUpdatePage();
        expect(await periodTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.periodType.home.createOrEditLabel');
        await periodTypeUpdatePage.cancel();
    });

    it('should create and save PeriodTypes', async () => {
        const nbButtonsBeforeCreate = await periodTypeComponentsPage.countDeleteButtons();

        await periodTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            periodTypeUpdatePage.setPeriodLabelInput('periodLabel'),
            periodTypeUpdatePage.setDescriptionInput('description')
        ]);
        expect(await periodTypeUpdatePage.getPeriodLabelInput()).to.eq('periodLabel');
        expect(await periodTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await periodTypeUpdatePage.save();
        expect(await periodTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await periodTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PeriodType', async () => {
        const nbButtonsBeforeDelete = await periodTypeComponentsPage.countDeleteButtons();
        await periodTypeComponentsPage.clickOnLastDeleteButton();

        periodTypeDeleteDialog = new PeriodTypeDeleteDialog();
        expect(await periodTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.periodType.delete.question');
        await periodTypeDeleteDialog.clickOnConfirmButton();

        expect(await periodTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
