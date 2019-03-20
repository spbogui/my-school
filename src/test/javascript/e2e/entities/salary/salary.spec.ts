/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SalaryComponentsPage, SalaryDeleteDialog, SalaryUpdatePage } from './salary.page-object';

const expect = chai.expect;

describe('Salary e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let salaryUpdatePage: SalaryUpdatePage;
    let salaryComponentsPage: SalaryComponentsPage;
    let salaryDeleteDialog: SalaryDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Salaries', async () => {
        await navBarPage.goToEntity('salary');
        salaryComponentsPage = new SalaryComponentsPage();
        await browser.wait(ec.visibilityOf(salaryComponentsPage.title), 5000);
        expect(await salaryComponentsPage.getTitle()).to.eq('mySchoolApp.salary.home.title');
    });

    it('should load create Salary page', async () => {
        await salaryComponentsPage.clickOnCreateButton();
        salaryUpdatePage = new SalaryUpdatePage();
        expect(await salaryUpdatePage.getPageTitle()).to.eq('mySchoolApp.salary.home.createOrEditLabel');
        await salaryUpdatePage.cancel();
    });

    it('should create and save Salaries', async () => {
        const nbButtonsBeforeCreate = await salaryComponentsPage.countDeleteButtons();

        await salaryComponentsPage.clickOnCreateButton();
        await promise.all([
            salaryUpdatePage.setAmountInput('5'),
            salaryUpdatePage.setPaymentDateInput('2000-12-31'),
            salaryUpdatePage.setCreatedAtInput('2000-12-31'),
            salaryUpdatePage.setMemoInput('memo'),
            salaryUpdatePage.employeeSelectLastOption()
        ]);
        expect(await salaryUpdatePage.getAmountInput()).to.eq('5');
        expect(await salaryUpdatePage.getPaymentDateInput()).to.eq('2000-12-31');
        expect(await salaryUpdatePage.getCreatedAtInput()).to.eq('2000-12-31');
        expect(await salaryUpdatePage.getMemoInput()).to.eq('memo');
        await salaryUpdatePage.save();
        expect(await salaryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await salaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Salary', async () => {
        const nbButtonsBeforeDelete = await salaryComponentsPage.countDeleteButtons();
        await salaryComponentsPage.clickOnLastDeleteButton();

        salaryDeleteDialog = new SalaryDeleteDialog();
        expect(await salaryDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.salary.delete.question');
        await salaryDeleteDialog.clickOnConfirmButton();

        expect(await salaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
