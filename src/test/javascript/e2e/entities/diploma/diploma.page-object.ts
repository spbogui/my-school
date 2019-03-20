import { element, by, ElementFinder } from 'protractor';

export class DiplomaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-diploma div table .btn-danger'));
    title = element.all(by.css('jhi-diploma div h2#page-heading span')).first();

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

export class DiplomaUpdatePage {
    pageTitle = element(by.id('jhi-diploma-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    diplomaLabelInput = element(by.id('field_diplomaLabel'));
    descriptionInput = element(by.id('field_description'));
    cycleSelect = element(by.id('field_cycle'));
    parentDiplomaSelect = element(by.id('field_parentDiploma'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDiplomaLabelInput(diplomaLabel) {
        await this.diplomaLabelInput.sendKeys(diplomaLabel);
    }

    async getDiplomaLabelInput() {
        return this.diplomaLabelInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async cycleSelectLastOption() {
        await this.cycleSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cycleSelectOption(option) {
        await this.cycleSelect.sendKeys(option);
    }

    getCycleSelect(): ElementFinder {
        return this.cycleSelect;
    }

    async getCycleSelectedOption() {
        return this.cycleSelect.element(by.css('option:checked')).getText();
    }

    async parentDiplomaSelectLastOption() {
        await this.parentDiplomaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async parentDiplomaSelectOption(option) {
        await this.parentDiplomaSelect.sendKeys(option);
    }

    getParentDiplomaSelect(): ElementFinder {
        return this.parentDiplomaSelect;
    }

    async getParentDiplomaSelectedOption() {
        return this.parentDiplomaSelect.element(by.css('option:checked')).getText();
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

export class DiplomaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-diploma-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-diploma'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
