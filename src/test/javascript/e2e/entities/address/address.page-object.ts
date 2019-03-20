import { element, by, ElementFinder } from 'protractor';

export class AddressComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-address div table .btn-danger'));
    title = element.all(by.css('jhi-address div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AddressUpdatePage {
    pageTitle = element(by.id('jhi-address-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    mobilePhoneInput = element(by.id('field_mobilePhone'));
    officePhoneInput = element(by.id('field_officePhone'));
    homePhoneInput = element(by.id('field_homePhone'));
    emailInput = element(by.id('field_email'));
    postalAddressInput = element(by.id('field_postalAddress'));
    townInput = element(by.id('field_town'));
    regionInput = element(by.id('field_region'));
    villageInput = element(by.id('field_village'));
    isPreferredInput = element(by.id('field_isPreferred'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMobilePhoneInput(mobilePhone) {
        await this.mobilePhoneInput.sendKeys(mobilePhone);
    }

    async getMobilePhoneInput() {
        return this.mobilePhoneInput.getAttribute('value');
    }

    async setOfficePhoneInput(officePhone) {
        await this.officePhoneInput.sendKeys(officePhone);
    }

    async getOfficePhoneInput() {
        return this.officePhoneInput.getAttribute('value');
    }

    async setHomePhoneInput(homePhone) {
        await this.homePhoneInput.sendKeys(homePhone);
    }

    async getHomePhoneInput() {
        return this.homePhoneInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setPostalAddressInput(postalAddress) {
        await this.postalAddressInput.sendKeys(postalAddress);
    }

    async getPostalAddressInput() {
        return this.postalAddressInput.getAttribute('value');
    }

    async setTownInput(town) {
        await this.townInput.sendKeys(town);
    }

    async getTownInput() {
        return this.townInput.getAttribute('value');
    }

    async setRegionInput(region) {
        await this.regionInput.sendKeys(region);
    }

    async getRegionInput() {
        return this.regionInput.getAttribute('value');
    }

    async setVillageInput(village) {
        await this.villageInput.sendKeys(village);
    }

    async getVillageInput() {
        return this.villageInput.getAttribute('value');
    }

    getIsPreferredInput() {
        return this.isPreferredInput;
    }

    async actorSelectLastOption() {
        await this.actorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async actorSelectOption(option) {
        await this.actorSelect.sendKeys(option);
    }

    getActorSelect(): ElementFinder {
        return this.actorSelect;
    }

    async getActorSelectedOption() {
        return this.actorSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class AddressDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-address-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-address'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
