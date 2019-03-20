import { element, by, ElementFinder } from 'protractor';

export class PeriodTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-period-type div table .btn-danger'));
    title = element.all(by.css('jhi-period-type div h2#page-heading span')).first();

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

export class PeriodTypeUpdatePage {
    pageTitle = element(by.id('jhi-period-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    periodLabelInput = element(by.id('field_periodLabel'));
    descriptionInput = element(by.id('field_description'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPeriodLabelInput(periodLabel) {
        await this.periodLabelInput.sendKeys(periodLabel);
    }

    async getPeriodLabelInput() {
        return this.periodLabelInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

export class PeriodTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-periodType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-periodType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
