import { element, by, ElementFinder } from 'protractor';

export class ImageComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-image div table .btn-danger'));
    title = element.all(by.css('jhi-image div h2#page-heading span')).first();

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

export class ImageUpdatePage {
    pageTitle = element(by.id('jhi-image-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    contentInput = element(by.id('file_content'));
    nameInput = element(by.id('field_name'));
    formatInput = element(by.id('field_format'));
    imageSizeInput = element(by.id('field_imageSize'));
    isUsedInput = element(by.id('field_isUsed'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setContentInput(content) {
        await this.contentInput.sendKeys(content);
    }

    async getContentInput() {
        return this.contentInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setFormatInput(format) {
        await this.formatInput.sendKeys(format);
    }

    async getFormatInput() {
        return this.formatInput.getAttribute('value');
    }

    async setImageSizeInput(imageSize) {
        await this.imageSizeInput.sendKeys(imageSize);
    }

    async getImageSizeInput() {
        return this.imageSizeInput.getAttribute('value');
    }

    getIsUsedInput() {
        return this.isUsedInput;
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

export class ImageDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-image-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-image'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
