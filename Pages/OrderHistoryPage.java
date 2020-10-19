package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import models.acq.POL.POLStatus;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.acq.pol.POL_SummaryTabPage;
import util.SelUtils.LocatorType;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.OrderHistoryListComponent;
import util.components.rialto.splitView.Offering_SplitView;
import util.interfaces.GeneralInterface.CodeNameable;
import util.interfaces.GeneralInterface.Nameable;

public class OrderHistoryPage extends RialtoInternalPage {

	public OrderHistoryPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public OrderHistoryPage ensurePageLoaded() {
		ensurePageLoadedBasic();
		orderHistoryList.ensurePageLoaded();
		orderHistorySplitView.ensurePageLoaded();
		return this;
	}

	public OrderHistoryPage ensurePageLoadedBasic() {
		super.ensurePageLoaded();
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(orderHistoryLoadingBlocker)));
		waitVeryBig.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(orderHistoryTitle)));
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(splitViewLoadingBlocker)));
		return this;
	}
	
	public enum OrderHistoryStatus implements Nameable {
		WaitingForApproval("Waiting for Approval"),
		Rejected("Rejected"), 
		OrderPlaced("Order Placed"),
		Cancelled("Cancelled"),
		ToBeCancelled("To be cancelled"),
		OrderComplete("Order Complete"),
		FullyReceived("(Fully received)");

		private String name;

		OrderHistoryStatus(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum OrderHistorySearchOption implements CodeNameable {
		ISBN("ISBN", "IDENTIFIER"),
		MmsId("MMS ID", "MMS_ID"),
		OfferingId("Offering ID", "OFFERING_ID"),
		PoLineReference("PO Line Reference", "PO_LINE_REFERENCE"),
		Title("Title", "TITLE"),;

		private String name;
		private String code;

		OrderHistorySearchOption(String name, String code) {
			this.name = name;
			this.code = code;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getCode() {
			return code;
		}
	};

	private OrderHistoryListComponent orderHistoryList = new OrderHistoryListComponent(pages);
	private Offering_SplitView orderHistorySplitView = new Offering_SplitView(pages);

	private String orderHistoryLoadingBlocker = "//mat-spinner[contains(@class, 'order-history-spinner')]";
	private String orderHistoryTitle = "//div[@class = 'alma-page-column-primary-title']//span[text()='Order History']";
	private String orderHistoryEmpty = "//p[contains(@class, 'ex-list-layout-no-records-text') and text() = ' No records were found. ']";
	private String orderHistorySearchDropdown = "//ex-list-layout[contains(@class, 'sel-list-layout')]//button[contains(@class, 'sel-ex-dropdown-selected-button')]";
	private String orderHistorySearchOption = "//button[@id='{0}']";
	private String orderHistorySearchTexbox = "//ex-search[@class='ex-extended-search-searchbox']//input";
	private String orderHistorySearchButton = "//ex-search[@class = 'ex-extended-search-searchbox']//button[contains(@class, 'ex-search-search-button')]";
	private String clearOrderHistorySearch = "//button[contains(@class, 'ex-search-clear-button')]";
	private String splitViewLoadingBlocker = "//div[contains(@class, 'order-history-details-spinner')]";
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public OrderHistoryPage orderHistorySearch(String searchTerm, OrderHistorySearchOption option) {
		log.info("Search in Order History by " + option.getName());
		su.waitBigElementClickable(LocatorType.Xpath, orderHistorySearchDropdown);
		su.waitElementClickableAndClick(LocatorType.Xpath, orderHistorySearchDropdown);
		su.getElementAndClickJS(LocatorType.Xpath, orderHistorySearchOption, option.getCode());
		su.clearAndSendKeys(searchTerm.replaceAll("[^A-Za-z0-9]+", " "), LocatorType.Xpath, orderHistorySearchTexbox);	//NGS-1986
		su.getElementAndClick(LocatorType.Xpath, orderHistorySearchButton);
		return this.ensurePageLoaded();	
	}
	
	public OrderHistoryPage clearOrderHistorySearch() {
		log.info("Clear the Order History search");
		su.getElementAndClick(LocatorType.Xpath, clearOrderHistorySearch);
		return this.ensurePageLoaded();
	}
	
	public OrderHistoryPage clickItem(int row) {
		orderHistoryList.clickItem(row);
		return this.ensurePageLoaded();
	}
	
	public POL_SummaryTabPage clickPol() {
		orderHistorySplitView.clickPol();
		return pages.acq.pol.pol_SummaryTabPage.ensurePageLoaded();
	}
	
	@Override
	public OrderHistoryPage applyFacetIfExists(FacetGroup group, String facet) {
		super.applyFacetIfExists(group, facet);
		return this.ensurePageLoaded();
	}
	
	@Override
	public OrderHistoryPage clearAllFacets() {
		super.clearAllFacets();
		return this.ensurePageLoaded();
	}
	
	public OrderHistoryPage selectPagination(RialtoPaginationOption paginationOption) {
		super.selectPagination(paginationOption);
		return this.ensurePageLoaded();
	}
	
	@Override
	public OrderHistoryPage jumpToPageFixed(String pageNum) {
		super.jumpToPageFixed(pageNum);
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public OrderHistoryPage verifyOrderHistoryEmpty() {
		log.info("Verify the Order History page is empty");
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(orderHistoryEmpty)));
		return this;
	}
	
	public OrderHistoryPage verifyItemInList(RialtoItem item, OrderHistoryStatus... status) {
		orderHistoryList.verifyItemInList(item, status);
		return this;
	}
	
	public OrderHistoryPage verifySelectedByInRow(String selector, int row) {
		orderHistoryList.verifySelectedByInRow(selector, row);
		return this;
	}
	
	public OrderHistoryPage verifySelectedByInSplitView(String selector) {
		orderHistorySplitView.verifySelectedBy(selector);
		return this;
	}
	
	public OrderHistoryPage verifyApprovedByInRow(String approver, int row) {
		orderHistoryList.verifyApprovedByInRow(approver, row);
		return this;
	}
	
	public OrderHistoryPage verifyApprovedByInSplitView(String approver) {
		orderHistorySplitView.verifyApprovedBy(approver);
		return this;
	}
	
	public OrderHistoryPage verifyRejectedByInRow(String approver, int row) {
		orderHistoryList.verifyRejectedByInRow(approver, row);
		return this;
	}
	
	public OrderHistoryPage verifyRejectedByInSplitView(String approver) {
		orderHistorySplitView.verifyRejectedBy(approver);
		return this;
	}

	@Override
	public OrderHistoryPage verifyFacetApplied(String facetName, boolean expected) {
		super.verifyFacetApplied(facetName, expected);
		return this;
	}
	
	public OrderHistoryPage verifyQuantity(String expected) {
		orderHistorySplitView.verifyQuantity(expected);
		return this;
	}
	
	public OrderHistoryPage verifyPolCreated() {
		orderHistorySplitView.verifyPolCreated();
		return this.ensurePageLoaded();
	}
	
	public OrderHistoryPage verifyPolStatus(POLStatus status) {
		orderHistorySplitView.verifyPolStatus(status);
		return this;
	}
	
	public OrderHistoryPage verifyPoCreated() {
		orderHistorySplitView.verifyPoCreated();
		return this.ensurePageLoaded();
	}
	
	public OrderHistoryPage verifyAlmaOrderCreated() {
		orderHistorySplitView.verifyAlmaOrderCreated();
		return this.ensurePageLoaded();
	}

	public OrderHistoryPage verifyRejectReason(String expected) {
		orderHistorySplitView.verifyRejectReason(expected);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public String getPol() {
		return orderHistorySplitView.getPolReference();
	}
	
	public String getPo() {
		return orderHistorySplitView.getPoReference();
	}
	
	public int getListLength() {
		return orderHistoryList.getPageSize();
	}
}
