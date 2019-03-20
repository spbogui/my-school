import { element, by, ElementFinder } from 'protractor';

export class StudentDiplomaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-student-diploma div table .btn-danger'));
    title = element.all(by.css('jhi-student-diploma div h2#page-heading span')).first();

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

export class StudentDiplomaUpdatePage {
    pageTitle = element(by.id('jhi-student-diploma-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    mentionInput = element(by.id('field_mention'));
    graduationDateInput = element(by.id('field_graduationDate'));
    studentSelect = element(by.id('field_student'));
    diplomaSelect = element(by.id('field_diploma'));
    schoolSchoolYearSelect = element(by.id('field_schoolSchoolYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMentionInput(mention) {
        await this.mentionInput.sendKeys(mention);
    }

    async getMentionInput() {
        return this.mentionInput.getAttribute('value');
    }

    async setGraduationDateInput(graduationDate) {
        await this.graduationDateInput.sendKeys(graduationDate);
    }

    async getGraduationDateInput() {
        return this.graduationDateInput.getAttribute('value');
    }

    async studentSelectLastOption() {
        await this.studentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async studentSelectOption(option) {
        await this.studentSelect.sendKeys(option);
    }

    getStudentSelect(): ElementFinder {
        return this.studentSelect;
    }

    async getStudentSelectedOption() {
        return this.studentSelect.element(by.css('option:checked')).getText();
    }

    async diplomaSelectLastOption() {
        await this.diplomaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async diplomaSelectOption(option) {
        await this.diplomaSelect.sendKeys(option);
    }

    getDiplomaSelect(): ElementFinder {
        return this.diplomaSelect;
    }

    async getDiplomaSelectedOption() {
        return this.diplomaSelect.element(by.css('option:checked')).getText();
    }

    async schoolSchoolYearSelectLastOption() {
        await this.schoolSchoolYearSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schoolSchoolYearSelectOption(option) {
        await this.schoolSchoolYearSelect.sendKeys(option);
    }

    getSchoolSchoolYearSelect(): ElementFinder {
        return this.schoolSchoolYearSelect;
    }

    async getSchoolSchoolYearSelectedOption() {
        return this.schoolSchoolYearSelect.element(by.css('option:checked')).getText();
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

export class StudentDiplomaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-studentDiploma-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-studentDiploma'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
