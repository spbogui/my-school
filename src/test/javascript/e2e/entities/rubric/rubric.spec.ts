/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RubricComponentsPage, RubricDeleteDialog, RubricUpdatePage } from './rubric.page-object';

const expect = chai.expect;

describe('Rubric e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rubricUpdatePage: RubricUpdatePage;
    let rubricComponentsPage: RubricComponentsPage;
    let rubricDeleteDialog: RubricDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Rubrics', async () => {
        await navBarPage.goToEntity('rubric');
        rubricComponentsPage = new RubricComponentsPage();
        await browser.wait(ec.visibilityOf(rubricComponentsPage.title), 5000);
        expect(await rubricComponentsPage.getTitle()).to.eq('mySchoolApp.rubric.home.title');
    });

    it('should load create Rubric page', async () => {
        await rubricComponentsPage.clickOnCreateButton();
        rubricUpdatePage = new RubricUpdatePage();
        expect(await rubricUpdatePage.getPageTitle()).to.eq('mySchoolApp.rubric.home.createOrEditLabel');
        await rubricUpdatePage.cancel();
    });

    it('should create and save Rubrics', async () => {
        const nbButtonsBeforeCreate = await rubricComponentsPage.countDeleteButtons();

        await rubricComponentsPage.clickOnCreateButton();
        await promise.all([rubricUpdatePage.setNameInput('name'), rubricUpdatePage.setDescriptionInput('description')]);
        expect(await rubricUpdatePage.getNameInput()).to.eq('name');
        expect(await rubricUpdatePage.getDescriptionInput()).to.eq('description');
        await rubricUpdatePage.save();
        expect(await rubricUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rubricComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Rubric', async () => {
        const nbButtonsBeforeDelete = await rubricComponentsPage.countDeleteButtons();
        await rubricComponentsPage.clickOnLastDeleteButton();

        rubricDeleteDialog = new RubricDeleteDialog();
        expect(await rubricDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.rubric.delete.question');
        await rubricDeleteDialog.clickOnConfirmButton();

        expect(await rubricComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
