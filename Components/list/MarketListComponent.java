package util.components.rialto.rialtoList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;

public class MarketListComponent extends RialtoListComponent {

	public MarketListComponent(MainPageManager pages) {
		super(pages);

	}

	@Override
	public MarketListComponent ensurePageLoaded() {
		super.ensurePageLoaded();
		waitVeryBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(ITEM_IN_ROW + "//" + WORK_FORMAT_AMOUNT, 0, ""))));
		return this;
	}

	public final static String WORK_FORMAT_AMOUNT = "*[contains(@class, 'work-pricing-amount') and contains(text(), '{1}')]"; //URM-138274
	protected String worksTitles = "//ex-component-loader[contains(@class, 'sel-title')]//a[contains(text(), \"{0}\")]";
	private String viewAllOffers = "span[text()='View all offers']";



	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void clickViewAllOffers(int row) {
		log.info("Click on View all Offers - row " + row);
		su.moveToElementAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + viewAllOffers, row);
	}

	public void clickWorkTitle(String workTitle) {
		su.waitElementClickableAndClickJS(LocatorType.Xpath, worksTitles, workTitle);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public boolean isWorkTitleExist(String workTitle) {
		return su.isElementExist(LocatorType.Xpath, worksTitles, workTitle);
	}

	
	
}