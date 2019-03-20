/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ImageComponentsPage, ImageDeleteDialog, ImageUpdatePage } from './image.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Image e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let imageUpdatePage: ImageUpdatePage;
    let imageComponentsPage: ImageComponentsPage;
    let imageDeleteDialog: ImageDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Images', async () => {
        await navBarPage.goToEntity('image');
        imageComponentsPage = new ImageComponentsPage();
        await browser.wait(ec.visibilityOf(imageComponentsPage.title), 5000);
        expect(await imageComponentsPage.getTitle()).to.eq('mySchoolApp.image.home.title');
    });

    it('should load create Image page', async () => {
        await imageComponentsPage.clickOnCreateButton();
        imageUpdatePage = new ImageUpdatePage();
        expect(await imageUpdatePage.getPageTitle()).to.eq('mySchoolApp.image.home.createOrEditLabel');
        await imageUpdatePage.cancel();
    });

    it('should create and save Images', async () => {
        const nbButtonsBeforeCreate = await imageComponentsPage.countDeleteButtons();

        await imageComponentsPage.clickOnCreateButton();
        await promise.all([
            imageUpdatePage.setContentInput(absolutePath),
            imageUpdatePage.setNameInput('name'),
            imageUpdatePage.setFormatInput('format'),
            imageUpdatePage.setImageSizeInput('imageSize'),
            imageUpdatePage.actorSelectLastOption()
        ]);
        expect(await imageUpdatePage.getContentInput()).to.endsWith(fileNameToUpload);
        expect(await imageUpdatePage.getNameInput()).to.eq('name');
        expect(await imageUpdatePage.getFormatInput()).to.eq('format');
        expect(await imageUpdatePage.getImageSizeInput()).to.eq('imageSize');
        const selectedIsUsed = imageUpdatePage.getIsUsedInput();
        if (await selectedIsUsed.isSelected()) {
            await imageUpdatePage.getIsUsedInput().click();
            expect(await imageUpdatePage.getIsUsedInput().isSelected()).to.be.false;
        } else {
            await imageUpdatePage.getIsUsedInput().click();
            expect(await imageUpdatePage.getIsUsedInput().isSelected()).to.be.true;
        }
        await imageUpdatePage.save();
        expect(await imageUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await imageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Image', async () => {
        const nbButtonsBeforeDelete = await imageComponentsPage.countDeleteButtons();
        await imageComponentsPage.clickOnLastDeleteButton();

        imageDeleteDialog = new ImageDeleteDialog();
        expect(await imageDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.image.delete.question');
        await imageDeleteDialog.clickOnConfirmButton();

        expect(await imageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
