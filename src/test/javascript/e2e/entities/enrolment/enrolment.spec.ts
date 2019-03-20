/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EnrolmentComponentsPage, EnrolmentDeleteDialog, EnrolmentUpdatePage } from './enrolment.page-object';

const expect = chai.expect;

describe('Enrolment e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let enrolmentUpdatePage: EnrolmentUpdatePage;
    let enrolmentComponentsPage: EnrolmentComponentsPage;
    let enrolmentDeleteDialog: EnrolmentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Enrolments', async () => {
        await navBarPage.goToEntity('enrolment');
        enrolmentComponentsPage = new EnrolmentComponentsPage();
        await browser.wait(ec.visibilityOf(enrolmentComponentsPage.title), 5000);
        expect(await enrolmentComponentsPage.getTitle()).to.eq('mySchoolApp.enrolment.home.title');
    });

    it('should load create Enrolment page', async () => {
        await enrolmentComponentsPage.clickOnCreateButton();
        enrolmentUpdatePage = new EnrolmentUpdatePage();
        expect(await enrolmentUpdatePage.getPageTitle()).to.eq('mySchoolApp.enrolment.home.createOrEditLabel');
        await enrolmentUpdatePage.cancel();
    });

    it('should create and save Enrolments', async () => {
        const nbButtonsBeforeCreate = await enrolmentComponentsPage.countDeleteButtons();

        await enrolmentComponentsPage.clickOnCreateButton();
        await promise.all([
            enrolmentUpdatePage.setEnrolmentDateInput('2000-12-31'),
            enrolmentUpdatePage.setRegimenRateInput('5'),
            enrolmentUpdatePage.setModernLanguage1Input('modernLanguage1'),
            enrolmentUpdatePage.setModernLanguage2Input('modernLanguage2'),
            enrolmentUpdatePage.schoolYearSelectLastOption(),
            enrolmentUpdatePage.actorSelectLastOption(),
            enrolmentUpdatePage.classRoomSelectLastOption(),
            enrolmentUpdatePage.regimenSelectLastOption(),
            enrolmentUpdatePage.previousSchoolSelectLastOption(),
            enrolmentUpdatePage.previousClassSelectLastOption()
        ]);
        expect(await enrolmentUpdatePage.getEnrolmentDateInput()).to.eq('2000-12-31');
        expect(await enrolmentUpdatePage.getRegimenRateInput()).to.eq('5');
        const selectedRepeating = enrolmentUpdatePage.getRepeatingInput();
        if (await selectedRepeating.isSelected()) {
            await enrolmentUpdatePage.getRepeatingInput().click();
            expect(await enrolmentUpdatePage.getRepeatingInput().isSelected()).to.be.false;
        } else {
            await enrolmentUpdatePage.getRepeatingInput().click();
            expect(await enrolmentUpdatePage.getRepeatingInput().isSelected()).to.be.true;
        }
        expect(await enrolmentUpdatePage.getModernLanguage1Input()).to.eq('modernLanguage1');
        expect(await enrolmentUpdatePage.getModernLanguage2Input()).to.eq('modernLanguage2');
        const selectedExemption = enrolmentUpdatePage.getExemptionInput();
        if (await selectedExemption.isSelected()) {
            await enrolmentUpdatePage.getExemptionInput().click();
            expect(await enrolmentUpdatePage.getExemptionInput().isSelected()).to.be.false;
        } else {
            await enrolmentUpdatePage.getExemptionInput().click();
            expect(await enrolmentUpdatePage.getExemptionInput().isSelected()).to.be.true;
        }
        const selectedWithInsurance = enrolmentUpdatePage.getWithInsuranceInput();
        if (await selectedWithInsurance.isSelected()) {
            await enrolmentUpdatePage.getWithInsuranceInput().click();
            expect(await enrolmentUpdatePage.getWithInsuranceInput().isSelected()).to.be.false;
        } else {
            await enrolmentUpdatePage.getWithInsuranceInput().click();
            expect(await enrolmentUpdatePage.getWithInsuranceInput().isSelected()).to.be.true;
        }
        await enrolmentUpdatePage.save();
        expect(await enrolmentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await enrolmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Enrolment', async () => {
        const nbButtonsBeforeDelete = await enrolmentComponentsPage.countDeleteButtons();
        await enrolmentComponentsPage.clickOnLastDeleteButton();

        enrolmentDeleteDialog = new EnrolmentDeleteDialog();
        expect(await enrolmentDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.enrolment.delete.question');
        await enrolmentDeleteDialog.clickOnConfirmButton();

        expect(await enrolmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
