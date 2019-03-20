import { element, by, ElementFinder } from 'protractor';

export class PeriodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-period div table .btn-danger'));
    title = element.all(by.css('jhi-period div h2#page-heading span')).first();

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

export class PeriodUpdatePage {
    pageTitle = element(by.id('jhi-period-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    createdAtInput = element(by.id('field_createdAt'));
    periodLabelSelect = element(by.id('field_periodLabel'));
    schoolYearlabelSelect = element(by.id('field_schoolYearlabel'));

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

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async periodLabelSelectLastOption() {
        await this.periodLabelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async periodLabelSelectOption(option) {
        await this.periodLabelSelect.sendKeys(option);
    }

    getPeriodLabelSelect(): ElementFinder {
        return this.periodLabelSelect;
    }

    async getPeriodLabelSelectedOption() {
        return this.periodLabelSelect.element(by.css('option:checked')).getText();
    }

    async schoolYearlabelSelectLastOption() {
        await this.schoolYearlabelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schoolYearlabelSelectOption(option) {
        await this.schoolYearlabelSelect.sendKeys(option);
    }

    getSchoolYearlabelSelect(): ElementFinder {
        return this.schoolYearlabelSelect;
    }

    async getSchoolYearlabelSelectedOption() {
        return this.schoolYearlabelSelect.element(by.css('option:checked')).getText();
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

export class PeriodDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-period-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-period'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
