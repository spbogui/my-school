import { element, by, ElementFinder } from 'protractor';

export class RoomComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-room div table .btn-danger'));
    title = element.all(by.css('jhi-room div h2#page-heading span')).first();

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

export class RoomUpdatePage {
    pageTitle = element(by.id('jhi-room-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    roomTypeSelect = element(by.id('field_roomType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
    }

    async roomTypeSelectLastOption() {
        await this.roomTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async roomTypeSelectOption(option) {
        await this.roomTypeSelect.sendKeys(option);
    }

    getRoomTypeSelect(): ElementFinder {
        return this.roomTypeSelect;
    }

    async getRoomTypeSelectedOption() {
        return this.roomTypeSelect.element(by.css('option:checked')).getText();
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

export class RoomDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-room-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-room'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
