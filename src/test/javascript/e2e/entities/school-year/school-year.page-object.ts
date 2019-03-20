import { element, by, ElementFinder } from 'protractor';

export class SchoolYearComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-school-year div table .btn-danger'));
    title = element.all(by.css('jhi-school-year div h2#page-heading span')).first();

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

export class SchoolYearUpdatePage {
    pageTitle = element(by.id('jhi-school-year-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    schoolYearlabelInput = element(by.id('field_schoolYearlabel'));
    courseStartDateInput = element(by.id('field_courseStartDate'));
    courseEndDateInput = element(by.id('field_courseEndDate'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    isBlankYearInput = element(by.id('field_isBlankYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSchoolYearlabelInput(schoolYearlabel) {
        await this.schoolYearlabelInput.sendKeys(schoolYearlabel);
    }

    async getSchoolYearlabelInput() {
        return this.schoolYearlabelInput.getAttribute('value');
    }

    async setCourseStartDateInput(courseStartDate) {
        await this.courseStartDateInput.sendKeys(courseStartDate);
    }

    async getCourseStartDateInput() {
        return this.courseStartDateInput.getAttribute('value');
    }

    async setCourseEndDateInput(courseEndDate) {
        await this.courseEndDateInput.sendKeys(courseEndDate);
    }

    async getCourseEndDateInput() {
        return this.courseEndDateInput.getAttribute('value');
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

    getIsBlankYearInput() {
        return this.isBlankYearInput;
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

export class SchoolYearDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-schoolYear-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-schoolYear'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
