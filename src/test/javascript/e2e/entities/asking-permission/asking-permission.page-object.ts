import { element, by, ElementFinder } from 'protractor';

export class AskingPermissionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-asking-permission div table .btn-danger'));
    title = element.all(by.css('jhi-asking-permission div h2#page-heading span')).first();

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

export class AskingPermissionUpdatePage {
    pageTitle = element(by.id('jhi-asking-permission-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    askingDateInput = element(by.id('field_askingDate'));
    numberOfDayInput = element(by.id('field_numberOfDay'));
    reasonInput = element(by.id('field_reason'));
    schoolSchoolYearSelect = element(by.id('field_schoolSchoolYear'));
    studentSelect = element(by.id('field_student'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAskingDateInput(askingDate) {
        await this.askingDateInput.sendKeys(askingDate);
    }

    async getAskingDateInput() {
        return this.askingDateInput.getAttribute('value');
    }

    async setNumberOfDayInput(numberOfDay) {
        await this.numberOfDayInput.sendKeys(numberOfDay);
    }

    async getNumberOfDayInput() {
        return this.numberOfDayInput.getAttribute('value');
    }

    async setReasonInput(reason) {
        await this.reasonInput.sendKeys(reason);
    }

    async getReasonInput() {
        return this.reasonInput.getAttribute('value');
    }

    async schoolSchoolYearSelectLastOption() {
        await this.schoolSchoolYearSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schoolSchoolYearSelectOption(option) {
        await this.schoolSchoolYearSelect.sendKeys(option);
    }

    getSchoolSchoolYearSelect(): ElementFinder {
        return this.schoolSchoolYearSelect;
    }

    async getSchoolSchoolYearSelectedOption() {
        return this.schoolSchoolYearSelect.element(by.css('option:checked')).getText();
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

export class AskingPermissionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-askingPermission-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-askingPermission'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
