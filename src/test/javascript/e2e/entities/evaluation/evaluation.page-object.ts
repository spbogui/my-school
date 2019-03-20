import { element, by, ElementFinder } from 'protractor';

export class EvaluationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-evaluation div table .btn-danger'));
    title = element.all(by.css('jhi-evaluation div h2#page-heading span')).first();

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

export class EvaluationUpdatePage {
    pageTitle = element(by.id('jhi-evaluation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    plannedDateInput = element(by.id('field_plannedDate'));
    isDoneInput = element(by.id('field_isDone'));
    evaluationDateInput = element(by.id('field_evaluationDate'));
    durationInput = element(by.id('field_duration'));
    evaluationTypeSelect = element(by.id('field_evaluationType'));
    schoolSchoolYearSelect = element(by.id('field_schoolSchoolYear'));
    periodSelect = element(by.id('field_period'));
    classRoomSelect = element(by.id('field_classRoom'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPlannedDateInput(plannedDate) {
        await this.plannedDateInput.sendKeys(plannedDate);
    }

    async getPlannedDateInput() {
        return this.plannedDateInput.getAttribute('value');
    }

    getIsDoneInput() {
        return this.isDoneInput;
    }
    async setEvaluationDateInput(evaluationDate) {
        await this.evaluationDateInput.sendKeys(evaluationDate);
    }

    async getEvaluationDateInput() {
        return this.evaluationDateInput.getAttribute('value');
    }

    async setDurationInput(duration) {
        await this.durationInput.sendKeys(duration);
    }

    async getDurationInput() {
        return this.durationInput.getAttribute('value');
    }

    async evaluationTypeSelectLastOption() {
        await this.evaluationTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async evaluationTypeSelectOption(option) {
        await this.evaluationTypeSelect.sendKeys(option);
    }

    getEvaluationTypeSelect(): ElementFinder {
        return this.evaluationTypeSelect;
    }

    async getEvaluationTypeSelectedOption() {
        return this.evaluationTypeSelect.element(by.css('option:checked')).getText();
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

    async periodSelectLastOption() {
        await this.periodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async periodSelectOption(option) {
        await this.periodSelect.sendKeys(option);
    }

    getPeriodSelect(): ElementFinder {
        return this.periodSelect;
    }

    async getPeriodSelectedOption() {
        return this.periodSelect.element(by.css('option:checked')).getText();
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

export class EvaluationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-evaluation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-evaluation'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
