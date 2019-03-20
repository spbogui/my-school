import { element, by, ElementFinder } from 'protractor';

export class ActorRelationshipComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-actor-relationship div table .btn-danger'));
    title = element.all(by.css('jhi-actor-relationship div h2#page-heading span')).first();

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

export class ActorRelationshipUpdatePage {
    pageTitle = element(by.id('jhi-actor-relationship-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    isActiveInput = element(by.id('field_isActive'));
    responsibleSelect = element(by.id('field_responsible'));
    studentSelect = element(by.id('field_student'));
    relationshipTypeSelect = element(by.id('field_relationshipType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getIsActiveInput() {
        return this.isActiveInput;
    }

    async responsibleSelectLastOption() {
        await this.responsibleSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async responsibleSelectOption(option) {
        await this.responsibleSelect.sendKeys(option);
    }

    getResponsibleSelect(): ElementFinder {
        return this.responsibleSelect;
    }

    async getResponsibleSelectedOption() {
        return this.responsibleSelect.element(by.css('option:checked')).getText();
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

    async relationshipTypeSelectLastOption() {
        await this.relationshipTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async relationshipTypeSelectOption(option) {
        await this.relationshipTypeSelect.sendKeys(option);
    }

    getRelationshipTypeSelect(): ElementFinder {
        return this.relationshipTypeSelect;
    }

    async getRelationshipTypeSelectedOption() {
        return this.relationshipTypeSelect.element(by.css('option:checked')).getText();
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

export class ActorRelationshipDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-actorRelationship-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-actorRelationship'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
