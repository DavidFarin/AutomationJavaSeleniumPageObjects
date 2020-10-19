package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import util.components.rialto.RialtoTable;
import util.interfaces.GeneralInterface.Nameable;
import util.GenUtils;
import util.SelUtils.LocatorType;

public class MyListsPage extends RialtoInternalPage {

	public MyListsPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public MyListsPage ensurePageLoaded() {
		ensurePageLoadedEmptyList();
		myListsTable.ensureTableLoaded();
		return this;
	}
	
	public MyListsPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		myListsTable.ensureTableLoadedBasic();
		waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createNewList)));
		return this;
	}
	
	public enum FilterListOption implements Nameable {
		All("All"),
		SharedWithMe("Shared with me"),
		MyLists("My lists");

		private String name;

		FilterListOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	private RialtoTable myListsTable = new RialtoTable(pages);
	
	private String filterLists = "//ex-filters//i[contains(@class, 'sel-combobox-arrow')]";
	private String removeBtn = "//button[text() = 'Remove']";
	private String createNewList = "//button[contains(@class, 'sel-ex-button') and text()=' Create New List ']";
	private String listName = "//ex-link/a[contains(text(), '{0}')]";
	private String numberOfLists = "//span[contains(@class, 'sel-pagination-summary')]";
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditListPage createNewList() {
		log.info("Create a new list");
		su.waitElementClickableAndClick(LocatorType.Xpath, createNewList);
		return pages.rialto.editListPage.ensurePageLoadedEmptyList();
	}
	
	public MyListsPage filterLists(FilterListOption category) {
		log.info("Filter the lists by " + category);
		su.waitElementClickableAndClick(LocatorType.Xpath, filterLists);
		dropdown.setText(category.getName());
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditListPage editEmptyList(String name) {
		log.info("Edit empty list " + name);
		su.getElementAndClick(LocatorType.Xpath, listName, name);
		return pages.rialto.editListPage.ensurePageLoadedEmptyList();
	}
	
	public EditListPage editList(String name) {
		log.info("Edit list " + name);
		su.getElementAndClick(LocatorType.Xpath, listName, name);
		return pages.rialto.editListPage.ensurePageLoaded();
	}
	
	public MyListsPage removeList(String listName) {
		log.info("Remove list: " + listName);
		myListsTable.clickMoreActionsByValue(listName);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, removeBtn);
		return this.ensurePageLoadedEmptyList();
	}

	public MyListsPage selectPagination(RialtoPaginationOption paginationOption) {
		super.selectPagination(paginationOption);
		return this.ensurePageLoaded();
	}

	public MyListsPage jumpToPageFixed(String pageNum) {
		super.jumpToPageFixed(pageNum);
		return this.ensurePageLoaded();
	}
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public MyListsPage verifyListExist(String name, boolean expected) {
		log.info("Verify if the list '" + name + "' exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, listName, name), expected);
		return this;
	}
	
	public MyListsPage verifyNumberOfLists(int expected) {
		log.info("Verify number of lists is " + expected);
		GenUtils.verifyResult(getNumberOfListsInPageSummary(), expected);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public int getNumberOfListsInPageSummary() {
		return Integer.parseInt(su.getElementAndGetText(LocatorType.Xpath, numberOfLists).split(" of ")[1].replaceAll("\\D+", ""));
	}

	public int getListLength() {
		return myListsTable.getListLength();
	}
}