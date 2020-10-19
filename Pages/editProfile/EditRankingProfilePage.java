package pages.rialto.editProfile;

import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage;
import pages.rialto.profilesList.RankingProfilesListPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.common.Button;
import util.interfaces.GeneralInterface.Codeable;
import util.interfaces.GeneralInterface.Nameable;

public class EditRankingProfilePage extends RialtoInternalPage {

	public EditRankingProfilePage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public EditRankingProfilePage ensurePageLoaded() {
		super.ensurePageLoaded();
		addBoost.waitElementClickable();
		return this;
	}
	
	public enum RankingCriteriaOption implements Nameable {
		LowestPrice("Lowest price"),
		TotalSales("Total sales");

		private String name;

		private RankingCriteriaOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
	
	public enum RankingWeigthOption implements Codeable {
		Low("0"),
		Med("1"),
		High("2");

		private String code;

		private RankingWeigthOption(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
	
	private Button addBoost = new Button(pages, "//button[contains(@class, 'sel-ex-button')]//span[text() = 'Add Boost']", LocatorType.Xpath);
	private Button save = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()= ' Save ']", LocatorType.Xpath);
	private Button rankingDropdown = new Button(pages,"//div[contains(@class, 'alma-page-column-main-header')]//ex-combobox//button", LocatorType.Xpath);
	private String editName = "//div[contains(@class, 'info-section-title')]//input";
	private String editDescription = "//div[contains(@class, 'info-description')]//textarea";
	private String profileDescriptionLabel = "//edit-ranking-info//div[contains(@class, 'ranking-info-description')]//label";
	private String setCriteriaInRow = "//ex-combobox[contains(@class, 'sel-field-column-{0}')]//button";
	private String setWeigthInRow = "//span[contains(@class, 'sel-weight-column-{0}')]//div[contains(@class, 'sel-scale-{1}')]";
	private String nameWarning = "//div[contains(@class, 'ex-editable-text-not-valid-border')]";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRankingProfilePage editName(String name) {
		log.info("Set Profile Name to: '" + name + "'");
		su.getElementAndClick(LocatorType.Xpath, editName);
		su.clearAndSendKeys(name, LocatorType.Xpath, editName);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this.ensurePageLoaded();
	}
	
	public EditRankingProfilePage editDescription(String description) {
		log.info("Set Profile Description to: '" + description + "'");
		su.getElementAndClick(LocatorType.Xpath, editDescription);
		su.clearAndSendKeys(description, LocatorType.Xpath, editDescription);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this.ensurePageLoaded();
	}
	
	public EditRankingProfilePage setCriteriaInRow(int row, RankingCriteriaOption criteria) {
		log.info("Set Criteria in row " + row + " to: " + criteria.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, setCriteriaInRow, row);
		dropdown.setText(criteria.getName());
		return this.ensurePageLoaded();
	}
	
	public EditRankingProfilePage setWeightInRow(int row, RankingWeigthOption weight) {
		log.info("Set Weight in row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, setWeigthInRow, row, weight.getCode());
		return this.ensurePageLoaded();
	}
	
	public RankingProfilesListPage saveAndReturnToRankingListPage() {
		log.info("Save profile and return to the Ranking Profiles List page");
		save.waitElementClickableAndClick();
		return pages.rialto.rankingProfilesListPage.ensurePageLoaded();	
	}

	public EditRankingProfilePage selectRankingFromDropdown(String rankingName) {
		log.info("Select the ranking " + rankingName + " in the dropdown");
		rankingDropdown.click();
		dropdown.setText(rankingName);
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditRankingProfilePage clickAddCriteria() {
		addBoost.click();
		return this.ensurePageLoaded();
	}
	
	public EditRankingProfilePage clickSave() {
		log.info("Click on Save button");
		save.waitElementClickableAndClick();
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public EditRankingProfilePage verifyNameWarning(boolean expected) {
		log.info("Verify the name warning");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, nameWarning), expected);
		return this.ensurePageLoaded();
	}

	public EditRankingProfilePage verifySelectedRankingInDropdown(String rankingName, boolean expected) {
		log.info("Verify the selected ranking in the Ranking dropdown");
		rankingDropdown.click();
		GenUtils.verifyResult(dropdown.isOptionSelected(rankingName), expected);
		rankingDropdown.click();
		return this.ensurePageLoaded();
	}
}