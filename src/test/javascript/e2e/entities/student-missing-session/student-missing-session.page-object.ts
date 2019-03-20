import { element, by, ElementFinder } from 'protractor';

export class StudentMissingSessionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-student-missing-session div table .btn-danger'));
    title = element.all(by.css('jhi-student-missing-session div h2#page-heading span')).first();

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

export class StudentMissingSessionUpdatePage {
    pageTitle = element(by.id('jhi-student-missing-session-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    isJustifiedInput = element(by.id('field_isJustified'));
    classSessionSelect = element(by.id('field_classSession'));
    studentSelect = element(by.id('field_student'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getIsJustifiedInput() {
        return this.isJustifiedInput;
    }

    async classSessionSelectLastOption() {
        await this.classSessionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async classSessionSelectOption(option) {
        await this.classSessionSelect.sendKeys(option);
    }

    getClassSessionSelect(): ElementFinder {
        return this.classSessionSelect;
    }

    async getClassSessionSelectedOption() {
        return this.classSessionSelect.element(by.css('option:checked')).getText();
    }

    async studentSelectLastOption() {
        await this.studentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async studentSelectOption(option) {
        await this.studentSelect.sendKeys(option);
    }

    getStudentSelect(): ElementFinder {
        return this.studentSelect;
    }

    async getStudentSelectedOption() {
        return this.studentSelect.element(by.css('option:checked')).getText();
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

export class StudentMissingSessionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-studentMissingSession-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-studentMissingSession'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
