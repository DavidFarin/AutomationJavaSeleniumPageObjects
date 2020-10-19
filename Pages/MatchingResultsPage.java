package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.rialto.rialtoPanel.PurchaseRequestPanel.CourseDetail;
import util.components.rialto.rialtoPanel.PurchaseRequestPanel.PurchaseRequestDetail;
import pages._pages_mngt.MainPageManager;
import util.components.rialto.rialtoPanel.PurchaseRequestPanel;
import util.components.rialto.rialtoList.MarketListComponent;

public class MatchingResultsPage extends RialtoInternalPage {
	
	public MatchingResultsPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public MatchingResultsPage ensurePageLoaded() {
		ensurePageLoadedEmptyList();
		matchingResultsPageList.ensurePageLoaded();
		return this;
	}
	
	public MatchingResultsPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pageTitle)));
		return this;
	}
	
	private MarketListComponent matchingResultsPageList = new MarketListComponent(pages);
	private PurchaseRequestPanel purchaseRequestPanel = new PurchaseRequestPanel(pages);
	
	private String pageTitle = "//span[contains(@class, 'search-results-title') and text()='Matching Results']";


	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public WorkPage clickViewAllOffers(int row) {
		log.info("Click on View all Offers - row " + row);
		matchingResultsPageList.clickViewAllOffers(row);
		return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
	}
	
	public MatchingResultsPage showPurchaseRequestPanel() {
		purchaseRequestPanel.showPurchaseRequestPanel();
		return this.ensurePageLoadedEmptyList();
	}
	
	public MatchingResultsPage closePanelIfVisible() {
		purchaseRequestPanel.closePanelIfVisible();
		return this.ensurePageLoadedEmptyList();
	}
	
	public MatchingResultsPage openCourseSessionInPanel(String courseName) {
		purchaseRequestPanel.openCourseSession(courseName);
		return this.ensurePageLoadedEmptyList();
	}
	
	public MatchingResultsPage closeCourseSessionInPanel(String courseName) {
		purchaseRequestPanel.closeCourseSession(courseName);
		return this.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public MatchingResultsPage verifyPurchaseRequestDetail (PurchaseRequestDetail detail, String expected) {
		purchaseRequestPanel.verifyPurchaseRequestDetail(detail, expected);
		return this;
	}
	
	public MatchingResultsPage verifyCourseDetail (String courseName, CourseDetail detail, String expected) {
		purchaseRequestPanel.verifyCourseDetail(courseName, detail, expected);
		return this;
	}
}
