package util.components.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.SelUtils.LocatorType;


public class RialtoTable extends AnyPage {

	public RialtoTable(MainPageManager pages) {
		super(pages);
	}

	public RialtoTable ensureTableLoaded() {
		ensureTableLoadedBasic();
		waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listLength)));
		return this;
	}
	
	public RialtoTable ensureTableLoadedBasic() {
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(tableShimmer)));
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingBlocker)));
		waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableContainer)));
		return this;
	}

	protected String tableShimmer = "//tbody[contains(@class, \"ex-grid-layout-animate\")]";
	protected String loadingBlocker = "//div[contains(@class, 'ex-grid-layout-spinner')]";
	protected String listLength = "//tr[contains(@class, 'mat-row')]";
	protected String tableContainer = "//ex-grid-layout//table[contains(@class, 'sel-grid-table')]";
	protected String checkboxInRow = "//input[@id='mat-checkbox-{0}-input']";
	protected String moreActionsInRow = "//tbody//tr[{0}]//button";
	protected String moreActionsByValue = "//a[contains(text(), '{0}')]/ancestor::tr/td[last()]//button";
	protected String name = "//a[normalize-space(text())='{1}']";


	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void setCheckbox(int row, boolean check) {
		if (check == isCheckboxChecked(row))
			return;
		su.waitElementClickableAndClickJS(LocatorType.Xpath, checkboxInRow, row + 1);
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void clickProfileName(String profileName) {
		log.info("Click on profile - " + profileName);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, name, profileName);
	}

	public void clickMoreActions(int row) {
		log.info("Click on More Actions button - row " + row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, moreActionsInRow, row);
	}
	
	public void clickMoreActionsByValue(String value) {
		log.info("Click on More Actions on row with value: " + value);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, moreActionsByValue, value);
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public int getListLength() {
		ensureTableLoadedBasic();
		int length = su.getListOfElements(LocatorType.Xpath, listLength).size();
		log.info("The list's length is: " + length);
		return length;
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	private boolean isCheckboxChecked(int row) {
		return (su.getElement(LocatorType.Xpath, checkboxInRow, row + 1).getAttribute("aria-checked").equals("true"));
	}
}