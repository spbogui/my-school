import { element, by, ElementFinder } from 'protractor';

export class ActorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-actor div table .btn-danger'));
    title = element.all(by.css('jhi-actor div h2#page-heading span')).first();

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

export class ActorUpdatePage {
    pageTitle = element(by.id('jhi-actor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    birthDateInput = element(by.id('field_birthDate'));
    birthPlaceInput = element(by.id('field_birthPlace'));
    genderInput = element(by.id('field_gender'));
    countrySelect = element(by.id('field_country'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setBirthDateInput(birthDate) {
        await this.birthDateInput.sendKeys(birthDate);
    }

    async getBirthDateInput() {
        return this.birthDateInput.getAttribute('value');
    }

    async setBirthPlaceInput(birthPlace) {
        await this.birthPlaceInput.sendKeys(birthPlace);
    }

    async getBirthPlaceInput() {
        return this.birthPlaceInput.getAttribute('value');
    }

    async setGenderInput(gender) {
        await this.genderInput.sendKeys(gender);
    }

    async getGenderInput() {
        return this.genderInput.getAttribute('value');
    }

    async countrySelectLastOption() {
        await this.countrySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async countrySelectOption(option) {
        await this.countrySelect.sendKeys(option);
    }

    getCountrySelect(): ElementFinder {
        return this.countrySelect;
    }

    async getCountrySelectedOption() {
        return this.countrySelect.element(by.css('option:checked')).getText();
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

export class ActorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-actor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-actor'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
