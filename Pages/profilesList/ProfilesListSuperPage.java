package pages.rialto.profilesList;

import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.common.Button;
import util.components.rialto.RialtoTable;

public class ProfilesListSuperPage extends RialtoInternalPage {

	public ProfilesListSuperPage(MainPageManager pages) {
		super(pages);
	}

	public ProfilesListSuperPage ensurePageLoaded() {
		super.ensurePageLoaded();
		table.ensureTableLoaded();
		return this;
	}
	
	public ProfilesListSuperPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		table.ensureTableLoadedBasic();
		return this;
	}

	protected RialtoTable table = new RialtoTable(pages);
	protected Button edit = new Button(pages, "//button[contains(@class, 'ex-action-menu-item') and text() = 'Edit']", LocatorType.Xpath);
	protected Button duplicate = new Button(pages, "//button[contains(@class, 'ex-action-menu-item') and text() = 'Duplicate']", LocatorType.Xpath);
	protected Button remove = new Button(pages, "//button[contains(@class, 'ex-action-menu-item') and text() = 'Remove']", LocatorType.Xpath);
	protected Button removeSelected = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()=' Remove Selected ' and not(@disabled)]", LocatorType.Xpath);
	protected String bulkCheckbox ="//th//mat-checkbox//div[contains(@class, 'mat-checkbox-inner-container')]";
	protected String profileCheckbox = "//ex-link/a[contains(text(), '{0}')]/ancestor::tr//input[contains(@class, 'checkbox-input')]";
	protected String moreActions = "//ex-link/a[contains(text(), '{0}')]/ancestor::tr//div[contains(text(), '{1}')]/ancestor::tr//button[contains(@class, 'ex-action-menu-more-btn')]";
	private String profile = "//ex-link/a[contains(text(), '{0}')]/ancestor::tr//div[text() = ' {1} ']";

	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	protected void clickEditProfile(String name, String... description) {
		log.info("Edit profile: " + name);
		su.waitElementClickableAndClick(LocatorType.Xpath, moreActions, name, description.length > 0 ? description[0] : "");
		edit.waitElementClickableAndClickJS(); 
	}
	
	protected void clickDuplicateProfile(String name, String... description) {
		log.info("Duplicate profile: " + name);
		su.waitElementClickableAndClick(LocatorType.Xpath, moreActions, name, description.length > 0 ? description[0] : "");
		duplicate.waitElementClickableAndClickJS(); 
	}
	
	protected void clickRemoveProfile(String name, String... description) {
		log.info("Remove profile: " + name);
		su.waitElementClickableAndClick(LocatorType.Xpath, moreActions, name, description.length > 0 ? description[0] : "");
		remove.waitElementClickableAndClickJS();
	}

	protected void clickCheckbox(String name) {
		log.info("Click on the Profile's checkbox");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, profileCheckbox, name);
	}
	
	protected void clickBulkCheckbox() {
		log.info("Click on bulk checkbox");
		su.waitElementClickableAndClick(LocatorType.Xpath, bulkCheckbox);
	}
	
	protected void clickRemoveSelected() {
		log.info("Click on Remove Selected");
		if(removeSelected.isElementVisible())
			removeSelected.waitElementClickableAndClick();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	protected ProfilesListSuperPage verifyProfileExists(String name, String description, boolean expected) {
		log.info("Verify if the Profile '" + name + "' exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, profile, name, description), expected);
		return this;
	}
}
