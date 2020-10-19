package util.components.rialto.rialtoList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage.RialtoBadge;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Codeable;

public class RialtoListComponent extends AnyPage {

	public RialtoListComponent(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoListComponent ensurePageLoaded() {
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(ITEM_IN_ROW, 0))));
		return this;
	}

	protected String allItems = "//div[contains(@class, 'sel-column-main')]//*[contains(@class, 'sel-row')]";
	public final static String ITEM_IN_ROW = "//div[contains(@class, 'sel-column-main')]//*[contains(@class, 'sel-row-{0}-item')]";
	protected String focusedItem = "/div[contains(@class, 'ex-list-layout-selected')]";
	protected String disabledItem = "//div[contains(@class, 'disabled-row-item')]"; 
	protected String badgeInItem = "//*[text() = \"{1}\"]";


	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void clickItem(int row) {
		log.info("Click on item - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW, row);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public int getPageSize() {
		int size = su.getListOfElements(LocatorType.Xpath, allItems).size();
		log.info("The page size is: " + size);
		return size;
	}

	public String getItemDetailText(Codeable detail, int row) {
		log.info("Get item detail in row " + row);
		if (su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + detail.getCode(), row)) {
			su.waitElementVisible(LocatorType.Xpath, ITEM_IN_ROW + "//" + detail.getCode(), row);
			return su.getElementAndGetText(LocatorType.Xpath, ITEM_IN_ROW + "//" + detail.getCode(), row);
		}
		log.info("Detail does not exist");
		return "";
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyItemFocused(int row, boolean expected) {
		log.info("Verify if item is focused in row " + row);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + focusedItem, row), expected);
	}
	
	public void verifyItemDisabled(int row, boolean expected) {
		log.info("Verify if item is disabled in row " + row);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + disabledItem, row), expected);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public boolean isBadgesInItemExist(int row, RialtoBadge... badges) {
		log.info("Verify if the badges exist");
		int length = badges.length;
		for (int i = 0; i < length; i++) {
			if (!su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + badgeInItem, row, badges[i].getName()))
				return false;
		}
		return true;
	}

}