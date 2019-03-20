import { element, by, ElementFinder } from 'protractor';

export class TeacherSubjectSchoolYearComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-teacher-subject-school-year div table .btn-danger'));
    title = element.all(by.css('jhi-teacher-subject-school-year div h2#page-heading span')).first();

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

export class TeacherSubjectSchoolYearUpdatePage {
    pageTitle = element(by.id('jhi-teacher-subject-school-year-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    isPrincipalInput = element(by.id('field_isPrincipal'));
    actorSelect = element(by.id('field_actor'));
    teacherSelect = element(by.id('field_teacher'));
    schoolSchoolYearSelect = element(by.id('field_schoolSchoolYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getIsPrincipalInput() {
        return this.isPrincipalInput;
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

    async teacherSelectLastOption() {
        await this.teacherSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async teacherSelectOption(option) {
        await this.teacherSelect.sendKeys(option);
    }

    getTeacherSelect(): ElementFinder {
        return this.teacherSelect;
    }

    async getTeacherSelectedOption() {
        return this.teacherSelect.element(by.css('option:checked')).getText();
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

export class TeacherSubjectSchoolYearDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-teacherSubjectSchoolYear-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-teacherSubjectSchoolYear'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
