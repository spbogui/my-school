import { element, by, ElementFinder } from 'protractor';

export class EnrolmentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-enrolment div table .btn-danger'));
    title = element.all(by.css('jhi-enrolment div h2#page-heading span')).first();

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

export class EnrolmentUpdatePage {
    pageTitle = element(by.id('jhi-enrolment-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    enrolmentDateInput = element(by.id('field_enrolmentDate'));
    regimenRateInput = element(by.id('field_regimenRate'));
    repeatingInput = element(by.id('field_repeating'));
    modernLanguage1Input = element(by.id('field_modernLanguage1'));
    modernLanguage2Input = element(by.id('field_modernLanguage2'));
    exemptionInput = element(by.id('field_exemption'));
    withInsuranceInput = element(by.id('field_withInsurance'));
    schoolYearSelect = element(by.id('field_schoolYear'));
    actorSelect = element(by.id('field_actor'));
    classRoomSelect = element(by.id('field_classRoom'));
    regimenSelect = element(by.id('field_regimen'));
    previousSchoolSelect = element(by.id('field_previousSchool'));
    previousClassSelect = element(by.id('field_previousClass'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setEnrolmentDateInput(enrolmentDate) {
        await this.enrolmentDateInput.sendKeys(enrolmentDate);
    }

    async getEnrolmentDateInput() {
        return this.enrolmentDateInput.getAttribute('value');
    }

    async setRegimenRateInput(regimenRate) {
        await this.regimenRateInput.sendKeys(regimenRate);
    }

    async getRegimenRateInput() {
        return this.regimenRateInput.getAttribute('value');
    }

    getRepeatingInput() {
        return this.repeatingInput;
    }
    async setModernLanguage1Input(modernLanguage1) {
        await this.modernLanguage1Input.sendKeys(modernLanguage1);
    }

    async getModernLanguage1Input() {
        return this.modernLanguage1Input.getAttribute('value');
    }

    async setModernLanguage2Input(modernLanguage2) {
        await this.modernLanguage2Input.sendKeys(modernLanguage2);
    }

    async getModernLanguage2Input() {
        return this.modernLanguage2Input.getAttribute('value');
    }

    getExemptionInput() {
        return this.exemptionInput;
    }
    getWithInsuranceInput() {
        return this.withInsuranceInput;
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

    async regimenSelectLastOption() {
        await this.regimenSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async regimenSelectOption(option) {
        await this.regimenSelect.sendKeys(option);
    }

    getRegimenSelect(): ElementFinder {
        return this.regimenSelect;
    }

    async getRegimenSelectedOption() {
        return this.regimenSelect.element(by.css('option:checked')).getText();
    }

    async previousSchoolSelectLastOption() {
        await this.previousSchoolSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async previousSchoolSelectOption(option) {
        await this.previousSchoolSelect.sendKeys(option);
    }

    getPreviousSchoolSelect(): ElementFinder {
        return this.previousSchoolSelect;
    }

    async getPreviousSchoolSelectedOption() {
        return this.previousSchoolSelect.element(by.css('option:checked')).getText();
    }

    async previousClassSelectLastOption() {
        await this.previousClassSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async previousClassSelectOption(option) {
        await this.previousClassSelect.sendKeys(option);
    }

    getPreviousClassSelect(): ElementFinder {
        return this.previousClassSelect;
    }

    async getPreviousClassSelectedOption() {
        return this.previousClassSelect.element(by.css('option:checked')).getText();
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

export class EnrolmentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-enrolment-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-enrolment'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
