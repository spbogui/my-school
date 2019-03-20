import { element, by, ElementFinder } from 'protractor';

export class SchoolSchoolYearComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-school-school-year div table .btn-danger'));
    title = element.all(by.css('jhi-school-school-year div h2#page-heading span')).first();

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

export class SchoolSchoolYearUpdatePage {
    pageTitle = element(by.id('jhi-school-school-year-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    schoolNameSelect = element(by.id('field_schoolName'));
    schoolYearlabelSelect = element(by.id('field_schoolYearlabel'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async schoolNameSelectLastOption() {
        await this.schoolNameSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schoolNameSelectOption(option) {
        await this.schoolNameSelect.sendKeys(option);
    }

    getSchoolNameSelect(): ElementFinder {
        return this.schoolNameSelect;
    }

    async getSchoolNameSelectedOption() {
        return this.schoolNameSelect.element(by.css('option:checked')).getText();
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

export class SchoolSchoolYearDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-schoolSchoolYear-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-schoolSchoolYear'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
