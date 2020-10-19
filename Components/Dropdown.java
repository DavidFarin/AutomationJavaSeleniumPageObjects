package util.components.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;

public class RialtoDropdown extends AnyPage {

	public RialtoDropdown(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoDropdown ensurePageLoaded() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.elementToBeClickable(By.xpath(searchInput)));
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(optionUnSelected, ""))));
		GenUtils.sleepMillis(200);
		return this;
	}

	private String searchInput = "//div[contains(@class,'ex-combobox-menu-wrapper')]//input";
	private String option = "//div[contains(@class, 'sel-menu-option')]//mark[text() = \"{0}\"]";
	private String optionUnSelected = "//div[contains(@class, 'sel-menu-option')]//span[contains(text(), '{0}')]";
	private String optionSelected = "//div[contains(@class, 'ex-group-outlet-selected')]//span[text()='{0}']";

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void setText(String searchTerm) {
		ensurePageLoaded();
		su.waitElementVisibleAndSendKeys(searchTerm, LocatorType.Xpath, searchInput);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, option, searchTerm);
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public boolean isOptionSelected(String option) {
		ensurePageLoaded();
		return su.isElementExist(LocatorType.Xpath, optionSelected, option);
	}
	
	public boolean isOptionExists(String option) {
		ensurePageLoaded();
		return su.isElementExist(LocatorType.Xpath, optionUnSelected, option);
	}
}