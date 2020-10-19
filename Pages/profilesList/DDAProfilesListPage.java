package pages.rialto.profilesList;

import pages._pages_mngt.MainPageManager;
import pages.rialto.editProfile.EditDDAProfilePage;
import util.SelUtils.LocatorType;
import util.components.common.Button;


public class DDAProfilesListPage extends ProfilesListSuperPage {

	public DDAProfilesListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public DDAProfilesListPage ensurePageLoaded() {
		super.ensurePageLoaded();
		addDDAProfile.waitElementClickable();
		return this;
	}
	
	@Override
	public DDAProfilesListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		addDDAProfile.waitElementClickable();
		return this;
	}

	private Button addDDAProfile = new Button(pages, "//button[text()=' Add DDA Profile ']", LocatorType.Xpath);
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditDDAProfilePage addDDAProfile() {
		log.info("Add DDA Profile");
		addDDAProfile.waitElementClickableAndClick();
		return pages.rialto.editDDAProfilePage.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage editProfile(String name, String... description) {
		super.clickEditProfile(name, description);
		return pages.rialto.editDDAProfilePage.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage duplicateProfile(String name) {
		super.clickDuplicateProfile(name);
		return pages.rialto.editDDAProfilePage.ensurePageLoadedEmptyList();
	}
	
	public DDAProfilesListPage removeProfile(String name, String... description) {
		super.clickRemoveProfile(name, description);
		return this.ensurePageLoadedEmptyList();
	}
	
	public DDAProfilesListPage clickProfileCheckbox(String name) {
		super.clickCheckbox(name);
		return this.ensurePageLoaded();
	}
	
	public DDAProfilesListPage clickProfileBulkCheckbox() {
		super.clickBulkCheckbox();
		return this.ensurePageLoadedEmptyList();
	}
	
	public DDAProfilesListPage removeSelected() {
		super.clickRemoveSelected();
		return this.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public DDAProfilesListPage verifyProfileExists(String name, String description, boolean expected) {
		super.verifyProfileExists(name, description, expected);
		return this;
	}
}
