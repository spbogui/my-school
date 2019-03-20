/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressComponentsPage, AddressDeleteDialog, AddressUpdatePage } from './address.page-object';

const expect = chai.expect;

describe('Address e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let addressUpdatePage: AddressUpdatePage;
    let addressComponentsPage: AddressComponentsPage;
    let addressDeleteDialog: AddressDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Addresses', async () => {
        await navBarPage.goToEntity('address');
        addressComponentsPage = new AddressComponentsPage();
        await browser.wait(ec.visibilityOf(addressComponentsPage.title), 5000);
        expect(await addressComponentsPage.getTitle()).to.eq('mySchoolApp.address.home.title');
    });

    it('should load create Address page', async () => {
        await addressComponentsPage.clickOnCreateButton();
        addressUpdatePage = new AddressUpdatePage();
        expect(await addressUpdatePage.getPageTitle()).to.eq('mySchoolApp.address.home.createOrEditLabel');
        await addressUpdatePage.cancel();
    });

    it('should create and save Addresses', async () => {
        const nbButtonsBeforeCreate = await addressComponentsPage.countDeleteButtons();

        await addressComponentsPage.clickOnCreateButton();
        await promise.all([
            addressUpdatePage.setMobilePhoneInput('mobilePhone'),
            addressUpdatePage.setOfficePhoneInput('officePhone'),
            addressUpdatePage.setHomePhoneInput('homePhone'),
            addressUpdatePage.setEmailInput('email'),
            addressUpdatePage.setPostalAddressInput('postalAddress'),
            addressUpdatePage.setTownInput('town'),
            addressUpdatePage.setRegionInput('region'),
            addressUpdatePage.setVillageInput('village'),
            addressUpdatePage.actorSelectLastOption()
        ]);
        expect(await addressUpdatePage.getMobilePhoneInput()).to.eq('mobilePhone');
        expect(await addressUpdatePage.getOfficePhoneInput()).to.eq('officePhone');
        expect(await addressUpdatePage.getHomePhoneInput()).to.eq('homePhone');
        expect(await addressUpdatePage.getEmailInput()).to.eq('email');
        expect(await addressUpdatePage.getPostalAddressInput()).to.eq('postalAddress');
        expect(await addressUpdatePage.getTownInput()).to.eq('town');
        expect(await addressUpdatePage.getRegionInput()).to.eq('region');
        expect(await addressUpdatePage.getVillageInput()).to.eq('village');
        const selectedIsPreferred = addressUpdatePage.getIsPreferredInput();
        if (await selectedIsPreferred.isSelected()) {
            await addressUpdatePage.getIsPreferredInput().click();
            expect(await addressUpdatePage.getIsPreferredInput().isSelected()).to.be.false;
        } else {
            await addressUpdatePage.getIsPreferredInput().click();
            expect(await addressUpdatePage.getIsPreferredInput().isSelected()).to.be.true;
        }
        await addressUpdatePage.save();
        expect(await addressUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Address', async () => {
        const nbButtonsBeforeDelete = await addressComponentsPage.countDeleteButtons();
        await addressComponentsPage.clickOnLastDeleteButton();

        addressDeleteDialog = new AddressDeleteDialog();
        expect(await addressDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.address.delete.question');
        await addressDeleteDialog.clickOnConfirmButton();

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
