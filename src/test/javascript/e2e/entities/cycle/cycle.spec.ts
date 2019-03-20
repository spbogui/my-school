/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CycleComponentsPage, CycleDeleteDialog, CycleUpdatePage } from './cycle.page-object';

const expect = chai.expect;

describe('Cycle e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let cycleUpdatePage: CycleUpdatePage;
    let cycleComponentsPage: CycleComponentsPage;
    let cycleDeleteDialog: CycleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cycles', async () => {
        await navBarPage.goToEntity('cycle');
        cycleComponentsPage = new CycleComponentsPage();
        await browser.wait(ec.visibilityOf(cycleComponentsPage.title), 5000);
        expect(await cycleComponentsPage.getTitle()).to.eq('mySchoolApp.cycle.home.title');
    });

    it('should load create Cycle page', async () => {
        await cycleComponentsPage.clickOnCreateButton();
        cycleUpdatePage = new CycleUpdatePage();
        expect(await cycleUpdatePage.getPageTitle()).to.eq('mySchoolApp.cycle.home.createOrEditLabel');
        await cycleUpdatePage.cancel();
    });

    it('should create and save Cycles', async () => {
        const nbButtonsBeforeCreate = await cycleComponentsPage.countDeleteButtons();

        await cycleComponentsPage.clickOnCreateButton();
        await promise.all([cycleUpdatePage.setLabelInput('label'), cycleUpdatePage.setDescriptionInput('description')]);
        expect(await cycleUpdatePage.getLabelInput()).to.eq('label');
        expect(await cycleUpdatePage.getDescriptionInput()).to.eq('description');
        await cycleUpdatePage.save();
        expect(await cycleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await cycleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Cycle', async () => {
        const nbButtonsBeforeDelete = await cycleComponentsPage.countDeleteButtons();
        await cycleComponentsPage.clickOnLastDeleteButton();

        cycleDeleteDialog = new CycleDeleteDialog();
        expect(await cycleDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.cycle.delete.question');
        await cycleDeleteDialog.clickOnConfirmButton();

        expect(await cycleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
