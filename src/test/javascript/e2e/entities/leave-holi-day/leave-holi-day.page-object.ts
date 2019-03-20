import { element, by, ElementFinder } from 'protractor';

export class LeaveHoliDayComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-leave-holi-day div table .btn-danger'));
    title = element.all(by.css('jhi-leave-holi-day div h2#page-heading span')).first();

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

export class LeaveHoliDayUpdatePage {
    pageTitle = element(by.id('jhi-leave-holi-day-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    labelInput = element(by.id('field_label'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    memoInput = element(by.id('field_memo'));
    createdAtInput = element(by.id('field_createdAt'));
    schoolYearSelect = element(by.id('field_schoolYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLabelInput(label) {
        await this.labelInput.sendKeys(label);
    }

    async getLabelInput() {
        return this.labelInput.getAttribute('value');
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

    async setMemoInput(memo) {
        await this.memoInput.sendKeys(memo);
    }

    async getMemoInput() {
        return this.memoInput.getAttribute('value');
    }

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async schoolYearSelectLastOption() {
        await this.schoolYearSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schoolYearSelectOption(option) {
        await this.schoolYearSelect.sendKeys(option);
    }

    getSchoolYearSelect(): ElementFinder {
        return this.schoolYearSelect;
    }

    async getSchoolYearSelectedOption() {
        return this.schoolYearSelect.element(by.css('option:checked')).getText();
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

export class LeaveHoliDayDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-leaveHoliDay-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-leaveHoliDay'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
