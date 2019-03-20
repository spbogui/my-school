/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LevelComponentsPage, LevelDeleteDialog, LevelUpdatePage } from './level.page-object';

const expect = chai.expect;

describe('Level e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let levelUpdatePage: LevelUpdatePage;
    let levelComponentsPage: LevelComponentsPage;
    let levelDeleteDialog: LevelDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Levels', async () => {
        await navBarPage.goToEntity('level');
        levelComponentsPage = new LevelComponentsPage();
        await browser.wait(ec.visibilityOf(levelComponentsPage.title), 5000);
        expect(await levelComponentsPage.getTitle()).to.eq('mySchoolApp.level.home.title');
    });

    it('should load create Level page', async () => {
        await levelComponentsPage.clickOnCreateButton();
        levelUpdatePage = new LevelUpdatePage();
        expect(await levelUpdatePage.getPageTitle()).to.eq('mySchoolApp.level.home.createOrEditLabel');
        await levelUpdatePage.cancel();
    });

    it('should create and save Levels', async () => {
        const nbButtonsBeforeCreate = await levelComponentsPage.countDeleteButtons();

        await levelComponentsPage.clickOnCreateButton();
        await promise.all([
            levelUpdatePage.setLabelInput('label'),
            levelUpdatePage.setShortFormInput('shortForm'),
            levelUpdatePage.parentLevelSelectLastOption(),
            levelUpdatePage.cycleSelectLastOption()
        ]);
        expect(await levelUpdatePage.getLabelInput()).to.eq('label');
        expect(await levelUpdatePage.getShortFormInput()).to.eq('shortForm');
        await levelUpdatePage.save();
        expect(await levelUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await levelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Level', async () => {
        const nbButtonsBeforeDelete = await levelComponentsPage.countDeleteButtons();
        await levelComponentsPage.clickOnLastDeleteButton();

        levelDeleteDialog = new LevelDeleteDialog();
        expect(await levelDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.level.delete.question');
        await levelDeleteDialog.clickOnConfirmButton();

        expect(await levelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
