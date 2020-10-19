package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import models.rialto.RialtoBib;
import util.components.rialto.rialtoPanel.HoldingsPanel;
import util.components.rialto.rialtoPanel.PurchaseRequestPanel;
import util.components.rialto.rialtoPanel.PurchaseRequestPanel.PurchaseRequestDetail;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.acq.misc.PurchaseRequest_GeneralInformationTabPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.angular.AngDropdown;
import util.components.common.Button;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.rialto.splitView.Offering_SplitView;

public class WorkPage extends RialtoInternalPage {

	public WorkPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public WorkPage ensurePageLoaded() {
		ensurePageLoadedWithoutSplitView();
		workPageSplitView.ensurePageLoaded();
		return this;
	}

	public WorkPage ensurePageLoadedWithoutSplitView() {
		super.ensurePageLoaded();
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(workPageLoadingBlocker)));
		workPageList.ensurePageLoaded();
		return this;
	}

	private OfferingListComponent workPageList = new OfferingListComponent(pages);
	private Offering_SplitView workPageSplitView = new Offering_SplitView(pages);
	private PurchaseRequestPanel purchaseRequestPanel = new PurchaseRequestPanel(pages);
	private HoldingsPanel holdingsPanel = new HoldingsPanel(pages);
	private AngDropdown chooseOrderingLibrary = new AngDropdown(pages, "//button[contains(@class, 'sel-ex-combobox-selected-button')]//span[text()=' Choose Ordering Library']", LocatorType.Xpath);
	private AngDropdown chooseFund = new AngDropdown(pages, "//button[contains(@class, 'sel-ex-combobox-selected-button')]//span[text()=' Choose Fund']", LocatorType.Xpath);
	private Button apply = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text() = 'Apply']", LocatorType.Xpath);
	private String targetList = "//button[contains(@class, 'sel-record-actions-button')]//span[text()='{0}']";
	private String workPageLoadingBlocker = "//mat-spinner[contains(@class, 'work-details-spinner')]";
	private String summarySessionHoldingNumber = "//ex-summary-section//div[contains(@class, 'ex-summary-section-count')]";

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public WorkPage sortList(RialtoSort option) {
		super.sortList(option);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage addSeveralItemsToCart(int numOfItems) {
		int listLength = workPageList.getPageSize();
		for (int row = 0; row < listLength && row < numOfItems; row++) {
			clickAddToCart(row);
		}
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage addItemToList(int row, String list) {
		log.info("Add item in row " + row + " to the list '" + list + "'");
		workPageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, addToList);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, list);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage showFacets() {
		super.showFacets();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage configureDDALibraryAndFund(String library, String fundName) {
		log.info("Choose Ordering Library and Fund for DDA Pool");
		chooseOrderingLibrary.setBySendingKeys(library);
		chooseFund.setBySendingKeys(fundName);
		apply.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public WorkPage clickAddToCart(int row) {
		workPageList.clickAddToCart(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage clickLinkAndAddToCart(int row) {
		workPageList.clickLinkAndAddToCart(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage clickRelinkAndAddToCart(int row) {
		workPageList.clickRelinkAndAddToCart(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage replaceTitle(int row) {
		workPageList.clickReplaceTitle(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage clickAddToDDA(int row) {
		workPageList.clickAddToDDA(row);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage clickAddToCartInSplitView() {
		log.info("Click on Add to Cart in the Split View");
		workPageSplitView.clickAddToCart();
		return this.ensurePageLoaded();
	}

	public WorkPage clickItem(int row) {
		workPageList.clickItem(row);
		workPageSplitView.ensurePageLoaded();
		return this;
	}
	
	public PurchaseRequest_GeneralInformationTabPage clickPurchaseRequestBadge() {
		workPageList.clickPurchaseRequestBadge();
		return pages.acq.misc.purchaseRequest_GeneralInformationTabPage.ensurePageLoaded();
	}
	
	public WorkPage showPurchaseRequestPanel() {
		purchaseRequestPanel.showPurchaseRequestPanel();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage showHoldingsPanel() {
		holdingsPanel.showHoldingsPanel();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage showAvailableForSessionIfClosed() {
		holdingsPanel.showAvailableForSessionIfClosed();
		return this;
	}

	public WorkPage closePanelIfVisible() {
		holdingsPanel.closePanelIfVisible();
		return this.ensurePageLoadedWithoutSplitView();
	}

	@Override
	public WorkPage applyFacetIfExists(FacetGroup group, String facet) {
		super.applyFacetIfExists(group, facet);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public WorkPage clickApply() {
		super.clickApply();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public WorkPage clickFacetCheckboxIfExists(FacetGroup facetGroupName, String facetName) {
		super.clickFacetCheckboxIfExists(facetGroupName, facetName);
		return this.ensurePageLoadedWithoutSplitView();
	}

	@Override
	public WorkPage clearAllFacets() {
		super.clearAllFacets();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage clickPreviousItem() {
		workPageSplitView.clickPreviousItem();
		return this.ensurePageLoaded();
	}

	public WorkPage clickNextItem() {
		workPageSplitView.clickNextItem();
		return this.ensurePageLoaded();
	}

	public WorkPage clickListViewButton() {
		super.clickListViewButton();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage clickSplitViewButton() {
		super.clickSplitViewButton();
		return this.ensurePageLoaded();
	}

	public WorkPage clickCloseSplitViewButton() {
		workPageSplitView.closeSplitView();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public MarketPage clickBack() {
		super.clickBack();
		return pages.rialto.marketPage.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public WorkPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		super.verifyMsgAppears(expectedMessage, placeHolders);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage verifyItemFocused(int row, boolean expected) {
		workPageList.verifyItemFocused(row, expected);
		return this;
	}

	public WorkPage verifySplitViewDetails(int row) {
		log.info("Verify Split View details in row: " + row);
		GenUtils.verifyResult(workPageList.getItem(row).deepEquals(workPageSplitView.getItem()), true);
		return this;
	}

	public WorkPage verifyItemInList(RialtoItem item, boolean expected, RialtoBadge... badges) {
		workPageList.verifyItemInList(item, expected, badges);
		return this;
	}
	
	public WorkPage verifyBadgesInItem(int row, RialtoBadge[] badges) {
		workPageList.isBadgesInItemExist(row, badges);
		return this;
	}
	
	public WorkPage verifyBibInHoldingsPanel(RialtoBib bib, boolean expected) {
		holdingsPanel.verifyBibInHoldingsPanel(bib, expected);
		return this;
	}

	public void verifyGroupName(String name) {
		holdingsPanel.showHoldingsPanel().verifyGroupName(name);
	}

	@Override
	public WorkPage verifyFacetApplied(String facetName, boolean expected) {
		super.verifyFacetApplied(facetName, expected);
		return this;
	}

	public WorkPage verifyAddToCartButtonIsDisabled(int row, boolean expected) {
		workPageList.verifyAddToCartButtonIsDisabled(row, expected);
		return this;
	}
	
	public WorkPage verifyAddToCartButtonIsDisabledInMoreActions(int row, boolean expected) {
		workPageList.clickMoreActions(row);
		workPageList.verifyAddToCartButtonDisabledInMoreActions(expected);
		su.clickKeyboard(Keys.ESCAPE);
		return this;
	}
	
	public WorkPage verifyReplaceTitleButtonIsDisabled(int row, boolean expected) {
		workPageList.verifyReplaceTitleButtonDisabled(row, expected);
		return this;
	}

	public WorkPage verifyAddToCartButtonIsDisabledInSplitView() {
		workPageSplitView.verifyAddToCartButtonIsDisabled();
		return this;
	}
	
	public WorkPage verifyAddToDdaInMoreActionsExists(int row, boolean expected) {
		super.verifyAddToDdaInMoreActionsExists(row, expected);
		return this;
	}
	
	public WorkPage verifyDdaConfigurationExists(boolean expected) {
		super.verifyDdaConfigurationExists(expected);
		return this;
	}

	public WorkPage verifyListSorted(RialtoSort option) {
		log.info("Verify the list is sorted by " + option.getName());
		workPageList.verifyListSorted(option);
		return this;
	}

	public WorkPage verifySplitViewClosed() {
		workPageSplitView.verifySplitViewClosed();
		return this;
	}

	public WorkPage verifyItemsNumberInCart(int expected) {
		super.verifyItemsNumberInCart(expected);
		return this;
	}

	public WorkPage verifyNumberOfItemsListed(int expected) {
		log.info("Verify the number of items listed in the Work Page is " + expected);
		GenUtils.verifyResult(getNumberOfResults(), expected);
		return this;
	}
	
	public WorkPage verifyPurchaseRequestDetail (PurchaseRequestDetail detail, String expected) {
		purchaseRequestPanel.verifyPurchaseRequestDetail(detail, expected);
		return this;
	}
	
	public WorkPage verifyNumberOfHoldingsDisplayedInPanel () {
		log.info("Verify the panel's number is equal to the panel's list length");
		GenUtils.verifyResult(holdingsPanel.getListLength(), getNumberOfHoldingsInWork());
		return this;
	}
	
	public WorkPage verifyNumberInSummarySession (int expected) {
		log.info("Verify the number of holdings displayed in the Summary Session is: " + expected);
		GenUtils.verifyResult(getNumberOfHoldingsInWork(), expected);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		return workPageList.getItem(row);
	}

	public int getListLength() {
		return workPageList.getPageSize();
	}
	
	public RialtoBib getBibInHoldingsPanel(int row) {
		return holdingsPanel.getBib(row);
	}
	
	public int getHoldingsPanelListLength() {
		return holdingsPanel.getListLength();
	}
	
	public int getNumberOfHoldingsInWork() {
		su.waitBigElementVisible(LocatorType.Xpath, summarySessionHoldingNumber);
		int numberOfHoldings = Integer.parseInt(su.getElementAndGetText(LocatorType.Xpath, summarySessionHoldingNumber)); 
		log.info("The number of Holdings is " + numberOfHoldings);
		return numberOfHoldings;
	}

	public int getRowOfItem(RialtoItem item, RialtoBadge... badges) {
		return workPageList.getRowOfItem(item, badges);
	}
	
	public int getFacetAmount(FacetGroup group, String facetName) {
		return facet.getFacetAmount(group, facetName);
	}
}
