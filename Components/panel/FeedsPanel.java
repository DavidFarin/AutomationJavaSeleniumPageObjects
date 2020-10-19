package util.components.rialto.rialtoPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.GenUtils;
import util.SelUtils.LocatorType;
import pages._pages_mngt.MainPageManager;

public class FeedsPanel extends RialtoPanelSuperPage {

	public FeedsPanel(MainPageManager pages) {
		super(pages);
	}
	
	@Override
	public FeedsPanel ensurePageLoaded() {
		super.ensurePageLoaded();
		waitSmall.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(query)));
		return this;
	}
	
	private String editFeed = "//mat-sidenav//profile-panel//a[contains(@class, 'sel-edit-feed')]";
	private String query = "//mat-sidenav//profile-panel//query-builder";
	private String ruleInQuery = query + "//view-mode//div[contains(@class, 'sel-category') and text() = '{0}']"
									   + "/..//span[contains(@class, 'sel-operator') and text() = '{1}']"
									   + "/..//span[contains(@class, 'sel-field') and text()='{2}']";
	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void showFeedPanel() {
		super.showPanelIfNotVisible(RialtoPanels.Feed);
		ensurePageLoaded();
	}
	
	public void clickEditFeed() {
		log.info("Click on the 'Edit feed' link");
		su.waitElementClickableAndClick(LocatorType.Xpath, editFeed);
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void verifyEditFeedLinkExists(boolean expected) {
		log.info("Verify if the 'Edit feed' link exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, editFeed), expected);
	}
	
	public void verifyRuleInQueryExists(String category, String operator, String value, boolean expected) {
		log.info("Verify if exists in the query the rule '" + category + " " + operator + " " + value + "'");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ruleInQuery, category, operator, value), expected);
	}
}
