import { element, by, ElementFinder } from 'protractor';

export class StudentEvaluationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-student-evaluation div table .btn-danger'));
    title = element.all(by.css('jhi-student-evaluation div h2#page-heading span')).first();

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

export class StudentEvaluationUpdatePage {
    pageTitle = element(by.id('jhi-student-evaluation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    gradeInput = element(by.id('field_grade'));
    actorSelect = element(by.id('field_actor'));
    evaluationSelect = element(by.id('field_evaluation'));
    evaluationModeSelect = element(by.id('field_evaluationMode'));
    subjectSelect = element(by.id('field_subject'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setGradeInput(grade) {
        await this.gradeInput.sendKeys(grade);
    }

    async getGradeInput() {
        return this.gradeInput.getAttribute('value');
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

    async evaluationSelectLastOption() {
        await this.evaluationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async evaluationSelectOption(option) {
        await this.evaluationSelect.sendKeys(option);
    }

    getEvaluationSelect(): ElementFinder {
        return this.evaluationSelect;
    }

    async getEvaluationSelectedOption() {
        return this.evaluationSelect.element(by.css('option:checked')).getText();
    }

    async evaluationModeSelectLastOption() {
        await this.evaluationModeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async evaluationModeSelectOption(option) {
        await this.evaluationModeSelect.sendKeys(option);
    }

    getEvaluationModeSelect(): ElementFinder {
        return this.evaluationModeSelect;
    }

    async getEvaluationModeSelectedOption() {
        return this.evaluationModeSelect.element(by.css('option:checked')).getText();
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

export class StudentEvaluationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-studentEvaluation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-studentEvaluation'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
