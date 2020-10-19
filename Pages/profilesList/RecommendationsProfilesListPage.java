package pages.rialto.profilesList;

import pages._pages_mngt.MainPageManager;
import pages.rialto.editProfile.EditRecommendationsProfilePage;
import util.SelUtils.LocatorType;
import util.components.common.Button;

public class RecommendationsProfilesListPage extends ProfilesListSuperPage {

	public RecommendationsProfilesListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RecommendationsProfilesListPage ensurePageLoaded() {
		super.ensurePageLoaded();
		addRecommendationsProfile.waitElementClickable();
		return this;
	}
	
	@Override
	public RecommendationsProfilesListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		addRecommendationsProfile.waitElementClickable();
		return this;
	}

	private Button addRecommendationsProfile = new Button(pages, "//button[text()=' Add Recommendations Profile ']", LocatorType.Xpath);


	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRecommendationsProfilePage addRecommendationsProfile() {
		log.info("Add Recommendation Profile");
		addRecommendationsProfile.waitElementClickableAndClick();
		return pages.rialto.editRecommendationsProfilePage.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage editProfile(String name, String... description) {
		super.clickEditProfile(name, description);
		return pages.rialto.editRecommendationsProfilePage.ensurePageLoadedEmptyList();
	}
	
	public EditRecommendationsProfilePage duplicateProfile(String name) {
		super.clickDuplicateProfile(name);
		return pages.rialto.editRecommendationsProfilePage.ensurePageLoadedEmptyList();
	}
	
	public RecommendationsProfilesListPage removeProfile(String name, String... description) {
		super.clickRemoveProfile(name, description);
		return this.ensurePageLoadedEmptyList();
	}
	
	public RecommendationsProfilesListPage clickProfileCheckbox(String name) {
		super.clickCheckbox(name);
		return this.ensurePageLoaded();
	}
	
	public RecommendationsProfilesListPage clickProfileBulkCheckbox() {
		super.clickBulkCheckbox();
		return this.ensurePageLoadedEmptyList();
	}
	
	public RecommendationsProfilesListPage removeSelected() {
		super.clickRemoveSelected();
		return this.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public RecommendationsProfilesListPage verifyProfileExists(String name, String description, boolean expected) {
		super.verifyProfileExists(name, description, expected);
		return this;
	}
}
