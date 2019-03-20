import { element, by, ElementFinder } from 'protractor';

export class LevelComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-level div table .btn-danger'));
    title = element.all(by.css('jhi-level div h2#page-heading span')).first();

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

export class LevelUpdatePage {
    pageTitle = element(by.id('jhi-level-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    shortFormInput = element(by.id('field_shortForm'));
    parentLevelSelect = element(by.id('field_parentLevel'));
    cycleSelect = element(by.id('field_cycle'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async setShortFormInput(shortForm) {
        await this.shortFormInput.sendKeys(shortForm);
    }

    async getShortFormInput() {
        return this.shortFormInput.getAttribute('value');
    }

    async parentLevelSelectLastOption() {
        await this.parentLevelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async parentLevelSelectOption(option) {
        await this.parentLevelSelect.sendKeys(option);
    }

    getParentLevelSelect(): ElementFinder {
        return this.parentLevelSelect;
    }

    async getParentLevelSelectedOption() {
        return this.parentLevelSelect.element(by.css('option:checked')).getText();
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

export class LevelDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-level-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-level'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
