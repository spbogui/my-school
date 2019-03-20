import { element, by, ElementFinder } from 'protractor';

export class StudentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-student div table .btn-danger'));
    title = element.all(by.css('jhi-student div h2#page-heading span')).first();

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

export class StudentUpdatePage {
    pageTitle = element(by.id('jhi-student-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    pareantSeparatedInput = element(by.id('field_pareantSeparated'));
    fatherIsAliveInput = element(by.id('field_fatherIsAlive'));
    livingWithFatherInput = element(by.id('field_livingWithFather'));
    motherIsAliveInput = element(by.id('field_motherIsAlive'));
    livingWithMotherInput = element(by.id('field_livingWithMother'));
    actorSelect = element(by.id('field_actor'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getPareantSeparatedInput() {
        return this.pareantSeparatedInput;
    }
    getFatherIsAliveInput() {
        return this.fatherIsAliveInput;
    }
    getLivingWithFatherInput() {
        return this.livingWithFatherInput;
    }
    getMotherIsAliveInput() {
        return this.motherIsAliveInput;
    }
    getLivingWithMotherInput() {
        return this.livingWithMotherInput;
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

export class StudentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-student-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-student'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
