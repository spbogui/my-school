/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClassRoomComponentsPage, ClassRoomDeleteDialog, ClassRoomUpdatePage } from './class-room.page-object';

const expect = chai.expect;

describe('ClassRoom e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let classRoomUpdatePage: ClassRoomUpdatePage;
    let classRoomComponentsPage: ClassRoomComponentsPage;
    let classRoomDeleteDialog: ClassRoomDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ClassRooms', async () => {
        await navBarPage.goToEntity('class-room');
        classRoomComponentsPage = new ClassRoomComponentsPage();
        await browser.wait(ec.visibilityOf(classRoomComponentsPage.title), 5000);
        expect(await classRoomComponentsPage.getTitle()).to.eq('mySchoolApp.classRoom.home.title');
    });

    it('should load create ClassRoom page', async () => {
        await classRoomComponentsPage.clickOnCreateButton();
        classRoomUpdatePage = new ClassRoomUpdatePage();
        expect(await classRoomUpdatePage.getPageTitle()).to.eq('mySchoolApp.classRoom.home.createOrEditLabel');
        await classRoomUpdatePage.cancel();
    });

    it('should create and save ClassRooms', async () => {
        const nbButtonsBeforeCreate = await classRoomComponentsPage.countDeleteButtons();

        await classRoomComponentsPage.clickOnCreateButton();
        await promise.all([classRoomUpdatePage.setNameInput('name'), classRoomUpdatePage.levelSelectLastOption()]);
        expect(await classRoomUpdatePage.getNameInput()).to.eq('name');
        await classRoomUpdatePage.save();
        expect(await classRoomUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await classRoomComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ClassRoom', async () => {
        const nbButtonsBeforeDelete = await classRoomComponentsPage.countDeleteButtons();
        await classRoomComponentsPage.clickOnLastDeleteButton();

        classRoomDeleteDialog = new ClassRoomDeleteDialog();
        expect(await classRoomDeleteDialog.getDialogTitle()).to.eq('mySchoolApp.classRoom.delete.question');
        await classRoomDeleteDialog.clickOnConfirmButton();

        expect(await classRoomComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
