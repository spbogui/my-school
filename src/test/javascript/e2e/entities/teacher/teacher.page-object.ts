import { element, by, ElementFinder } from 'protractor';

export class TeacherComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-teacher div table .btn-danger'));
    title = element.all(by.css('jhi-teacher div h2#page-heading span')).first();

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

export class TeacherUpdatePage {
    pageTitle = element(by.id('jhi-teacher-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    hourlyRateInput = element(by.id('field_hourlyRate'));
    employeeSelect = element(by.id('field_employee'));
    subjectSelect = element(by.id('field_subject'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setHourlyRateInput(hourlyRate) {
        await this.hourlyRateInput.sendKeys(hourlyRate);
    }

    async getHourlyRateInput() {
        return this.hourlyRateInput.getAttribute('value');
    }

    async employeeSelectLastOption() {
        await this.employeeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async employeeSelectOption(option) {
        await this.employeeSelect.sendKeys(option);
    }

    getEmployeeSelect(): ElementFinder {
        return this.employeeSelect;
    }

    async getEmployeeSelectedOption() {
        return this.employeeSelect.element(by.css('option:checked')).getText();
    }

    async subjectSelectLastOption() {
        await this.subjectSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async subjectSelectOption(option) {
        await this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect(): ElementFinder {
        return this.subjectSelect;
    }

    async getSubjectSelectedOption() {
        return this.subjectSelect.element(by.css('option:checked')).getText();
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

export class TeacherDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-teacher-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-teacher'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
