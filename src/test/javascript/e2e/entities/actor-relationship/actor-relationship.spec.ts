/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ActorRelationshipComponentsPage,
    ActorRelationshipDeleteDialog,
    ActorRelationshipUpdatePage
} from './actor-relationship.page-object';

const expect = chai.expect;

describe('ActorRelationship e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actorRelationshipUpdatePage: ActorRelationshipUpdatePage;
    let actorRelationshipComponentsPage: ActorRelationshipComponentsPage;
    let actorRelationshipDeleteDialog: ActorRelationshipDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActorRelationships', async () => {
        await navBarPage.goToEntity('actor-relationship');
        actorRelationshipComponentsPage = new ActorRelationshipComponentsPage();
        await browser.wait(ec.visibilityOf(actorRelationshipComponentsPage.title), 5000);
        expect(await actorRelationshipComponentsPage.getTitle()).to.eq('mySchoolApp.actorRelationship.home.title');
    });

    it('should load create ActorRelationship page', async () => {
        await actorRelationshipComponentsPage.clickOnCreateButton();
        actorRelationshipUpdatePage = new ActorRelationshipUpdatePage();
        expect(await actorRelationshipUpdatePage.getPageTitle()).to.eq('mySchoolApp.actorRelationship.home.createOrEditLabel');
        await actorRelationshipUpdatePage.cancel();
    });

    it('should create and save ActorRelationships', async () => {
        const nbButtonsBeforeCreate = await actorRelationshipComponentsPage.countDeleteButtons();

        await actorRelationshipComponentsPage.clickOnCreateButton();
        await promise.all([
            actorRelationshipUpdatePage.responsibleSelectLastOption(),
            actorRelationshipUpdatePage.studentSelectLastOption(),
            actorRelationshipUpdatePage.relationshipTypeSelectLastOption()
        ]);
        const selectedIsActive = actorRelationshipUpdatePage.getIsActiveInput();
        if (await selectedIsActive.isSelected()) {
            await actorRelationshipUpdatePage.getIsActiveInput().click();
            expect(await actorRelationshipUpdatePage.getIsActiveInput().isSelected()).to.be.false;
        } else {
            await actorRelationshipUpdatePage.getIsActiveInput().click();
            expect(await actorRelationshipUpdatePage.getIsActiveInput().isSelected()).to.be.true;
        }
        await actorRelationshipUpdatePage.save();
        expect(await actorRelationshipUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actorRelationshipComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActorRelationship', async () => {
        const nbButtonsBeforeDelete = await actorRelationshipComponentsPage.countDeleteButtons();
        await actorRelationshipComponentsPage.clickOnLastDeleteButton();

        actorRelationshipDeleteDialog = new ActorRelationshipDeleteDialog();
        expect(await actorRelationshipDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.actorRelationship.delete.question');
        await actorRelationshipDeleteDialog.clickOnConfirmButton();

        expect(await actorRelationshipComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
