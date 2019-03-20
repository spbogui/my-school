import { element, by, ElementFinder } from 'protractor';

export class ActorIdentifierNumberComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-actor-identifier-number div table .btn-danger'));
    title = element.all(by.css('jhi-actor-identifier-number div h2#page-heading span')).first();

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

export class ActorIdentifierNumberUpdatePage {
    pageTitle = element(by.id('jhi-actor-identifier-number-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    identifierInput = element(by.id('field_identifier'));
    identifierTypeSelect = element(by.id('field_identifierType'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setIdentifierInput(identifier) {
        await this.identifierInput.sendKeys(identifier);
    }

    async getIdentifierInput() {
        return this.identifierInput.getAttribute('value');
    }

    async identifierTypeSelectLastOption() {
        await this.identifierTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async identifierTypeSelectOption(option) {
        await this.identifierTypeSelect.sendKeys(option);
    }

    getIdentifierTypeSelect(): ElementFinder {
        return this.identifierTypeSelect;
    }

    async getIdentifierTypeSelectedOption() {
        return this.identifierTypeSelect.element(by.css('option:checked')).getText();
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

export class ActorIdentifierNumberDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-actorIdentifierNumber-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-actorIdentifierNumber'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
