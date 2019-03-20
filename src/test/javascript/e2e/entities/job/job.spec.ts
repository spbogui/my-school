/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JobComponentsPage, JobDeleteDialog, JobUpdatePage } from './job.page-object';

const expect = chai.expect;

describe('Job e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let jobUpdatePage: JobUpdatePage;
    let jobComponentsPage: JobComponentsPage;
    let jobDeleteDialog: JobDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Jobs', async () => {
        await navBarPage.goToEntity('job');
        jobComponentsPage = new JobComponentsPage();
        await browser.wait(ec.visibilityOf(jobComponentsPage.title), 5000);
        expect(await jobComponentsPage.getTitle()).to.eq('mySchoolApp.job.home.title');
    });

    it('should load create Job page', async () => {
        await jobComponentsPage.clickOnCreateButton();
        jobUpdatePage = new JobUpdatePage();
        expect(await jobUpdatePage.getPageTitle()).to.eq('mySchoolApp.job.home.createOrEditLabel');
        await jobUpdatePage.cancel();
    });

    it('should create and save Jobs', async () => {
        const nbButtonsBeforeCreate = await jobComponentsPage.countDeleteButtons();

        await jobComponentsPage.clickOnCreateButton();
        await promise.all([jobUpdatePage.setNameInput('name'), jobUpdatePage.setDescriptionInput('description')]);
        expect(await jobUpdatePage.getNameInput()).to.eq('name');
        expect(await jobUpdatePage.getDescriptionInput()).to.eq('description');
        await jobUpdatePage.save();
        expect(await jobUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await jobComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Job', async () => {
        const nbButtonsBeforeDelete = await jobComponentsPage.countDeleteButtons();
        await jobComponentsPage.clickOnLastDeleteButton();

        jobDeleteDialog = new JobDeleteDialog();
        expect(await jobDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.job.delete.question');
        await jobDeleteDialog.clickOnConfirmButton();

        expect(await jobComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
