package util.components.rialto.splitView;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;

public class RialtoSplitView extends AnyPage {

	public RialtoSplitView(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoSplitView ensurePageLoaded() {
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(closeSplitView)));
		return this;
	}

	protected String previousItem = "//button[contains(@class, 'sel-prev')]/i";
	protected String nextItem = "//button[contains(@class, 'sel-next')]/i";
	protected String closeSplitView = "//as-split-area//button[contains(@class,'sel-ex-close')]//i";


	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoSplitView clickPreviousItem() {
		log.info("Click on 'Previous Item' button");
		su.waitElementClickableAndClick(LocatorType.Xpath, previousItem);
		return this.ensurePageLoaded();
	}

	public RialtoSplitView clickNextItem() {
		log.info("Click on 'Next Item' button");
		su.waitElementClickableAndClick(LocatorType.Xpath, nextItem);
		return this.ensurePageLoaded();
	}

	public void closeSplitView() {
		log.info("Close Split View");        
		su.getElementAndClickIfExists(LocatorType.Xpath, closeSplitView);
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifySplitViewClosed() {
		log.info("Verify the Split View is closed");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, closeSplitView), false);
	}
}