package pages.rialto.editProfile;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.rialto.profilesList.DDAProfilesListPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.common.Button;

public class EditDDAProfilePage extends EditProfileSuperPage {

	public EditDDAProfilePage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public EditDDAProfilePage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(profileType)));
		return this;
	}
	
	@Override
	public EditDDAProfilePage ensurePageLoadedEmptyList() {
		super.ensurePageLoadedEmptyList();
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(profileType)));
		return this;
	}
	
	private Button chooseOwningLibrary = new Button(pages, "//div[contains(@class, 'sel-edit-profile-location')]//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private Button chooseFund = new Button(pages, "//div[contains(@class, 'sel-edit-profile-fund')]//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private String profileType = "//div[contains(@class, 'edit-profile-info-profile-type')]//div[text()='DDA']";
	private String editCap = "//div[contains(@class, 'edit-profile-info-cap')]//input";
	private String libraryWarning = "//div[contains(@class, 'sel-edit-profile-location') and contains(@class, 'not-valid-border-animation')]";
	private String capWarning = "//input[contains(@class, 'not-valid-border-animation')]";

	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditDDAProfilePage editName(String name) {
		super.editName(name);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage editDescription(String description) {
		super.editDescription(description);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage selectRanking(String name) {
		super.selectRanking(name);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage chooseOwningLibrary(String library) {
		log.info("Choose Owning Library: " + library);
		chooseOwningLibrary.waitElementClickableAndClick();
		dropdown.setText(library);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage chooseFund(String fund) {
		log.info("Choose Fund: " + fund);
		chooseFund.waitElementClickableAndClick();
		dropdown.setText(fund);
		return this.ensurePageLoadedEmptyList();
	}
	
	public EditDDAProfilePage editCap(String cap) {
		log.info("Edit Cap to: " + cap);
		su.clearAndSendKeys(cap, LocatorType.Xpath, editCap);
		return this.ensurePageLoadedEmptyList();
	}
	
	public DDAProfilesListPage saveAndReturnToDDAProfilesListPage() {
		log.info("Save profile and return to the DDA Profile List page");
		save.waitElementClickableAndClick();
		return pages.rialto.ddaProfilesListPage.ensurePageLoaded();	
	}
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	@Override
	public EditDDAProfilePage verifyNameWarningExists(boolean expected) {
		super.verifyNameWarningExists(expected);
		return this;
	}
	
	@Override
	public EditDDAProfilePage verifyRankingWarningExists(boolean expected) {
		super.verifyRankingWarningExists(expected);
		return this;
	}
	
	public EditDDAProfilePage verifyLibraryWarningExists(boolean expected) {
		log.info("Verify if Library warning exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, libraryWarning), expected);
		return this;
	}
	
	public EditDDAProfilePage verifyCapWarningExists(boolean expected) {
		log.info("Verify if Cap warning exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, capWarning), expected);
		return this;
	}
	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditDDAProfilePage clickSave() {
		super.clickSave();
		return this.ensurePageLoadedEmptyList();
	}
}
