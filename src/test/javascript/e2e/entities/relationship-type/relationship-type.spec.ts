/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RelationshipTypeComponentsPage, RelationshipTypeDeleteDialog, RelationshipTypeUpdatePage } from './relationship-type.page-object';

const expect = chai.expect;

describe('RelationshipType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let relationshipTypeUpdatePage: RelationshipTypeUpdatePage;
    let relationshipTypeComponentsPage: RelationshipTypeComponentsPage;
    let relationshipTypeDeleteDialog: RelationshipTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RelationshipTypes', async () => {
        await navBarPage.goToEntity('relationship-type');
        relationshipTypeComponentsPage = new RelationshipTypeComponentsPage();
        await browser.wait(ec.visibilityOf(relationshipTypeComponentsPage.title), 5000);
        expect(await relationshipTypeComponentsPage.getTitle()).to.eq('mySchoolApp.relationshipType.home.title');
    });

    it('should load create RelationshipType page', async () => {
        await relationshipTypeComponentsPage.clickOnCreateButton();
        relationshipTypeUpdatePage = new RelationshipTypeUpdatePage();
        expect(await relationshipTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.relationshipType.home.createOrEditLabel');
        await relationshipTypeUpdatePage.cancel();
    });

    it('should create and save RelationshipTypes', async () => {
        const nbButtonsBeforeCreate = await relationshipTypeComponentsPage.countDeleteButtons();

        await relationshipTypeComponentsPage.clickOnCreateButton();
        await promise.all([relationshipTypeUpdatePage.setNameInput('name'), relationshipTypeUpdatePage.setDescriptionInput('description')]);
        expect(await relationshipTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await relationshipTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await relationshipTypeUpdatePage.save();
        expect(await relationshipTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await relationshipTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RelationshipType', async () => {
        const nbButtonsBeforeDelete = await relationshipTypeComponentsPage.countDeleteButtons();
        await relationshipTypeComponentsPage.clickOnLastDeleteButton();

        relationshipTypeDeleteDialog = new RelationshipTypeDeleteDialog();
        expect(await relationshipTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.relationshipType.delete.question');
        await relationshipTypeDeleteDialog.clickOnConfirmButton();

        expect(await relationshipTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
