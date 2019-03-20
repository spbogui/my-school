import { element, by, ElementFinder } from 'protractor';

export class ClassSessionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-class-session div table .btn-danger'));
    title = element.all(by.css('jhi-class-session div h2#page-heading span')).first();

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

export class ClassSessionUpdatePage {
    pageTitle = element(by.id('jhi-class-session-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    startHourInput = element(by.id('field_startHour'));
    endHourInput = element(by.id('field_endHour'));
    detailInput = element(by.id('field_detail'));
    createdAtInput = element(by.id('field_createdAt'));
    classSessionTypeSelect = element(by.id('field_classSessionType'));
    programSelect = element(by.id('field_program'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStartHourInput(startHour) {
        await this.startHourInput.sendKeys(startHour);
    }

    async getStartHourInput() {
        return this.startHourInput.getAttribute('value');
    }

    async setEndHourInput(endHour) {
        await this.endHourInput.sendKeys(endHour);
    }

    async getEndHourInput() {
        return this.endHourInput.getAttribute('value');
    }

    async setDetailInput(detail) {
        await this.detailInput.sendKeys(detail);
    }

    async getDetailInput() {
        return this.detailInput.getAttribute('value');
    }

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async classSessionTypeSelectLastOption() {
        await this.classSessionTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async classSessionTypeSelectOption(option) {
        await this.classSessionTypeSelect.sendKeys(option);
    }

    getClassSessionTypeSelect(): ElementFinder {
        return this.classSessionTypeSelect;
    }

    async getClassSessionTypeSelectedOption() {
        return this.classSessionTypeSelect.element(by.css('option:checked')).getText();
    }

    async programSelectLastOption() {
        await this.programSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async programSelectOption(option) {
        await this.programSelect.sendKeys(option);
    }

    getProgramSelect(): ElementFinder {
        return this.programSelect;
    }

    async getProgramSelectedOption() {
        return this.programSelect.element(by.css('option:checked')).getText();
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

export class ClassSessionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-classSession-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-classSession'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
