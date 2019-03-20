import { element, by, ElementFinder } from 'protractor';

export class ClassRoomComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-class-room div table .btn-danger'));
    title = element.all(by.css('jhi-class-room div h2#page-heading span')).first();

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

export class ClassRoomUpdatePage {
    pageTitle = element(by.id('jhi-class-room-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    levelSelect = element(by.id('field_level'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async levelSelectLastOption() {
        await this.levelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async levelSelectOption(option) {
        await this.levelSelect.sendKeys(option);
    }

    getLevelSelect(): ElementFinder {
        return this.levelSelect;
    }

    async getLevelSelectedOption() {
        return this.levelSelect.element(by.css('option:checked')).getText();
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

export class ClassRoomDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-classRoom-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-classRoom'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
