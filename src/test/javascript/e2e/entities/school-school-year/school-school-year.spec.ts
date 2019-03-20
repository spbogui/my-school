/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchoolSchoolYearComponentsPage, SchoolSchoolYearDeleteDialog, SchoolSchoolYearUpdatePage } from './school-school-year.page-object';

const expect = chai.expect;

describe('SchoolSchoolYear e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let schoolSchoolYearUpdatePage: SchoolSchoolYearUpdatePage;
    let schoolSchoolYearComponentsPage: SchoolSchoolYearComponentsPage;
    let schoolSchoolYearDeleteDialog: SchoolSchoolYearDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SchoolSchoolYears', async () => {
        await navBarPage.goToEntity('school-school-year');
        schoolSchoolYearComponentsPage = new SchoolSchoolYearComponentsPage();
        await browser.wait(ec.visibilityOf(schoolSchoolYearComponentsPage.title), 5000);
        expect(await schoolSchoolYearComponentsPage.getTitle()).to.eq('mySchoolApp.schoolSchoolYear.home.title');
    });

    it('should load create SchoolSchoolYear page', async () => {
        await schoolSchoolYearComponentsPage.clickOnCreateButton();
        schoolSchoolYearUpdatePage = new SchoolSchoolYearUpdatePage();
        expect(await schoolSchoolYearUpdatePage.getPageTitle()).to.eq('mySchoolApp.schoolSchoolYear.home.createOrEditLabel');
        await schoolSchoolYearUpdatePage.cancel();
    });

    it('should create and save SchoolSchoolYears', async () => {
        const nbButtonsBeforeCreate = await schoolSchoolYearComponentsPage.countDeleteButtons();

        await schoolSchoolYearComponentsPage.clickOnCreateButton();
        await promise.all([
            schoolSchoolYearUpdatePage.setDescriptionInput('description'),
            schoolSchoolYearUpdatePage.schoolNameSelectLastOption(),
            schoolSchoolYearUpdatePage.schoolYearlabelSelectLastOption()
        ]);
        expect(await schoolSchoolYearUpdatePage.getDescriptionInput()).to.eq('description');
        await schoolSchoolYearUpdatePage.save();
        expect(await schoolSchoolYearUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await schoolSchoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SchoolSchoolYear', async () => {
        const nbButtonsBeforeDelete = await schoolSchoolYearComponentsPage.countDeleteButtons();
        await schoolSchoolYearComponentsPage.clickOnLastDeleteButton();

        schoolSchoolYearDeleteDialog = new SchoolSchoolYearDeleteDialog();
        expect(await schoolSchoolYearDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.schoolSchoolYear.delete.question');
        await schoolSchoolYearDeleteDialog.clickOnConfirmButton();

        expect(await schoolSchoolYearComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
