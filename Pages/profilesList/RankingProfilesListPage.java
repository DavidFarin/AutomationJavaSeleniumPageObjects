package pages.rialto.profilesList;

import pages._pages_mngt.MainPageManager;
import pages.rialto.editProfile.EditRankingProfilePage;
import util.SelUtils.LocatorType;
import util.components.common.Button;

public class RankingProfilesListPage extends ProfilesListSuperPage {

	public RankingProfilesListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RankingProfilesListPage ensurePageLoaded() {
		super.ensurePageLoaded();
		addRanking.waitElementClickable();
		return this;
	}

	@Override
	public RankingProfilesListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		addRanking.waitElementClickable();
		return this;
	}
	
	private Button addRanking = new Button(pages, "//button[text()=' Add Ranking  ']", LocatorType.Xpath);


	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRankingProfilePage addRanking() {
		log.info("Add Ranking");
		addRanking.click();		
		return pages.rialto.editRankingProfilePage.ensurePageLoaded();
	}
	
	public EditRankingProfilePage editProfile(String name, String... description) {
		super.clickEditProfile(name, description);
		return pages.rialto.editRankingProfilePage.ensurePageLoaded();
	}
	
	public EditRankingProfilePage duplicateProfile(String name) {
		super.clickDuplicateProfile(name);
		return pages.rialto.editRankingProfilePage.ensurePageLoaded();
	}
	
	public RankingProfilesListPage removeProfile(String name, String... description) {
		super.clickRemoveProfile(name, description);
		return this.ensurePageLoadedEmptyList();
	}
	
	public RankingProfilesListPage clickProfileCheckbox(String name) {
		super.clickCheckbox(name);
		return this.ensurePageLoaded();
	}
	
	public RankingProfilesListPage clickProfileBulkCheckbox() {
		super.clickBulkCheckbox();
		return this.ensurePageLoadedEmptyList();
	}
	
	public RankingProfilesListPage removeSelected() {
		super.clickRemoveSelected();
		return this.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public RankingProfilesListPage verifyRankingProfileExists(String name, String description, boolean expected) {
		super.verifyProfileExists(name, description, expected);
		return this;
	}
}
