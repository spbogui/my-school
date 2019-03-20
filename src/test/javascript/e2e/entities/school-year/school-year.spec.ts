/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchoolYearComponentsPage, SchoolYearDeleteDialog, SchoolYearUpdatePage } from './school-year.page-object';

const expect = chai.expect;

describe('SchoolYear e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let schoolYearUpdatePage: SchoolYearUpdatePage;
    let schoolYearComponentsPage: SchoolYearComponentsPage;
    let schoolYearDeleteDialog: SchoolYearDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SchoolYears', async () => {
        await navBarPage.goToEntity('school-year');
        schoolYearComponentsPage = new SchoolYearComponentsPage();
        await browser.wait(ec.visibilityOf(schoolYearComponentsPage.title), 5000);
        expect(await schoolYearComponentsPage.getTitle()).to.eq('mySchoolApp.schoolYear.home.title');
    });

    it('should load create SchoolYear page', async () => {
        await schoolYearComponentsPage.clickOnCreateButton();
        schoolYearUpdatePage = new SchoolYearUpdatePage();
        expect(await schoolYearUpdatePage.getPageTitle()).to.eq('mySchoolApp.schoolYear.home.createOrEditLabel');
        await schoolYearUpdatePage.cancel();
    });

    it('should create and save SchoolYears', async () => {
        const nbButtonsBeforeCreate = await schoolYearComponentsPage.countDeleteButtons();

        await schoolYearComponentsPage.clickOnCreateButton();
        await promise.all([
            schoolYearUpdatePage.setSchoolYearlabelInput('schoolYearlabel'),
            schoolYearUpdatePage.setCourseStartDateInput('2000-12-31'),
            schoolYearUpdatePage.setCourseEndDateInput('2000-12-31'),
            schoolYearUpdatePage.setStartDateInput('2000-12-31'),
            schoolYearUpdatePage.setEndDateInput('2000-12-31')
        ]);
        expect(await schoolYearUpdatePage.getSchoolYearlabelInput()).to.eq('schoolYearlabel');
        expect(await schoolYearUpdatePage.getCourseStartDateInput()).to.eq('2000-12-31');
        expect(await schoolYearUpdatePage.getCourseEndDateInput()).to.eq('2000-12-31');
        expect(await schoolYearUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await schoolYearUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        const selectedIsBlankYear = schoolYearUpdatePage.getIsBlankYearInput();
        if (await selectedIsBlankYear.isSelected()) {
            await schoolYearUpdatePage.getIsBlankYearInput().click();
            expect(await schoolYearUpdatePage.getIsBlankYearInput().isSelected()).to.be.false;
        } else {
            await schoolYearUpdatePage.getIsBlankYearInput().click();
            expect(await schoolYearUpdatePage.getIsBlankYearInput().isSelected()).to.be.true;
        }
        await schoolYearUpdatePage.save();
        expect(await schoolYearUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await schoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SchoolYear', async () => {
        const nbButtonsBeforeDelete = await schoolYearComponentsPage.countDeleteButtons();
        await schoolYearComponentsPage.clickOnLastDeleteButton();

        schoolYearDeleteDialog = new SchoolYearDeleteDialog();
        expect(await schoolYearDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.schoolYear.delete.question');
        await schoolYearDeleteDialog.clickOnConfirmButton();

        expect(await schoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
