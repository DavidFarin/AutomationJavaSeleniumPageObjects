package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.MarketListComponent;
import util.exceptions.RialtoItemNotFoundException;


public class MarketPage extends RialtoInternalPage {


	public MarketPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public MarketPage ensurePageLoaded() {
		super.ensurePageLoaded();
		try {
			marketPageList.ensurePageLoaded();
		} catch (Exception e) {
			//if Market Page is empty
			waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(noSearchResults)));

		}
		return this;
	}
	
	public enum WorkFormats {
		ElectronicBook("Electronic"),
		PrintBook("Print"),
		Other("Other"),;

		private String name;

		WorkFormats(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	};

	private MarketListComponent marketPageList = new MarketListComponent(pages);
	private String noSearchResults = "//alma-icon[contains(@class, 'sel-no-search-results-icon')]";


	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public MarketPage jumpToPageFixed(String pageNum) {
		super.jumpToPageFixed(pageNum);
		return this.ensurePageLoaded();
	}

	@Override
	public MarketPage jumpToPageFloating(String pageNum) {
		super.jumpToPageFloating(pageNum);
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public WorkPage clickViewAllOffers(int row) {
		marketPageList.clickViewAllOffers(row);
		return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
	}

	public WorkPage clickWorkTitleIfExist(String workTitle) throws RialtoItemNotFoundException {
		log.info("Click on work: " + workTitle + " if exist");
		int numberOfPages = getNumberOfPages();
		for (int i = 1; i <= numberOfPages; i++) {
			if (marketPageList.isWorkTitleExist(workTitle)) {
				marketPageList.clickWorkTitle(workTitle);
				return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
			}
			else if (i < numberOfPages) {
				clickNextPage();
				this.ensurePageLoaded();
			}
		}
		throw new RialtoItemNotFoundException("Rialto work '" + workTitle + "' not found");
	}


	@Override
	public MarketPage applyFacetIfExists(FacetGroup facetGroupName, String facetName) {
		super.applyFacetIfExists(facetGroupName, facetName);
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage clickApply() {
		super.clickApply();
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage clickFacetCheckboxIfExists(FacetGroup facetGroupName, String facetName) {
		super.clickFacetCheckboxIfExists(facetGroupName, facetName);
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage hideFacets() {
		super.hideFacets();
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage showFacets() {
		super.showFacets();
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage removeSelectedFacet(String facetName) {
		super.removeSelectedFacet(facetName);
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage clearAllFacets() {
		super.clearAllFacets();
		return this.ensurePageLoaded();
	}
	
	@Override
	public MarketPage clickClear() {
		super.clickClear();
		return this.ensurePageLoaded();
	}
	
	public MarketPage selectPagination(RialtoPaginationOption paginationOption) {
		super.selectPagination(paginationOption);
		return this.ensurePageLoaded();
	}

	public MarketPage clickNextPage() {
		super.clickNextPage();
		return this.ensurePageLoaded();
	}

	public MarketPage clickPreviousPage() {
		super.clickPreviousPage();
		return this.ensurePageLoaded();
	}

	public MarketPage clickNextPageFloating() {
		super.clickNextPageFloating();
		return this.ensurePageLoaded();
	}

	public MarketPage clickPreviousPageFloating() {
		super.clickPreviousPageFloating();
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public MarketPage verifyFacetApplied(String facetName, boolean expected) {
		super.verifyFacetApplied(facetName, expected);
		return this;
	}
	
	@Override
	public MarketPage verifyFacetCheckboxSelected(FacetGroup group, String facetName, boolean expected) {
		super.verifyFacetCheckboxSelected(group, facetName, expected);
		return this;
	}
	
	@Override
	public MarketPage verifyFacetPanelVisible(boolean expected) {
		super.verifyFacetPanelVisible(expected);
		return this;
	}

	@Override
	public MarketPage verifyCurrentPageNumber(int page) {
		super.verifyCurrentPageNumber(page);
		return this;
	}

	public MarketPage verifyNoSearchResults() {
		log.info("Verify error picture exist");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, noSearchResults), true);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public int getListLength() {
		return marketPageList.getPageSize();
	}
	
	public int getFacetAmount(FacetGroup group, String facetName) {
		return facet.getFacetAmount(group, facetName);
	}
}
