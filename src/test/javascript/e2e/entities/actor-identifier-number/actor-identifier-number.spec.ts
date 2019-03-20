/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ActorIdentifierNumberComponentsPage,
    ActorIdentifierNumberDeleteDialog,
    ActorIdentifierNumberUpdatePage
} from './actor-identifier-number.page-object';

const expect = chai.expect;

describe('ActorIdentifierNumber e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actorIdentifierNumberUpdatePage: ActorIdentifierNumberUpdatePage;
    let actorIdentifierNumberComponentsPage: ActorIdentifierNumberComponentsPage;
    let actorIdentifierNumberDeleteDialog: ActorIdentifierNumberDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActorIdentifierNumbers', async () => {
        await navBarPage.goToEntity('actor-identifier-number');
        actorIdentifierNumberComponentsPage = new ActorIdentifierNumberComponentsPage();
        await browser.wait(ec.visibilityOf(actorIdentifierNumberComponentsPage.title), 5000);
        expect(await actorIdentifierNumberComponentsPage.getTitle()).to.eq('mySchoolApp.actorIdentifierNumber.home.title');
    });

    it('should load create ActorIdentifierNumber page', async () => {
        await actorIdentifierNumberComponentsPage.clickOnCreateButton();
        actorIdentifierNumberUpdatePage = new ActorIdentifierNumberUpdatePage();
        expect(await actorIdentifierNumberUpdatePage.getPageTitle()).to.eq('mySchoolApp.actorIdentifierNumber.home.createOrEditLabel');
        await actorIdentifierNumberUpdatePage.cancel();
    });

    it('should create and save ActorIdentifierNumbers', async () => {
        const nbButtonsBeforeCreate = await actorIdentifierNumberComponentsPage.countDeleteButtons();

        await actorIdentifierNumberComponentsPage.clickOnCreateButton();
        await promise.all([
            actorIdentifierNumberUpdatePage.setIdentifierInput('identifier'),
            actorIdentifierNumberUpdatePage.identifierTypeSelectLastOption(),
            actorIdentifierNumberUpdatePage.actorSelectLastOption()
        ]);
        expect(await actorIdentifierNumberUpdatePage.getIdentifierInput()).to.eq('identifier');
        await actorIdentifierNumberUpdatePage.save();
        expect(await actorIdentifierNumberUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actorIdentifierNumberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActorIdentifierNumber', async () => {
        const nbButtonsBeforeDelete = await actorIdentifierNumberComponentsPage.countDeleteButtons();
        await actorIdentifierNumberComponentsPage.clickOnLastDeleteButton();

        actorIdentifierNumberDeleteDialog = new ActorIdentifierNumberDeleteDialog();
        expect(await actorIdentifierNumberDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.actorIdentifierNumber.delete.question');
        await actorIdentifierNumberDeleteDialog.clickOnConfirmButton();

        expect(await actorIdentifierNumberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
