import { element, by, ElementFinder } from 'protractor';

export class StaffJobComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-staff-job div table .btn-danger'));
    title = element.all(by.css('jhi-staff-job div h2#page-heading span')).first();

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

export class StaffJobUpdatePage {
    pageTitle = element(by.id('jhi-staff-job-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    staffSelect = element(by.id('field_staff'));
    jobSelect = element(by.id('field_job'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStartDateInput(startDate) {
        await this.startDateInput.sendKeys(startDate);
    }

    async getStartDateInput() {
        return this.startDateInput.getAttribute('value');
    }

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
    }

    async staffSelectLastOption() {
        await this.staffSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async staffSelectOption(option) {
        await this.staffSelect.sendKeys(option);
    }

    getStaffSelect(): ElementFinder {
        return this.staffSelect;
    }

    async getStaffSelectedOption() {
        return this.staffSelect.element(by.css('option:checked')).getText();
    }

    async jobSelectLastOption() {
        await this.jobSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async jobSelectOption(option) {
        await this.jobSelect.sendKeys(option);
    }

    getJobSelect(): ElementFinder {
        return this.jobSelect;
    }

    async getJobSelectedOption() {
        return this.jobSelect.element(by.css('option:checked')).getText();
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

export class StaffJobDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-staffJob-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-staffJob'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
