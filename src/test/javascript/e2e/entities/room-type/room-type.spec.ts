/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RoomTypeComponentsPage, RoomTypeDeleteDialog, RoomTypeUpdatePage } from './room-type.page-object';

const expect = chai.expect;

describe('RoomType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let roomTypeUpdatePage: RoomTypeUpdatePage;
    let roomTypeComponentsPage: RoomTypeComponentsPage;
    let roomTypeDeleteDialog: RoomTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RoomTypes', async () => {
        await navBarPage.goToEntity('room-type');
        roomTypeComponentsPage = new RoomTypeComponentsPage();
        await browser.wait(ec.visibilityOf(roomTypeComponentsPage.title), 5000);
        expect(await roomTypeComponentsPage.getTitle()).to.eq('mySchoolApp.roomType.home.title');
    });

    it('should load create RoomType page', async () => {
        await roomTypeComponentsPage.clickOnCreateButton();
        roomTypeUpdatePage = new RoomTypeUpdatePage();
        expect(await roomTypeUpdatePage.getPageTitle()).to.eq('mySchoolApp.roomType.home.createOrEditLabel');
        await roomTypeUpdatePage.cancel();
    });

    it('should create and save RoomTypes', async () => {
        const nbButtonsBeforeCreate = await roomTypeComponentsPage.countDeleteButtons();

        await roomTypeComponentsPage.clickOnCreateButton();
        await promise.all([roomTypeUpdatePage.setLabelInput('label'), roomTypeUpdatePage.setDescriptionInput('description')]);
        expect(await roomTypeUpdatePage.getLabelInput()).to.eq('label');
        expect(await roomTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await roomTypeUpdatePage.save();
        expect(await roomTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await roomTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RoomType', async () => {
        const nbButtonsBeforeDelete = await roomTypeComponentsPage.countDeleteButtons();
        await roomTypeComponentsPage.clickOnLastDeleteButton();

        roomTypeDeleteDialog = new RoomTypeDeleteDialog();
        expect(await roomTypeDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.roomType.delete.question');
        await roomTypeDeleteDialog.clickOnConfirmButton();

        expect(await roomTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
