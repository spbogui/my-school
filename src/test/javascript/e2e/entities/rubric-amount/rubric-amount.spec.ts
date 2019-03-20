/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RubricAmountComponentsPage, RubricAmountDeleteDialog, RubricAmountUpdatePage } from './rubric-amount.page-object';

const expect = chai.expect;

describe('RubricAmount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rubricAmountUpdatePage: RubricAmountUpdatePage;
    let rubricAmountComponentsPage: RubricAmountComponentsPage;
    let rubricAmountDeleteDialog: RubricAmountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RubricAmounts', async () => {
        await navBarPage.goToEntity('rubric-amount');
        rubricAmountComponentsPage = new RubricAmountComponentsPage();
        await browser.wait(ec.visibilityOf(rubricAmountComponentsPage.title), 5000);
        expect(await rubricAmountComponentsPage.getTitle()).to.eq('mySchoolApp.rubricAmount.home.title');
    });

    it('should load create RubricAmount page', async () => {
        await rubricAmountComponentsPage.clickOnCreateButton();
        rubricAmountUpdatePage = new RubricAmountUpdatePage();
        expect(await rubricAmountUpdatePage.getPageTitle()).to.eq('mySchoolApp.rubricAmount.home.createOrEditLabel');
        await rubricAmountUpdatePage.cancel();
    });

    it('should create and save RubricAmounts', async () => {
        const nbButtonsBeforeCreate = await rubricAmountComponentsPage.countDeleteButtons();

        await rubricAmountComponentsPage.clickOnCreateButton();
        await promise.all([
            rubricAmountUpdatePage.setAmountInput('5'),
            rubricAmountUpdatePage.setPaymentMethodInput('paymentMethod'),
            rubricAmountUpdatePage.rubricSelectLastOption(),
            rubricAmountUpdatePage.levelSelectLastOption(),
            rubricAmountUpdatePage.schoolYearSelectLastOption()
        ]);
        expect(await rubricAmountUpdatePage.getAmountInput()).to.eq('5');
        expect(await rubricAmountUpdatePage.getPaymentMethodInput()).to.eq('paymentMethod');
        await rubricAmountUpdatePage.save();
        expect(await rubricAmountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rubricAmountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RubricAmount', async () => {
        const nbButtonsBeforeDelete = await rubricAmountComponentsPage.countDeleteButtons();
        await rubricAmountComponentsPage.clickOnLastDeleteButton();

        rubricAmountDeleteDialog = new RubricAmountDeleteDialog();
        expect(await rubricAmountDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.rubricAmount.delete.question');
        await rubricAmountDeleteDialog.clickOnConfirmButton();

        expect(await rubricAmountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
