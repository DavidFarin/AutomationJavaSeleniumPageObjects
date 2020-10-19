package pages.rialto.profilesList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.rialto.FeedsPage;


public class MyFeedsListPage extends ProfilesListSuperPage {

	public MyFeedsListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public MyFeedsListPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pageTitle)));
		return this;
	}
	
	public MyFeedsListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		waitBig.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pageTitle)));
		return this;
	}
	
	private String pageTitle = "//div[@class='alma-page-column-primary-title']//span[text()=' My Feeds  ']"; 
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public FeedsPage editProfile(String name, String... description) {
		super.clickEditProfile(name, description);
		return pages.rialto.feedsPage.ensurePageLoadedWithoutSplitView();
	}
}
