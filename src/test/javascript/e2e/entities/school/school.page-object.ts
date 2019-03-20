import { element, by, ElementFinder } from 'protractor';

export class SchoolComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-school div table .btn-danger'));
    title = element.all(by.css('jhi-school div h2#page-heading span')).first();

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

export class SchoolUpdatePage {
    pageTitle = element(by.id('jhi-school-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    schoolNameInput = element(by.id('field_schoolName'));
    sloganInput = element(by.id('field_slogan'));
    openningDateInput = element(by.id('field_openningDate'));
    phoneNumber1Input = element(by.id('field_phoneNumber1'));
    phoneNumber2Input = element(by.id('field_phoneNumber2'));
    emailInput = element(by.id('field_email'));
    faxInput = element(by.id('field_fax'));
    webSiteLinkInput = element(by.id('field_webSiteLink'));
    addressInput = element(by.id('field_address'));
    townInput = element(by.id('field_town'));
    regionInput = element(by.id('field_region'));
    municipalityInput = element(by.id('field_municipality'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSchoolNameInput(schoolName) {
        await this.schoolNameInput.sendKeys(schoolName);
    }

    async getSchoolNameInput() {
        return this.schoolNameInput.getAttribute('value');
    }

    async setSloganInput(slogan) {
        await this.sloganInput.sendKeys(slogan);
    }

    async getSloganInput() {
        return this.sloganInput.getAttribute('value');
    }

    async setOpenningDateInput(openningDate) {
        await this.openningDateInput.sendKeys(openningDate);
    }

    async getOpenningDateInput() {
        return this.openningDateInput.getAttribute('value');
    }

    async setPhoneNumber1Input(phoneNumber1) {
        await this.phoneNumber1Input.sendKeys(phoneNumber1);
    }

    async getPhoneNumber1Input() {
        return this.phoneNumber1Input.getAttribute('value');
    }

    async setPhoneNumber2Input(phoneNumber2) {
        await this.phoneNumber2Input.sendKeys(phoneNumber2);
    }

    async getPhoneNumber2Input() {
        return this.phoneNumber2Input.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setFaxInput(fax) {
        await this.faxInput.sendKeys(fax);
    }

    async getFaxInput() {
        return this.faxInput.getAttribute('value');
    }

    async setWebSiteLinkInput(webSiteLink) {
        await this.webSiteLinkInput.sendKeys(webSiteLink);
    }

    async getWebSiteLinkInput() {
        return this.webSiteLinkInput.getAttribute('value');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
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

    async setMunicipalityInput(municipality) {
        await this.municipalityInput.sendKeys(municipality);
    }

    async getMunicipalityInput() {
        return this.municipalityInput.getAttribute('value');
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

export class SchoolDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-school-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-school'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
