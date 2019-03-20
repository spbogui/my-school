/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActorComponentsPage, ActorDeleteDialog, ActorUpdatePage } from './actor.page-object';

const expect = chai.expect;

describe('Actor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actorUpdatePage: ActorUpdatePage;
    let actorComponentsPage: ActorComponentsPage;
    let actorDeleteDialog: ActorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Actors', async () => {
        await navBarPage.goToEntity('actor');
        actorComponentsPage = new ActorComponentsPage();
        await browser.wait(ec.visibilityOf(actorComponentsPage.title), 5000);
        expect(await actorComponentsPage.getTitle()).to.eq('mySchoolApp.actor.home.title');
    });

    it('should load create Actor page', async () => {
        await actorComponentsPage.clickOnCreateButton();
        actorUpdatePage = new ActorUpdatePage();
        expect(await actorUpdatePage.getPageTitle()).to.eq('mySchoolApp.actor.home.createOrEditLabel');
        await actorUpdatePage.cancel();
    });

    it('should create and save Actors', async () => {
        const nbButtonsBeforeCreate = await actorComponentsPage.countDeleteButtons();

        await actorComponentsPage.clickOnCreateButton();
        await promise.all([
            actorUpdatePage.setBirthDateInput('2000-12-31'),
            actorUpdatePage.setBirthPlaceInput('birthPlace'),
            actorUpdatePage.setGenderInput('gender'),
            actorUpdatePage.countrySelectLastOption()
        ]);
        expect(await actorUpdatePage.getBirthDateInput()).to.eq('2000-12-31');
        expect(await actorUpdatePage.getBirthPlaceInput()).to.eq('birthPlace');
        expect(await actorUpdatePage.getGenderInput()).to.eq('gender');
        await actorUpdatePage.save();
        expect(await actorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Actor', async () => {
        const nbButtonsBeforeDelete = await actorComponentsPage.countDeleteButtons();
        await actorComponentsPage.clickOnLastDeleteButton();

        actorDeleteDialog = new ActorDeleteDialog();
        expect(await actorDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.actor.delete.question');
        await actorDeleteDialog.clickOnConfirmButton();

        expect(await actorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
