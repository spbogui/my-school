import { element, by, ElementFinder } from 'protractor';

export class ActorNameComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-actor-name div table .btn-danger'));
    title = element.all(by.css('jhi-actor-name div h2#page-heading span')).first();

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

export class ActorNameUpdatePage {
    pageTitle = element(by.id('jhi-actor-name-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    civilityInput = element(by.id('field_civility'));
    familyNameInput = element(by.id('field_familyName'));
    givenName1Input = element(by.id('field_givenName1'));
    givenName2Input = element(by.id('field_givenName2'));
    givenName3Input = element(by.id('field_givenName3'));
    maidenNameInput = element(by.id('field_maidenName'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCivilityInput(civility) {
        await this.civilityInput.sendKeys(civility);
    }

    async getCivilityInput() {
        return this.civilityInput.getAttribute('value');
    }

    async setFamilyNameInput(familyName) {
        await this.familyNameInput.sendKeys(familyName);
    }

    async getFamilyNameInput() {
        return this.familyNameInput.getAttribute('value');
    }

    async setGivenName1Input(givenName1) {
        await this.givenName1Input.sendKeys(givenName1);
    }

    async getGivenName1Input() {
        return this.givenName1Input.getAttribute('value');
    }

    async setGivenName2Input(givenName2) {
        await this.givenName2Input.sendKeys(givenName2);
    }

    async getGivenName2Input() {
        return this.givenName2Input.getAttribute('value');
    }

    async setGivenName3Input(givenName3) {
        await this.givenName3Input.sendKeys(givenName3);
    }

    async getGivenName3Input() {
        return this.givenName3Input.getAttribute('value');
    }

    async setMaidenNameInput(maidenName) {
        await this.maidenNameInput.sendKeys(maidenName);
    }

    async getMaidenNameInput() {
        return this.maidenNameInput.getAttribute('value');
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

export class ActorNameDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-actorName-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-actorName'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
