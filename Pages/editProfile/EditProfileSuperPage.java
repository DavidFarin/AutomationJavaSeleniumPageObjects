package pages.rialto.editProfile;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.angular.AngDropdown;
import util.components.rialto.RialtoCheckbox;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.common.Button;
import util.components.rialto.rialtoList.OfferingListComponent;
import util.components.rialto.rialtoList.OfferingListComponent.OfferingListItemDetails;
import util.components.rialto.splitView.Offering_SplitView;
import util.interfaces.GeneralInterface.Codeable;
import util.interfaces.GeneralInterface.Nameable;

public class EditProfileSuperPage extends RialtoInternalPage {

	public EditProfileSuperPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public EditProfileSuperPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.elementToBeClickable(By.xpath(rankingDropdown)));
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(categoryInRow, "1.0"))));
		save.waitElementClickable();
		queryResultsList.ensurePageLoaded();
		return this;
	}
	
	public EditProfileSuperPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.elementToBeClickable(By.xpath(rankingDropdown)));
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(categoryInRow, "1.0"))));
		save.waitElementClickable();
		return this;
	}
	
	public enum QueryCategoryOption implements Nameable {
		Price("Price"), 
		PublishedXYearsAgo("Published X years ago"),
		YearOfPublication("Year of publication"),
		Publisher("Publisher"),
		Platform("Platform"), 
		License("License"),
		Availability("Availability"),
		DdaAvailable("DDA available (Ebooks)");

		private String name;

		private QueryCategoryOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
	
	public enum QueryOperatorOption implements Nameable {
		Equals("="),
		BiggerThan(">"),;

		private String name;

		private QueryOperatorOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
	
	public enum QueryValueOption implements Codeable {
		Input("//input[@id='field{0}']"), 
		SimpleDropdown("//ex-combobox[@id='field{0}']//button"),
		Checkbox("");

		private String code;

		private QueryValueOption(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
	
	public enum AvailabilityValueOption implements Nameable {
		NotYetPublished("Not yet published"), 
		OutOfPrint("Out of Print"),;

		private String name;

		private AvailabilityValueOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	protected OfferingListComponent queryResultsList = new OfferingListComponent(pages);
	protected Offering_SplitView profileSplitView = new Offering_SplitView(pages);

	protected Button save = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()= ' Save ']", LocatorType.Xpath);
	protected String cancel = "//button[contains(@class, 'sel-ex-button') and text()= ' Cancel ']";
	private String rankingDropdown = "//div[contains(@class, 'sel-edit-profile-ranking')]//i[contains(@class, 'sel-dropdown-arrow')]";
	private String selectRanking = "//button[contains(@class, 'ex-dropdown-item')]//span[text()='{0}']";
	private String runBtnInGroup = "//button[@id='playButton{0}']";
	private String categoryInRow = "//ex-combobox[@id='category{0}']//button";
	private String operatorInRow = "//ex-combobox[@id='operator{0}']//button";
	private String addRule = "//button[@id='addRule{0}']";
	private String closeTestResults = "//span[@class='edit-profile-close-results-icon']/i";
	private String editName = "//div[contains(@class, 'info-section-title')]//input";
	private String editDescription = "//div[contains(@class, 'info-description')]//textarea";
	private String profileDescriptionLabel = "//edit-profile-info//div[contains(@class, 'edit-profile-info-description')]//label";
	private String nameWarning = "//div[contains(@class, 'ex-editable-text-not-valid-border')]";
	private String rankingWarning = "//div[@class = 'edit-profile-top-actions']//div[contains(@class, 'not-valid-border-animation')]";
	protected String focusedItem = "/div[@class='ex-list-layout-row-item mat-ripple ex-list-layout-selected']";

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	protected EditProfileSuperPage setCategoryInRow(String queryId, QueryCategoryOption category) {
		log.info("Set Query category to " + category );
		AngDropdown categoryInRowD = new AngDropdown(pages, su.createDynamicLocator(categoryInRow, queryId), LocatorType.Xpath);
		categoryInRowD.setBySendingKeys(category.getName());
		return this;
	}
	
    public EditProfileSuperPage setOperatorInRow(String queryId, QueryOperatorOption operator) {
		log.info("Set Query operator to: " + operator.getName());
		AngDropdown operatorInRowD = new AngDropdown(pages, su.createDynamicLocator(operatorInRow, queryId), LocatorType.Xpath);
		operatorInRowD.setBySendingKeys(operator.getName());
		return this;
	}
	
    public EditProfileSuperPage setValueInRow(String queryId, QueryValueOption option, Object... value) {
		log.info("Set Query value");
		switch (option) {
		case Input:
			su.clearAndSendKeys((String) value[0], LocatorType.Xpath, option.getCode(), queryId);
			break;
		case SimpleDropdown:
			su.waitElementClickableAndClick(LocatorType.Xpath, option.getCode(), queryId);
			dropdown.setText((String) value[0]);
			break;
		case Checkbox:
			RialtoCheckbox checkbox =  new RialtoCheckbox(pages, "");
			checkbox.set((boolean) value[0]);
			break;
		}
		return this;
	}
	
    public EditProfileSuperPage runQueryInGroup(String groupId) {
		log.info("Run query in group " + groupId);
		su.getElementAndClick(LocatorType.Xpath, runBtnInGroup, groupId);
		return this;
	}
	
	protected EditProfileSuperPage addRule(String queryId) {
		log.info("Add rule");
		su.getElementAndClickJS(LocatorType.Xpath, addRule, queryId);
		return this;
	}
	
	protected EditProfileSuperPage closeQueryTestResults() {
		log.info("Close the Query test reusults list");
		su.getElementAndClick(LocatorType.Xpath, closeTestResults);
		return this;
	}
	
	protected EditProfileSuperPage editName(String name) {
		log.info("Set Profile Name to: '" + name + "'");
		su.getElementAndClick(LocatorType.Xpath, editName);
		su.clearAndSendKeys(name, LocatorType.Xpath, editName);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this;
	}
	
	protected EditProfileSuperPage editDescription(String description) {
		log.info("Set Profile Description to: '" + description + "'");
		su.getElementAndClick(LocatorType.Xpath, editDescription);
		su.clearAndSendKeys(description, LocatorType.Xpath, editDescription);
		su.waitElementClickableAndClick(LocatorType.Xpath, profileDescriptionLabel);
		return this;
	}
	
	protected EditProfileSuperPage selectRanking(String name) {
		log.info("Select Ranking: " + name);
		su.waitElementClickableAndClick(LocatorType.Xpath, rankingDropdown);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, selectRanking, name);
		return this;	
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditProfileSuperPage clickSave() {
		log.info("Click on Save button");
		save.waitElementClickableAndClick();
		return this;
	}

	public EditProfileSuperPage clickItem(int row) {
		queryResultsList.clickItem(row);
		profileSplitView.ensurePageLoaded();
		return this;
	}

	public EditProfileSuperPage clickPreviousItem() {
		profileSplitView.clickPreviousItem();
		return this;
	}

	public EditProfileSuperPage clickNextItem() {
		profileSplitView.clickNextItem();
		return this;
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public EditProfileSuperPage verifyQueryTestResults(QueryCategoryOption category, int numberOfItems, String expectedValue) {
		switch (category) {
		case PublishedXYearsAgo:
			queryResultsList.verifyResults(OfferingListItemDetails.PublisherAndYearValue, numberOfItems, expectedValue);
			break;
		case YearOfPublication:
			queryResultsList.verifyResults(OfferingListItemDetails.PublisherAndYearValue, numberOfItems, expectedValue);
			break;	
		case Publisher:
			queryResultsList.verifyResults(OfferingListItemDetails.PublisherAndYearValue, numberOfItems, expectedValue);
			break;
		case Price:
			queryResultsList.verifyResults(OfferingListItemDetails.PriceValue, numberOfItems, expectedValue);
			break;
		case Platform:
			queryResultsList.verifyResults(OfferingListItemDetails.PlatformValue, numberOfItems, expectedValue);
			break;
		case License:
			queryResultsList.verifyResults(OfferingListItemDetails.LicenseValue, numberOfItems, expectedValue);
			break;
		}
		return this;
	}
	
	protected EditProfileSuperPage verifyItemFocused(int row, boolean expected) {
		queryResultsList.verifyItemFocused(row, expected);
		return this;
	}

	protected EditProfileSuperPage verifyNameWarningExists(boolean expected) {
		log.info("Verify if Name warning exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, nameWarning), expected);
		return this;
	}
	
	protected EditProfileSuperPage verifyRankingWarningExists(boolean expected) {
		log.info("Verify if Ranking warning exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, rankingWarning), expected);
		return this;
	}

	protected EditProfileSuperPage verifySplitViewDetails(int row) {
		log.info("Verify Split View details in row: " + row);
		GenUtils.verifyResult(queryResultsList.getItem(row).deepEquals(profileSplitView.getItem()), true);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		return queryResultsList.getItem(row);
	}
}
