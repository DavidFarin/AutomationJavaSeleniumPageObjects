package pages.rialto.editProfile;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.rialto.FeedsPage;
import util.SelUtils.LocatorType;
import pages.rialto.profilesList.RecommendationsProfilesListPage;

public class EditRecommendationsProfilePage extends EditProfileSuperPage {

	public EditRecommendationsProfilePage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public EditRecommendationsProfilePage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(profileType)));
		return this;
	}
	
	@Override
	public EditRecommendationsProfilePage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(profileType)));
		return this;
	}
	
	public EditRecommendationsProfilePage ensurePageLoadedWithoutDetailsPanel() {
		super.ensurePageLoaded();
		return this;
	}

	private String profileType = "//div[contains(@class, 'edit-profile-info-profile-type')]//div[text()='Recommendation']";
	private String selectedRanking = "//div[contains(@class, 'sel-edit-profile-ranking')]//span[text() = '{0}']";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRecommendationsProfilePage editName(String name) {
		super.editName(name);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage editDescription(String description) {
		super.editDescription(description);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage setCategoryInRow(String queryId, QueryCategoryOption category) {
		super.setCategoryInRow(queryId, category);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage setOperatorInRow(String queryId, QueryOperatorOption operator) {
		super.setOperatorInRow(queryId, operator);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage setValueInRow(String queryId, QueryValueOption option, Object... value) {
		super.setValueInRow(queryId, option, value);
		return this.ensurePageLoadedEmptyList();
	}

	public EditRecommendationsProfilePage runQueryInGroup(String groupId) {
		super.runQueryInGroup(groupId);
		return this.ensurePageLoadedWithoutDetailsPanel();
	}
	
	public EditRecommendationsProfilePage addRule(String queryId) {
		super.addRule(queryId);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage closeQueryTestResults() {
		super.closeQueryTestResults();
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage selectRanking(String name) {
		super.selectRanking(name);
		return this.ensurePageLoadedEmptyList();
	}
	
	public RecommendationsProfilesListPage saveAndReturnToRecommendationsProfilesListPage() {
		log.info("Save profile and return to the Recommendations Profile List page");
		save.waitElementClickableAndClick();
		return pages.rialto.recommendationsProfilesListPage.ensurePageLoaded();	
	}	
	
	public FeedsPage saveAndReturnToFeedsPage() {
		log.info("Save profile and return to the Feeds page");
		save.waitElementClickableAndClick();
		return pages.rialto.feedsPage.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRecommendationsProfilePage clickSave() {
		super.clickSave();
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage clickItem(int row) {
		super.clickItem(row);
		return this.ensurePageLoadedWithoutDetailsPanel();
	}

	public EditRecommendationsProfilePage clickPreviousItem() {
		super.clickPreviousItem();
		return this.ensurePageLoadedWithoutDetailsPanel();
	}

	public EditRecommendationsProfilePage clickNextItem() {
		super.clickNextItem();
		return this.ensurePageLoadedWithoutDetailsPanel();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRecommendationsProfilePage verifyQueryTestResults(QueryCategoryOption category, int numberOfItems, String expectedValue) {
		super.verifyQueryTestResults(category, numberOfItems, expectedValue);
		return this;
	}
	
	public EditRecommendationsProfilePage verifySelectedRanking(String expected) {
		log.info("Verify the selected ranking is: " + expected);
		su.verifyElementExist(LocatorType.Xpath, selectedRanking, expected);
		return this;
	}
	
	@Override
	public EditRecommendationsProfilePage verifyNameWarningExists(boolean expected) {
		super.verifyNameWarningExists(expected);
		return this;
	}
	
	@Override
	public EditRecommendationsProfilePage verifyRankingWarningExists(boolean expected) {
		super.verifyRankingWarningExists(expected);
		return this;
	}

	@Override
	public EditRecommendationsProfilePage verifyItemFocused(int row, boolean expected) {
		super.verifyItemFocused(row, expected);
		return this;
	}

	@Override
	public EditRecommendationsProfilePage verifySplitViewDetails(int row) {
		super.verifySplitViewDetails(row);
		return this;
	}

}
