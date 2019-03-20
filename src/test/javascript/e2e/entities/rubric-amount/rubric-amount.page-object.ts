import { element, by, ElementFinder } from 'protractor';

export class RubricAmountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rubric-amount div table .btn-danger'));
    title = element.all(by.css('jhi-rubric-amount div h2#page-heading span')).first();

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

export class RubricAmountUpdatePage {
    pageTitle = element(by.id('jhi-rubric-amount-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountInput = element(by.id('field_amount'));
    paymentMethodInput = element(by.id('field_paymentMethod'));
    rubricSelect = element(by.id('field_rubric'));
    levelSelect = element(by.id('field_level'));
    schoolYearSelect = element(by.id('field_schoolYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setPaymentMethodInput(paymentMethod) {
        await this.paymentMethodInput.sendKeys(paymentMethod);
    }

    async getPaymentMethodInput() {
        return this.paymentMethodInput.getAttribute('value');
    }

    async rubricSelectLastOption() {
        await this.rubricSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async rubricSelectOption(option) {
        await this.rubricSelect.sendKeys(option);
    }

    getRubricSelect(): ElementFinder {
        return this.rubricSelect;
    }

    async getRubricSelectedOption() {
        return this.rubricSelect.element(by.css('option:checked')).getText();
    }

    async levelSelectLastOption() {
        await this.levelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async levelSelectOption(option) {
        await this.levelSelect.sendKeys(option);
    }

    getLevelSelect(): ElementFinder {
        return this.levelSelect;
    }

    async getLevelSelectedOption() {
        return this.levelSelect.element(by.css('option:checked')).getText();
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

export class RubricAmountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rubricAmount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rubricAmount'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
