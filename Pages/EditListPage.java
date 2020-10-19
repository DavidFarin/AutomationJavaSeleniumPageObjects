package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.rialto.rialtoPanel.ListPanel;
import util.components.rialto.rialtoPanel.ListPanel.ListPanelDetail;
import util.interfaces.GeneralInterface.Nameable;

public class EditListPage extends RialtoInternalPage{

	public EditListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public EditListPage ensurePageLoaded() {
		ensurePageLoadedEmptyList();
		pageList.ensurePageLoaded();
		return this;
	}

	public EditListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editName)));
		return this;
	}

	public enum ListVisibility implements Nameable {
		Private("Private list"),
		Shared("Shared list");

		private String name;

		ListVisibility(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	private OfferingListComponent pageList = new OfferingListComponent(pages);
	private ListPanel listPanel = new ListPanel(pages);

	private String listName = "//div[@class = 'alma-page-column-primary-title']//span[1]";
	private String editName = "//list-info//input[contains(@class, 'sel-input')]";
	private String editDescription = "//list-info//textarea[contains(@class, 'sel-textarea')]";
	private String listVisibility = "//ex-label[contains(@class,'sel-list-visibility')]//span[contains(@class, 'sel-value')]";
	private String saveBtn = "//button[contains(@class, 'sel-ex-button') and text() = ' Save ']";
	private String cancelBtn = "//button[contains(@class, 'sel-ex-button') and text() = ' Cancel ']";
	private String removeFromList = "//button[contains(@class, 'sel-record-actions-button')]//span[text()='Remove from List']";
	private String targetList = "//button[contains(@class, 'sel-record-actions-button')]//span[text()='{0}']";
	private String noMembersInList = "//alma-icon [contains(@class, 'sel-no-items-in-list-icon')]";

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public EditListPage editName (String name) {
		log.info("Set List Name to: '" + name + "'");
		su.getElementAndClick(LocatorType.Xpath, editName);
		su.clearAndSendKeys(name, LocatorType.Xpath, editName);
		su.getElementAndClick(LocatorType.Xpath, listName);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage editDescription(String description) {
		log.info("Set List Description to: '" + description + "'");
		su.getElementAndClick(LocatorType.Xpath, editDescription);
		su.clearAndSendKeys(description, LocatorType.Xpath, editDescription);
		su.getElementAndClick(LocatorType.Xpath, listName);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage addItemToList(RialtoItem item, String newList) {
		log.info("Add the item '" + item.getTitle() + "' to the list '" + newList + "'");
		int row = pageList.getRowOfItem(item);
		pageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, addToList);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, newList);
		return this.ensurePageLoaded();
	}

	public EditListPage addItemToCart(RialtoItem item) {
		log.info("Add the item '" + item.getTitle() + "' to the cart");
		int row = pageList.getRowOfItem(item);
		pageList.clickAddToCart(row);
		return this.ensurePageLoaded();
	}

	public EditListPage moveItemToCart(RialtoItem item) {
		log.info("Move the item '" + item.getTitle() + "' to the cart");
		int row = pageList.getRowOfItem(item);
		pageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, moveToCart);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage removeItemFromList(RialtoItem item) {
		log.info("Remove the item '" + item.getTitle() + "' from the list");
		int row = pageList.getRowOfItem(item);
		pageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, removeFromList);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage moveItemToList(RialtoItem item, String newList) {
		log.info("Move the item '" + item.getTitle() + "' to the list '" + newList + "'");
		int row = pageList.getRowOfItem(item);
		pageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, moveToList);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, newList);
		return this.ensurePageLoaded();
	}

	public MyListsPage save() {
		log.info("Save the list");
		su.getElementAndClick(LocatorType.Xpath, saveBtn);
		return pages.rialto.myListsPage.ensurePageLoaded();
	}

	public EditListPage showListPanel() {
		listPanel.showPanel();
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage setListShared() {
		listPanel.setListShared();
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage shareListWithUser(String user) {
		listPanel.shareListWithUser(user);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage addOwner(String user) {
		listPanel.addOwner(user);
		return this.ensurePageLoadedEmptyList();
	}

	public EditListPage removeOwner(String user) {
		listPanel.removeOwner(user);
		return this.ensurePageLoadedEmptyList();
	}

	public MyListsPage removeYourselfAsOwner() {
		listPanel.removeYourselfAsOwner();
		return pages.rialto.myListsPage.ensurePageLoadedEmptyList();
	}

	public EditListPage showFacets() {
		super.showFacets();
		return this.ensurePageLoadedEmptyList();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public EditListPage clickSaveBtn() {
		log.info("Click on 'Save' button");
		su.getElementAndClick(LocatorType.Xpath, saveBtn);
		return this.ensurePageLoadedEmptyList();
	}

	public MyListsPage clickCancel() {
		log.info("Click on 'Cancel' button");
		su.getElementAndClick(LocatorType.Xpath, cancelBtn);
		return pages.rialto.myListsPage.ensurePageLoadedEmptyList();
	}

	public MyListsPage clickBack() {
		super.clickBack();
		return pages.rialto.myListsPage.ensurePageLoadedEmptyList();
	}

	public EditListPage closeListPanelIfVisible() {
		listPanel.closePanelIfVisible();
		return this.ensurePageLoadedEmptyList();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public EditListPage verifyListName(String name) {
		log.info("Verify the List name is: " + name);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, listName), name);
		return this;
	}

	public EditListPage verifyItemInList(RialtoItem item, boolean expected, RialtoBadge... badges) {
		pageList.verifyItemInList(item, expected, badges);
		return this;
	}

	@Override
	public EditListPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		super.verifyMsgAppears(expectedMessage, placeHolders);
		return this;
	}

	public EditListPage verifyPanelDetail(ListPanelDetail detail, String expected) {
		listPanel.verifyPanelDetail(detail, expected);
		return this;
	}

	public EditListPage verifyOwnerExists(String user, boolean expected) {
		listPanel.verifyOwnerExists(user, expected);
		return this;
	}

	public EditListPage verifyVisibility(ListVisibility visibility) {
		log.info("Verify the list's visibility is: " + visibility.getName());
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, listVisibility), visibility.getName());
		return this;
	}

	public EditListPage verifyNoMembersInList() {
		log.info("Verify error picture exist");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, noMembersInList), true);
		return this;
	}
	
	public EditListPage verifyDdaConfigurationExists(boolean expected) {
		super.verifyDdaConfigurationExists(expected);
		return this;
	}
	
	public EditListPage verifyAddToDdaInMoreActionsExists(int row, boolean expected) {
		super.verifyAddToDdaInMoreActionsExists(row, expected);
		return this;
	}
}

