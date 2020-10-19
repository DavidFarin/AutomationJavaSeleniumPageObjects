package pages.rialto;

import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.rialto.splitView.Offering_SplitView;
import util.interfaces.GeneralInterface.CodeNameable;
import models.rialto.RialtoItem;
import util.SelUtils.LocatorType;
import util.components.common.Button;


public class DDAPoolPage extends RialtoInternalPage {

	public DDAPoolPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public DDAPoolPage ensurePageLoaded() {
		ensurePageLoadedWithoutSplitView();
		ddaPoolSplitView.ensurePageLoaded();
		return this;
	}

	public DDAPoolPage ensurePageLoadedWithoutSplitView() { 
		super.ensurePageLoaded();
		ddaPoolList.ensurePageLoaded();
		return this;
	}

	public enum DDASearchOption implements CodeNameable {
		ISBN("ISBN", "isbn"),
		Author("Author", "author"),
		Title("Title", "title"),
		Keywords("Keywords", "keyword");

		private String name;
		private String code;

		DDASearchOption(String name, String code) {
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
	
	private OfferingListComponent ddaPoolList = new OfferingListComponent(pages);
	private Offering_SplitView ddaPoolSplitView = new Offering_SplitView(pages);
	
	private Button searchDropdown = new Button(pages, "//ex-extended-search//button[contains(@class, 'sel-ex-dropdown-selected-button')]", LocatorType.Xpath);
	private Button searchButton = new Button(pages, "//button[contains(@class, 'ex-search-search-button')]", LocatorType.Xpath);
	private Button clearSearch = new Button(pages, "//button[contains(@class, 'ex-search-clear-button')]", LocatorType.Xpath);	
	private String searchOption = "//button[@id='{0}']";
	private String searchTexbox = "//ex-search[@class='ex-extended-search-searchbox']//input";
	private String targetList = "//button[contains(@class, 'sel-record-actions-button')]//span[text()='{0}']";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public DDAPoolPage sortList(RialtoSort option) {
		super.sortList(option);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public DDAPoolPage ddaSearch(String searchTerm, DDASearchOption option) {
		log.info("Search in DDA page by " + option.getName());
		searchDropdown.waitElementClickableAndClick();
		su.getElementAndClickJS(LocatorType.Xpath, searchOption, option.getCode());
		su.clearAndSendKeys(searchTerm, LocatorType.Xpath, searchTexbox);	//NGS-1986
		searchButton.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public DDAPoolPage clearDDASearch() {
		log.info("Clear the DDA search");
		clearSearch.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public DDAPoolPage addItemToList(int row, String list) {
		log.info("Add item in row " + row + " to the list '" + list + "'");
		ddaPoolList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, addToList);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, list);
		return this.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public DDAPoolPage clickAddToCart(int row) {
		ddaPoolList.clickAddToCart(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public DDAPoolPage clickItem(int row) {
		ddaPoolList.clickItem(row);
		return this.ensurePageLoaded();
	}
	
	public DDAPoolPage clickListViewButton() {
		super.clickListViewButton();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public DDAPoolPage clickSplitViewButton() {
		super.clickSplitViewButton();
		return this.ensurePageLoaded();
	}
	
	public DDAPoolPage clickPreviousItem() {
		ddaPoolSplitView.clickPreviousItem();
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public DDAPoolPage verifyListSorted(RialtoSort option) {
		log.info("Verify the list is sorted by " + option.getName());
		ddaPoolList.verifyListSorted(option);
		return this;
	}
	
	public DDAPoolPage verifyItemFocused(int row, boolean expected) {
		ddaPoolList.verifyItemFocused(row, expected);
		return this;
	}
	
	public DDAPoolPage verifySplitViewDetails(int row) {
		log.info("Verify Split View details in row: " + row);
		GenUtils.verifyResult(ddaPoolList.getItem(row).basicEquals(ddaPoolSplitView.getItem()), true);
		return this;
	}
	
	public DDAPoolPage clickNextItem() {
		ddaPoolSplitView.clickNextItem();
		return this.ensurePageLoaded();
	}
	
	public DDAPoolPage verifySplitViewClosed() {
		ddaPoolSplitView.verifySplitViewClosed();
		return this;
	}
	
	public DDAPoolPage verifyItemInList(RialtoItem item, boolean expected, RialtoBadge... badges) {
		ddaPoolList.verifyItemInList(item, expected, badges);
		return this;
	}
	
	public DDAPoolPage verifyAddToCartButtonIsDisabled(int row, boolean expected) {
		ddaPoolList.verifyAddToCartButtonIsDisabled(row, expected);
		return this;
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		return ddaPoolList.getItem(row);
	}
	
	public int getRowOfItem(RialtoItem item, RialtoBadge... badges) {
		return ddaPoolList.getRowOfItem(item, badges);
	}
}
