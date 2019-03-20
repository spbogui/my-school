import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
    title = element.all(by.css('jhi-employee div h2#page-heading span')).first();

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

export class EmployeeUpdatePage {
    pageTitle = element(by.id('jhi-employee-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    hiringDateInput = element(by.id('field_hiringDate'));
    employmentStartDateInput = element(by.id('field_employmentStartDate'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setHiringDateInput(hiringDate) {
        await this.hiringDateInput.sendKeys(hiringDate);
    }

    async getHiringDateInput() {
        return this.hiringDateInput.getAttribute('value');
    }

    async setEmploymentStartDateInput(employmentStartDate) {
        await this.employmentStartDateInput.sendKeys(employmentStartDate);
    }

    async getEmploymentStartDateInput() {
        return this.employmentStartDateInput.getAttribute('value');
    }

    async actorSelectLastOption() {
        await this.actorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async actorSelectOption(option) {
        await this.actorSelect.sendKeys(option);
    }

    getActorSelect(): ElementFinder {
        return this.actorSelect;
    }

    async getActorSelectedOption() {
        return this.actorSelect.element(by.css('option:checked')).getText();
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

export class EmployeeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-employee-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-employee'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
