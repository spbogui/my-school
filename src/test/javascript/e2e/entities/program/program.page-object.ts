import { element, by, ElementFinder } from 'protractor';

export class ProgramComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-program div table .btn-danger'));
    title = element.all(by.css('jhi-program div h2#page-heading span')).first();

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

export class ProgramUpdatePage {
    pageTitle = element(by.id('jhi-program-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    startHourInput = element(by.id('field_startHour'));
    endHourInput = element(by.id('field_endHour'));
    subjectSelect = element(by.id('field_subject'));
    classRoomSelect = element(by.id('field_classRoom'));
    roomSelect = element(by.id('field_room'));
    daysSelect = element(by.id('field_days'));
    schoolYearSelect = element(by.id('field_schoolYear'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStartHourInput(startHour) {
        await this.startHourInput.sendKeys(startHour);
    }

    async getStartHourInput() {
        return this.startHourInput.getAttribute('value');
    }

    async setEndHourInput(endHour) {
        await this.endHourInput.sendKeys(endHour);
    }

    async getEndHourInput() {
        return this.endHourInput.getAttribute('value');
    }

    async subjectSelectLastOption() {
        await this.subjectSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async subjectSelectOption(option) {
        await this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect(): ElementFinder {
        return this.subjectSelect;
    }

    async getSubjectSelectedOption() {
        return this.subjectSelect.element(by.css('option:checked')).getText();
    }

    async classRoomSelectLastOption() {
        await this.classRoomSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async classRoomSelectOption(option) {
        await this.classRoomSelect.sendKeys(option);
    }

    getClassRoomSelect(): ElementFinder {
        return this.classRoomSelect;
    }

    async getClassRoomSelectedOption() {
        return this.classRoomSelect.element(by.css('option:checked')).getText();
    }

    async roomSelectLastOption() {
        await this.roomSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async roomSelectOption(option) {
        await this.roomSelect.sendKeys(option);
    }

    getRoomSelect(): ElementFinder {
        return this.roomSelect;
    }

    async getRoomSelectedOption() {
        return this.roomSelect.element(by.css('option:checked')).getText();
    }

    async daysSelectLastOption() {
        await this.daysSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async daysSelectOption(option) {
        await this.daysSelect.sendKeys(option);
    }

    getDaysSelect(): ElementFinder {
        return this.daysSelect;
    }

    async getDaysSelectedOption() {
        return this.daysSelect.element(by.css('option:checked')).getText();
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

export class ProgramDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-program-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-program'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
