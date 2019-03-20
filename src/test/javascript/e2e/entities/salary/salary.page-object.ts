import { element, by, ElementFinder } from 'protractor';

export class SalaryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-salary div table .btn-danger'));
    title = element.all(by.css('jhi-salary div h2#page-heading span')).first();

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

export class SalaryUpdatePage {
    pageTitle = element(by.id('jhi-salary-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountInput = element(by.id('field_amount'));
    paymentDateInput = element(by.id('field_paymentDate'));
    createdAtInput = element(by.id('field_createdAt'));
    memoInput = element(by.id('field_memo'));
    employeeSelect = element(by.id('field_employee'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setPaymentDateInput(paymentDate) {
        await this.paymentDateInput.sendKeys(paymentDate);
    }

    async getPaymentDateInput() {
        return this.paymentDateInput.getAttribute('value');
    }

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async setMemoInput(memo) {
        await this.memoInput.sendKeys(memo);
    }

    async getMemoInput() {
        return this.memoInput.getAttribute('value');
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

export class SalaryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-salary-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-salary'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
