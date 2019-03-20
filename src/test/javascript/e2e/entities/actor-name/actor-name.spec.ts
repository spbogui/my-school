/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActorNameComponentsPage, ActorNameDeleteDialog, ActorNameUpdatePage } from './actor-name.page-object';

const expect = chai.expect;

describe('ActorName e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actorNameUpdatePage: ActorNameUpdatePage;
    let actorNameComponentsPage: ActorNameComponentsPage;
    let actorNameDeleteDialog: ActorNameDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActorNames', async () => {
        await navBarPage.goToEntity('actor-name');
        actorNameComponentsPage = new ActorNameComponentsPage();
        await browser.wait(ec.visibilityOf(actorNameComponentsPage.title), 5000);
        expect(await actorNameComponentsPage.getTitle()).to.eq('mySchoolApp.actorName.home.title');
    });

    it('should load create ActorName page', async () => {
        await actorNameComponentsPage.clickOnCreateButton();
        actorNameUpdatePage = new ActorNameUpdatePage();
        expect(await actorNameUpdatePage.getPageTitle()).to.eq('mySchoolApp.actorName.home.createOrEditLabel');
        await actorNameUpdatePage.cancel();
    });

    it('should create and save ActorNames', async () => {
        const nbButtonsBeforeCreate = await actorNameComponentsPage.countDeleteButtons();

        await actorNameComponentsPage.clickOnCreateButton();
        await promise.all([
            actorNameUpdatePage.setCivilityInput('civility'),
            actorNameUpdatePage.setFamilyNameInput('familyName'),
            actorNameUpdatePage.setGivenName1Input('givenName1'),
            actorNameUpdatePage.setGivenName2Input('givenName2'),
            actorNameUpdatePage.setGivenName3Input('givenName3'),
            actorNameUpdatePage.setMaidenNameInput('maidenName'),
            actorNameUpdatePage.actorSelectLastOption()
        ]);
        expect(await actorNameUpdatePage.getCivilityInput()).to.eq('civility');
        expect(await actorNameUpdatePage.getFamilyNameInput()).to.eq('familyName');
        expect(await actorNameUpdatePage.getGivenName1Input()).to.eq('givenName1');
        expect(await actorNameUpdatePage.getGivenName2Input()).to.eq('givenName2');
        expect(await actorNameUpdatePage.getGivenName3Input()).to.eq('givenName3');
        expect(await actorNameUpdatePage.getMaidenNameInput()).to.eq('maidenName');
        await actorNameUpdatePage.save();
        expect(await actorNameUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actorNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActorName', async () => {
        const nbButtonsBeforeDelete = await actorNameComponentsPage.countDeleteButtons();
        await actorNameComponentsPage.clickOnLastDeleteButton();

        actorNameDeleteDialog = new ActorNameDeleteDialog();
        expect(await actorNameDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.actorName.delete.question');
        await actorNameDeleteDialog.clickOnConfirmButton();

        expect(await actorNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
