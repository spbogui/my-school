/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchoolComponentsPage, SchoolDeleteDialog, SchoolUpdatePage } from './school.page-object';

const expect = chai.expect;

describe('School e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let schoolUpdatePage: SchoolUpdatePage;
    let schoolComponentsPage: SchoolComponentsPage;
    let schoolDeleteDialog: SchoolDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Schools', async () => {
        await navBarPage.goToEntity('school');
        schoolComponentsPage = new SchoolComponentsPage();
        await browser.wait(ec.visibilityOf(schoolComponentsPage.title), 5000);
        expect(await schoolComponentsPage.getTitle()).to.eq('mySchoolApp.school.home.title');
    });

    it('should load create School page', async () => {
        await schoolComponentsPage.clickOnCreateButton();
        schoolUpdatePage = new SchoolUpdatePage();
        expect(await schoolUpdatePage.getPageTitle()).to.eq('mySchoolApp.school.home.createOrEditLabel');
        await schoolUpdatePage.cancel();
    });

    it('should create and save Schools', async () => {
        const nbButtonsBeforeCreate = await schoolComponentsPage.countDeleteButtons();

        await schoolComponentsPage.clickOnCreateButton();
        await promise.all([
            schoolUpdatePage.setSchoolNameInput('schoolName'),
            schoolUpdatePage.setSloganInput('slogan'),
            schoolUpdatePage.setOpenningDateInput('2000-12-31'),
            schoolUpdatePage.setPhoneNumber1Input('phoneNumber1'),
            schoolUpdatePage.setPhoneNumber2Input('phoneNumber2'),
            schoolUpdatePage.setEmailInput('email'),
            schoolUpdatePage.setFaxInput('fax'),
            schoolUpdatePage.setWebSiteLinkInput('webSiteLink'),
            schoolUpdatePage.setAddressInput('address'),
            schoolUpdatePage.setTownInput('town'),
            schoolUpdatePage.setRegionInput('region'),
            schoolUpdatePage.setMunicipalityInput('municipality')
        ]);
        expect(await schoolUpdatePage.getSchoolNameInput()).to.eq('schoolName');
        expect(await schoolUpdatePage.getSloganInput()).to.eq('slogan');
        expect(await schoolUpdatePage.getOpenningDateInput()).to.eq('2000-12-31');
        expect(await schoolUpdatePage.getPhoneNumber1Input()).to.eq('phoneNumber1');
        expect(await schoolUpdatePage.getPhoneNumber2Input()).to.eq('phoneNumber2');
        expect(await schoolUpdatePage.getEmailInput()).to.eq('email');
        expect(await schoolUpdatePage.getFaxInput()).to.eq('fax');
        expect(await schoolUpdatePage.getWebSiteLinkInput()).to.eq('webSiteLink');
        expect(await schoolUpdatePage.getAddressInput()).to.eq('address');
        expect(await schoolUpdatePage.getTownInput()).to.eq('town');
        expect(await schoolUpdatePage.getRegionInput()).to.eq('region');
        expect(await schoolUpdatePage.getMunicipalityInput()).to.eq('municipality');
        await schoolUpdatePage.save();
        expect(await schoolUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await schoolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last School', async () => {
        const nbButtonsBeforeDelete = await schoolComponentsPage.countDeleteButtons();
        await schoolComponentsPage.clickOnLastDeleteButton();

        schoolDeleteDialog = new SchoolDeleteDialog();
        expect(await schoolDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.school.delete.question');
        await schoolDeleteDialog.clickOnConfirmButton();

        expect(await schoolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
