import { element, by, ElementFinder } from 'protractor';

export class PermissionAgreementComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-permission-agreement div table .btn-danger'));
    title = element.all(by.css('jhi-permission-agreement div h2#page-heading span')).first();

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

export class PermissionAgreementUpdatePage {
    pageTitle = element(by.id('jhi-permission-agreement-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    permissionAllowedInput = element(by.id('field_permissionAllowed'));
    allowanceDateInput = element(by.id('field_allowanceDate'));
    permissionStartDateInput = element(by.id('field_permissionStartDate'));
    permissionEndDateInput = element(by.id('field_permissionEndDate'));
    returnDateInput = element(by.id('field_returnDate'));
    effectiveReturnDateInput = element(by.id('field_effectiveReturnDate'));
    memoInput = element(by.id('field_memo'));
    askingPermissionSelect = element(by.id('field_askingPermission'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getPermissionAllowedInput() {
        return this.permissionAllowedInput;
    }
    async setAllowanceDateInput(allowanceDate) {
        await this.allowanceDateInput.sendKeys(allowanceDate);
    }

    async getAllowanceDateInput() {
        return this.allowanceDateInput.getAttribute('value');
    }

    async setPermissionStartDateInput(permissionStartDate) {
        await this.permissionStartDateInput.sendKeys(permissionStartDate);
    }

    async getPermissionStartDateInput() {
        return this.permissionStartDateInput.getAttribute('value');
    }

    async setPermissionEndDateInput(permissionEndDate) {
        await this.permissionEndDateInput.sendKeys(permissionEndDate);
    }

    async getPermissionEndDateInput() {
        return this.permissionEndDateInput.getAttribute('value');
    }

    async setReturnDateInput(returnDate) {
        await this.returnDateInput.sendKeys(returnDate);
    }

    async getReturnDateInput() {
        return this.returnDateInput.getAttribute('value');
    }

    async setEffectiveReturnDateInput(effectiveReturnDate) {
        await this.effectiveReturnDateInput.sendKeys(effectiveReturnDate);
    }

    async getEffectiveReturnDateInput() {
        return this.effectiveReturnDateInput.getAttribute('value');
    }

    async setMemoInput(memo) {
        await this.memoInput.sendKeys(memo);
    }

    async getMemoInput() {
        return this.memoInput.getAttribute('value');
    }

    async askingPermissionSelectLastOption() {
        await this.askingPermissionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async askingPermissionSelectOption(option) {
        await this.askingPermissionSelect.sendKeys(option);
    }

    getAskingPermissionSelect(): ElementFinder {
        return this.askingPermissionSelect;
    }

    async getAskingPermissionSelectedOption() {
        return this.askingPermissionSelect.element(by.css('option:checked')).getText();
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

export class PermissionAgreementDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-permissionAgreement-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-permissionAgreement'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
