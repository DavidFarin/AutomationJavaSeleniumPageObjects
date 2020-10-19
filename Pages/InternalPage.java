package pages.rialto;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.angular.AngDropdown;
import util.components.common.Button;
import util.components.rialto.RialtoDropdown;
import util.components.rialto.RialtoFacet;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.search.PersistenceSearch;
import util.enums.HotKeyData.HotKey;
import util.enums.SearchData.Market_Option;
import util.interfaces.GeneralInterface.CodeNameable;
import util.interfaces.GeneralInterface.Codeable;
import util.interfaces.GeneralInterface.Nameable;

public class RialtoInternalPage extends AnyPage {

	public RialtoInternalPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoInternalPage ensurePageLoaded() {
		waitLoadingBlocker(loadingBlocker);
		waitLoadingBlocker(shimmer);
		waitLoadingBlocker(panelLoadingBlocker);
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(progressBar)));
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.id("almaHeader")));      
		return this;
	}
	
	protected void waitLoadingBlocker(String loadingBlocker) {
		GenUtils.sleepMillis(500);
		if (su.isElementExist(LocatorType.Xpath, loadingBlocker))
			waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingBlocker)));
	}

	public enum RialtoBadge implements Nameable {
		InYourCart("In your cart"),
		InOthersCarts("In others\' cart"),
		InYourLists("In your lists"),
		InOthersLists("In others\' list"),
		InDDAPool("In dda pool"),
		AwaitingApproval("Awaiting approval"),
		Rejected("Rejected"),
		Processing("Processing"),
		PurchaseRequest("Purchase Request"),
		Rush("Rush"),
		NotYetPublished("Not yet published"),
		OutOfPrint("Out of print"),
		ElectronicMaterial("Electronic Material"),
		Downloadable("Downloadable"),
		DRMFree("DRM Free");

		private String name;

		RialtoBadge(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	public enum RialtoMessage implements Nameable {
		ItemAddedToYourCart("Item has been added to your cart"),
		ItemAddedToSelectorsCart("Item has been added to Selector's cart"),
		ItemRemovedFromYourCart("Item has been removed from your cart"),
		ItemAddedToList("Item has been added to your list"),
		ItemRemovedFromList("Item has been removed from your list"),
		MissingMandatoryInformation("Mandatory information is missing"),
		CheckoutFailedFundWithoutEnoughMoney("Fund %s does not have enough money for executing the desired transaction"),
		APrivateListWithThisNameAlreadyExist("A private list with this name already exists"),
		UpdatedUserProfileSuccessfully("Updated user profile successfully "),
		OfferingDismissed(" The offering \"%s\" was successfully dismissed."),
		OneOfferingPurged("%s offering was successfully removed."),
		MultipleOfferingsPurged("%s offerings were successfully removed."),
		ThereIsAlreadyATemplateWithTheSameNameAndLibrary("There is already a template with the same name or the selected owning library"),
		TemplateAppliedToAllItemsInCart("%s template applied to ALL items in your cart."),
		TemplateAppliedToSomeItemsInCart("%s template applied to %s items in your cart."),
		UserAlreadyChosen("User already chosen");

		private String name;

		RialtoMessage(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum RialtoAlert implements CodeNameable {
		Saved("Saved", "//span[contains(@class, 'sel-saved')]"), 
		Failed("Failed", "//span[contains(@class, 'sel-failed')]");

		private String name;
		private String code;

		RialtoAlert(String name, String code) {
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
	}

	public enum RialtoPaginationOption implements Codeable {
		TEN("10"), TWENTY("20"), FIFTY("50");

		private String code;

		private RialtoPaginationOption(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}

	public enum RialtoSort implements CodeNameable {
		PriceAsc("Price Asc", "Price"),
		PriceDesc("Price Desc", "Price"),
		PublicationYearAsc("Publication Year Asc", "Publication Year"),
		PublicationYearDesc("Publication Year Desc", "Publication Year"),
		TemplateCreationDateAsc("Creation date Asc", "Creation date"),
		TemplateCreationDateDesc("Creation date Desc", "Creation date"),
		TemplateNameAsc("Name Asc", "Name"),
		TemplateNameDesc("Name Desc", "Name"),;

		private String name, code;

		RialtoSort(String name, String code) {
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

	@FindBy(xpath = "//ex-dropdown[@id='ngSimpleSearchKeyButton']/button")
	protected WebElement searchTypeRialtoMode;

	@FindBy(id = "simpleSearchKeyDisplay")
	protected WebElement searchTypeAlmaMode;

	@FindBy(xpath = "//ex-dropdown[@id='ngSimpleSearchIndexes']/button")
	protected WebElement secondarySearchType;

	@FindBy(xpath = "//button[@id='MARKET']")
	protected WebElement marketSearchRialtoMode;

	@FindBy(xpath = "//li[@id = 'ADD_HIDERADIO_TOP_NAV_Search_input_MARKET']/a")
	protected WebElement marketSearchAlmaMode;

	@FindBy(xpath = "//ex-button[@id='ngPerformSearchBtn']//i")
	protected WebElement searchButton;

	@FindBy(css = ".persistent-search-search-box input")
	protected WebElement searchTextbox;

	protected RialtoFacet facet = new RialtoFacet(pages);
	protected RialtoDropdown dropdown = new RialtoDropdown(pages);
	protected OfferingListComponent list = new OfferingListComponent(pages);
	private PersistenceSearch search = new PersistenceSearch(pages);
	private AngDropdown sort = new AngDropdown(pages, "//ex-dropdown[contains(@class, 'sel-sort-list-dropdown')]/button", LocatorType.Xpath);
	protected Button backBtn = new Button(pages, "//button[contains(@class, 'sel-page-back')]", LocatorType.Xpath);
	private String ddaConfiguration = "//ex-floating-component[contains(@class, 'sel-dda-fund-and-location')]";
	protected Button cart = new Button(pages, "//button[@id='shoping_cart_btn']", LocatorType.Xpath);
	private String popUpMessage = "//div[contains(@class, 'sel-alert-message')]//span";
	protected String numberOfResults = "//span[contains(@class,'sel-pagination-summary')]";
	protected String progressBar = "//div[contains(@class, 'sel-ex-progress-bar')]";
	protected String loadingBlocker = "//mat-progress-spinner[contains(@class, 'sel-ex-spinner')]";
	protected String panelLoadingBlocker = "//div[contains(@class, 'sel-panel-trigger-spinner')]";
	protected String shimmer = "//div[contains(@class, 'sel-loading')]";
	protected String previousPageFixed = "//button[contains(@class, 'pagination-buttons-left-button')]";
	protected String paginationInputFixed = "//pagination-input//input";
	protected String nextPageFixed = "//button[contains(@class, 'pagination-buttons-right-button')]";
	protected String previousPageFloating = "//button[contains(@class, 'pagination-floating-left-button')]";
	protected String paginationInputFloating = "//div[contains(@class, 'pagination-floating-fixed-wrapper')]//input";
	protected String nextPageFloating = "//button[contains(@class, 'pagination-floating-right-button')]";
	protected String go = "//pagination-input//button";
	protected String sortByDropdown = "//ex-dropdown[contains(@class, 'sel-sort-list-dropdown')]/button";
	protected String sortByOption = "//button[@id='{0}']";
	protected String sortOrderOption = "//i[contains(@class, 'sel-sort-order-{0}')]";
	protected String listViewButton = "//button[contains(@class, 'sel-list-toggle-button')]";
	protected String splitViewButton = "//button[contains(@class, 'sel-split-toggle-button')]";
	protected String splitViewButton03 = "//button[contains(@class, 'sel-split-toggle-outer-button')]";		//URM-138519
	protected String itemsNumberInCart = "//button[@id='shoping_cart_btn']//span";
	protected String paginationScale = "//div[contains(@class, 'sel-scale-{0}')]//button"; 
	protected String selectedPaginationPage = "//button[contains(@class, 'pagination-buttons-selected-page')]//span[text()='{0}']";
	protected String addToList = "//button[text()=' Add to List ']";
	protected String moveToList = "//button[text()=' Move to List ']";
	protected String moveToCart = "//button[contains(@class, 'sel-record-actions-button')]//span[contains(@class, 'sel-value') and text() = 'Move to Cart']";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public MarketPage marketSearch(String searchTerm, Market_Option secondarySearch) {
		search.market(searchTerm, secondarySearch);
		return pages.rialto.marketPage.ensurePageLoaded();
	}

	public WorkPage marketIsbnSearch(String searchTerm) {
		search.market(searchTerm, Market_Option.ISBN);
		return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
	}
	
	public RialtoInternalPage selectPagination(RialtoPaginationOption paginationOption) {
		log.info("Select pagination to " + paginationOption);
		switch (paginationOption) {
			case TEN:
				su.waitElementClickableAndClick(LocatorType.Xpath, paginationScale, 0);
				break;
			case TWENTY:
				su.waitElementClickableAndClick(LocatorType.Xpath, paginationScale, 1);
				break;
			case FIFTY:
				su.waitElementClickableAndClick(LocatorType.Xpath, paginationScale, 2);
				break;
		}
		return this;
	}

	protected RialtoInternalPage jumpToPageFixed(String pageNum) {
		log.info("Jump to page " + pageNum + " - fixed pagination");
		su.clearAndSendKeys(pageNum, LocatorType.Xpath, paginationInputFixed);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, go);
		return this;
	}

	protected RialtoInternalPage jumpToPageFloating(String pageNum) {
		log.info("Jump to page " + pageNum + " - floating pagination");
		su.clearAndSendKeys(pageNum, LocatorType.Xpath, paginationInputFloating);
		su.pressKeyboard(HotKey.Enter);
		return this;
	}

	public RialtoInternalPage sortList(RialtoSort option) {
		log.info("Sort by " + option.getName());
		sort.setByClicking(option.getCode());
		pages.rialto.rialtoInternalPage.ensurePageLoaded();
		switch (option) {
			case PriceAsc:
			case PublicationYearAsc:
			case TemplateCreationDateAsc:
			case TemplateNameAsc:
				su.waitElementClickableAndClick(LocatorType.Xpath, sortOrderOption, "desc");
				break;
			case PriceDesc:
			case PublicationYearDesc:
			case TemplateCreationDateDesc:
			case TemplateNameDesc:
				su.waitElementClickableAndClick(LocatorType.Xpath, sortOrderOption, "asc");
				break;
		}
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	protected RialtoInternalPage clickNextPage() {
		log.info("Click next page - fixed pagination");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, nextPageFixed);
		return this;
	}
	
	protected RialtoInternalPage clickPreviousPage() {
		log.info("Click previous page - fixed pagination");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, previousPageFixed);
		return this;
	}

	protected RialtoInternalPage clickNextPageFloating() {
		log.info("Click next page - floating pagination");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, nextPageFloating);
		return this;
	}

	protected RialtoInternalPage clickPreviousPageFloating() {
		log.info("Click previous page - floating pagination");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, previousPageFloating);
		return this;
	}

	public CartPage clickCart() {
		log.info("Click on cart button");
		cart.waitElementClickableAndClick();
		return pages.rialto.cartPage.ensurePageLoadedWithoutSplitView();
	}

	public RialtoInternalPage applyFacetIfExists(FacetGroup group, String facetName) {
		facet.clickMore(group);
		facet.applyFacetIfExists(group, facetName);
		return this;
	}
	
	public RialtoInternalPage clickApply() {
		facet.clickApply();
		return this;
	}
	
	public RialtoInternalPage clickFacetCheckboxIfExists(FacetGroup group, String facetName) {
		facet.clickMore(group);
		facet.clickFacetCheckboxIfExists(group, facetName);
		return this;
	}
	
	public RialtoInternalPage hideFacets() {
		facet.clickHideFacetsIfVisible();
		return this;
	}

	public RialtoInternalPage showFacets() {
		facet.clickShowFacetsIfNotVisible();
		return this;
	}
	
	public RialtoInternalPage removeSelectedFacet(String facetName) {
		facet.removeSelectedFacet(facetName);
		return this;
	}
	
	public RialtoInternalPage clearAllFacets() {
		facet.clickClearAll();
		return this;
	}
	
	public RialtoInternalPage clickClear() {
		facet.clickClear();
		return this;
	}

	public RialtoInternalPage clickListViewButton() {
		log.info("Click on List View button");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, listViewButton);
		return this;
	}

	public RialtoInternalPage clickSplitViewButton() {
		log.info("Click on Split View button");
		if(pages.getSessionParams().getCurrentEnvironment().equals("QAC01"))
			su.waitElementClickableAndClick(LocatorType.Xpath, splitViewButton);		//URM-138519
		else
			su.waitElementClickableAndClick(LocatorType.Xpath, splitViewButton03);		//URM-138519
		return this;
	}
	
	protected RialtoInternalPage clickBack() {
		log.info("Click on 'Back' button");
		backBtn.moveToElement();
		backBtn.waitElementClickableAndClickJS();
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoInternalPage verifyFacetApplied(String facetName, boolean expected) {
		facet.verifyFacetApplied(facetName, expected);
		return this;
	}
	
	public RialtoInternalPage verifyFacetCheckboxSelected(FacetGroup group, String facetName, boolean expected) {
		facet.verifyFacetCheckboxSelected(group, facetName, expected);
		return this;
	}
	
	public RialtoInternalPage verifyFacetPanelVisible(boolean expected) {
		facet.verifyFacetPanelVisible(expected);
		return this;
	}

	public RialtoInternalPage verifyMarketSearchExists() {
		log.info("Verify Market search option exists");
		if (su.isElementExist(searchTypeAlmaMode)) {
			su.waitElementClickableAndClick(searchTypeAlmaMode);
			su.waitElementClickableAndClickJS(marketSearchAlmaMode);
		}
		su.waitElementClickableAndClick(searchTypeRialtoMode);
		su.getElementAndClickJS(marketSearchRialtoMode);
		return this.ensurePageLoaded();
	}

	public RialtoInternalPage verifyMarketOptionNotExists() {
		su.waitElementClickableAndClickJS(searchTypeAlmaMode);
		GenUtils.verifyResult(su.isElementExist(marketSearchRialtoMode), false);
		searchTypeAlmaMode.click();
		return this.ensurePageLoaded();
	}

	public RialtoInternalPage verifyCartExist(boolean expected) {
		log.info("Verify Cart");
		GenUtils.verifyResult(cart.exist(), expected);
		return this;
	}

	protected RialtoInternalPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		log.info("Verify popup message");
		String expected;
		switch (placeHolders.length) {
		case 2:
			expected = String.format(expectedMessage.getName(), placeHolders[0], placeHolders[1]);
			break;
		case 1:
			expected = String.format(expectedMessage.getName(), placeHolders[0]);
			break;
		default:
			expected = expectedMessage.getName();
			break;
		}
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(popUpMessage)));
		GenUtils.verifyResult(su.getElementAndGetTextJS(LocatorType.Xpath, popUpMessage), expected);
		waitMedium.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(popUpMessage)));
		return this;
	}

	protected RialtoInternalPage verifyCurrentPageNumber(int expected) {
		log.info("Verify current page number");
		String currentPage = getRangeOfPages().split(" of ")[0].replaceAll(",", "");
		GenUtils.verifyResult(currentPage, String.valueOf(expected));
		return this;
	}

	protected RialtoInternalPage verifyItemsNumberInCart(int expected) {
		log.info("Verify the current number in the cart icon is " + expected);
		GenUtils.verifyResult(getItemsNumberInCartIcon(), expected);
		return this;
	}
	
	public RialtoInternalPage verifyNumberOfResults(int expected) {
		log.info("Verify the number of results is " + expected);
		GenUtils.verifyResult(getNumberOfResults(), expected);
		return this;
	}
	
	public RialtoInternalPage verifySelectedPaginationPage(int expected) {
		log.info("Verify the current page in the pagination is: " + expected);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, selectedPaginationPage, expected), true);
		return this;
	}
	
	public RialtoInternalPage verifyDdaConfigurationExists(boolean expected) {
		log.info("Verify if the 'DDA configuration' exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ddaConfiguration), expected);
		return this;
	}
	
	public RialtoInternalPage verifyAddToDdaInMoreActionsExists(int row, boolean expected) {
		list.clickMoreActions(row);
		list.verifyAddToDdaInMoreActionsExists(expected);
		su.clickKeyboard(Keys.ESCAPE);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public int getNumberOfPages() {
		int numberOfPages = 1;
		if (su.isElementExist(LocatorType.Xpath, paginationInputFixed)) {
			String lastPageTextValue = getRangeOfPages().split(" of ")[1].replaceAll(",", "");
			numberOfPages = Integer.parseInt(lastPageTextValue);
		}
		log.info("The number of pages is " + numberOfPages);
		return numberOfPages;
	}

	public String getRangeOfPages() {
		log.info("Get range of pages");
		return su.getElement(LocatorType.Xpath, paginationInputFixed).getAttribute("placeholder");
	}
	
	public int getNumberOfResults() {
		log.info("Get number of results");
		return Integer.parseInt(su.getElementAndGetText(LocatorType.Xpath, numberOfResults).split(" of ")[1].replaceAll("\\D+", ""));
	}
	
	public int getItemsNumberInCartIcon() {
		String numberOfItems = su.getElementAndGetText(LocatorType.Xpath, itemsNumberInCart);
		log.info("The number of items in the cart's icon is: " + numberOfItems);
		return Integer.parseInt(numberOfItems);
	}
	
	public ArrayList<String> getVisibleFacetsInGroup(FacetGroup group) {
		return facet.getVisibleFacetsInGroup(group);
	}
}