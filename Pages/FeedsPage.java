package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.rialto.editProfile.EditRecommendationsProfilePage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.angular.AngDropdown;
import util.components.common.Button;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.rialto.rialtoPanel.FeedsPanel;
import util.components.rialto.splitView.Offering_SplitView;
import util.interfaces.GeneralInterface.Nameable;


public class FeedsPage extends RialtoInternalPage {


	public FeedsPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public FeedsPage ensurePageLoaded() {
		ensurePageLoadedWithoutSplitView();
		feedsPageSplitView.ensurePageLoaded();
		return this;
	}

	public FeedsPage ensurePageLoadedWithoutSplitView() {
		super.ensurePageLoaded();
		feedsPageList.ensurePageLoaded();
		return this;
	}
	
	public enum DismissReason implements Nameable {
		NonPreferedPublisher("Non-preferred publisher "),
		PreferAlternateFormat("Prefer alternate format ");

		private String name;

		DismissReason(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	private OfferingListComponent feedsPageList = new OfferingListComponent(pages);
	private Offering_SplitView feedsPageSplitView = new Offering_SplitView(pages);
	private FeedsPanel feedsPanel = new FeedsPanel(pages);
	
	private String selectRanking = "//span[contains(text(),'Ranking')]/..//ex-dropdown[contains(@class, 'sel-sort-list-dropdown')]";	//NGS-2983
	private String selectedRanking = selectRanking + "//span[contains(text(), '{0}')]";
	private String rankingOption = "//button[contains(@class, 'ex-dropdown-item')]//span[text()='{0}']";
	private AngDropdown profileDropdown = new AngDropdown(pages, "//ex-combobox[contains(@class, 'sel-profile-list')]//button", LocatorType.Xpath);
	private Button saveChanges = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text() = ' Save Changes']", LocatorType.Xpath);
	private String editName = "//alma-page-column[contains(@class, 'sel-feed-summary-section')]//input[contains(@class, 'sel-input')]";
	private String editDescription = "//alma-page-column[contains(@class, 'sel-feed-summary-section')]//textarea[contains(@class, 'sel-textarea')]";
	private String profileDescriptionLabel = "//span[contains(@class, 'sel-ex-chip-recommendation')]";
	private String targetList = "//button[contains(@class, 'sel-record-actions-button')]//span[text()='{0}']";
	private String dismiss = "//button[contains(@class, 'sel-record-actions-button')]//div[text()=' Dismiss ']";
	private String dismissOption = dismiss + "/parent::button//span[contains(@class, 'menu-item-arrow-menu')]";		//NGS-3049
	private String dismissReason = "//div[text() = '{0}']/parent::label//div[contains(@class, 'mat-radio-outer-circle')]";		//NGS-3049
	private String purge = "//button[contains(@class, 'sel-ex-button') and contains(text(), ' Purge dismissed offers {0}')]";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public FeedsPage saveChanges() {
		log.info("Click on Save Changes button");
		saveChanges.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage clickItem(int row) {
		feedsPageList.clickItem(row);
		feedsPageSplitView.ensurePageLoaded();
		return this.ensurePageLoaded();
	}
	
	public FeedsPage clickCloseSplitViewButton() {
		feedsPageSplitView.closeSplitView();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage clickPreviousItem() {
		feedsPageSplitView.clickPreviousItem();
		return this.ensurePageLoaded();
	}
	
	public FeedsPage clickNextItem() {
		feedsPageSplitView.clickNextItem();
		return this.ensurePageLoaded();
	}

	public FeedsPage addToCart(int row) {
		feedsPageList.clickAddToCart(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage addItemToList(int row, String list) {
		log.info("Add item in row " + row + " to the list '" + list + "'");
		feedsPageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, addToList);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, list);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage dismissItemFromList(int row) {
		log.info("Dismiss item in row - " + row);
		feedsPageList.clickMoreActions(row);
		su.waitElementClickableAndClick(LocatorType.Xpath, dismiss);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage dismissItemFromSplitView() {
		log.info("Dismiss item in the Split View");
		feedsPageSplitView.clickMoreActions();
		su.waitElementClickableAndClick(LocatorType.Xpath, dismiss);
		return this.ensurePageLoaded();
	}
	
	public FeedsPage dismissItemByReasonFromList(int row, DismissReason reason) {
		log.info("Dismiss item in row - " + row + " by reason: " + reason.getName());
		feedsPageList.clickMoreActions(row);
		su.waitElementClickableAndClick(LocatorType.Xpath, dismissOption);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, dismissReason, reason.getName());
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage dismissItemByReasonSplitView(DismissReason reason) {
		log.info("Dismiss item in Split View by reason: " + reason.getName());
		feedsPageSplitView.clickMoreActions();
		su.waitElementClickableAndClick(LocatorType.Xpath, dismissOption);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, dismissReason, reason.getName());
		return this.ensurePageLoaded();
	}
	
	public FeedsPage clickRestore() {
		feedsPageSplitView.clickRestore();
		return this.ensurePageLoaded();
	}
	
	public FeedsPage purgeDismissedItems(int numOfItems) {
		log.info("Purge dismissed items");
		su.waitElementClickableAndClick(LocatorType.Xpath, purge, "");
		verifyMsgAppears(numOfItems == 1 ? RialtoMessage.OneOfferingPurged : RialtoMessage.MultipleOfferingsPurged, String.valueOf(numOfItems));
		waitVeryBig.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(shimmer)));
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage selectProfileFromDropdown(String profileName) {
		log.info("Select the profile " + profileName + " in the dropdown");
		profileDropdown.setBySendingKeys(profileName);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage selectRanking(String ranking) {
		log.info("Select the ranking: " + ranking);
		su.waitElementClickableAndClick(LocatorType.Xpath, selectRanking);
		su.waitBigElementClickable(LocatorType.Xpath, rankingOption, ranking);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, rankingOption, ranking);
		waitVeryBig.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(shimmer)));
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage showFacets() {
		super.showFacets();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage showFeedPanel() {
		feedsPanel.showFeedPanel();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage closePanelIfVisible() {
		feedsPanel.closePanelIfVisible();
		return this.ensurePageLoadedWithoutSplitView();
	}

	@Override
	public FeedsPage applyFacetIfExists(FacetGroup group, String facet) {
		super.applyFacetIfExists(group, facet);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public FeedsPage clickFacetCheckboxIfExists(FacetGroup facetGroupName, String facetName) {
		super.clickFacetCheckboxIfExists(facetGroupName, facetName);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public FeedsPage clickApply() {
		super.clickApply();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public FeedsPage clearAllFacets() {
		super.clearAllFacets();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	@Override
	public FeedsPage clickClear() {
		super.clickClear();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage editName(String name) {
		log.info("Edit Profile Name to: '" + name + "'");
		su.getElementAndClick(LocatorType.Xpath, editName);
		su.clearAndSendKeys(name, LocatorType.Xpath, editName);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage editDescription(String description) {
		log.info("Edit Profile Description to: '" + description + "'");
		su.waitElementClickableAndClick(LocatorType.Xpath, editDescription);
		su.clearAndSendKeys(description, LocatorType.Xpath, editDescription);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public EditRecommendationsProfilePage clickEditFeed() {
		feedsPanel.clickEditFeed();
		return pages.rialto.editRecommendationsProfilePage.ensurePageLoadedEmptyList();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public FeedsPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		super.verifyMsgAppears(expectedMessage, placeHolders);
		return this;
	}
	
	public FeedsPage verifyNumberOfResults(int expected) {
		super.verifyNumberOfResults(expected);
		return this;
	}
	
	public FeedsPage verifyItemInList(RialtoItem item, boolean expected, RialtoBadge... badges) {
		feedsPageList.verifyItemInList(item, expected, badges);
		return this;
	}
	
	public FeedsPage verifyItemDisabled(int row, boolean expected) {
		feedsPageList.verifyItemDisabled(row, expected);
		return this;
	}
	
	public FeedsPage verifySelectedProfileInDropdown(String profileName, boolean expected) {
		log.info("Verify the selected profile in the Profile Dropdown");		
		profileDropdown.verifyOptionSelected(profileName, expected);
		return this.ensurePageLoadedWithoutSplitView();
	}

	@Override
	public FeedsPage verifyFacetApplied(String facetName, boolean expected) {
		super.verifyFacetApplied(facetName, expected);
		return this;
	}
	
	@Override
	public FeedsPage verifyFacetCheckboxSelected(FacetGroup group, String facetName, boolean expected) {
		super.verifyFacetCheckboxSelected(group, facetName, expected);
		return this;
	}
	
	public FeedsPage verifySelectedRanking(String name) {
		log.info("Verify the selected Ranking is: " + name);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, selectedRanking, name), true);
		return this;
	}
	
	public FeedsPage verifyPurgeButtonExists(int numberOfDismissedItems, boolean expected) {
		log.info("Verify if the 'Purge dismissed items' buttons exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, purge, expected ? "(" + numberOfDismissedItems + ")" : ""), expected);
		return this;
	}
	
	public FeedsPage verifyDismissedOfferNotificationExists() {
		feedsPageSplitView.verifyDismissedOfferNotificationExists();
		return this;
	}
	
	public FeedsPage verifySplitViewDetails(int row) {
		log.info("Verify Split View details in row: " + row);
		GenUtils.verifyResult(feedsPageList.getItem(row).deepEquals(feedsPageSplitView.getItem()), true);
		return this;
	}
	
	public FeedsPage verifyEditLinkExistsInFeedPanel(boolean expected) {
		feedsPanel.verifyEditFeedLinkExists(expected);
		return this;
	}
	
	public FeedsPage verifyRuleInQueryExists(String category, String operator, String value, boolean expected) {
		feedsPanel.verifyRuleInQueryExists(category, operator, value, expected);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public FeedsPage verifyDdaConfigurationExists(boolean expected) {
		super.verifyDdaConfigurationExists(expected);
		return this;
	}
	
	public FeedsPage verifyAddToDdaInMoreActionsExists(int row, boolean expected) {
		super.verifyAddToDdaInMoreActionsExists(row, expected);
		return this;
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		return feedsPageList.getItem(row);
	}
}