package util.components.rialto.splitView;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.angular.AngDropdown;
import util.components.common.Button;
import util.components.rialto.RialtoCheckbox;
import util.enums.HotKeyData.HotKey;
import util.interfaces.GeneralInterface.CodeNameable;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.GenUtils.Compare;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Nameable;

public class CartPage_SplitView extends RialtoSplitView {

	public CartPage_SplitView(MainPageManager pages) {
		super(pages);
	}

	@Override
	public CartPage_SplitView ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CartSplitViewField.InterestedUsersComponent.getName())));
		return this;
	}

	public enum CartSplitViewField implements Nameable {
		Remove("//ex-item-details-header//button[contains(@class, 'sel-record-actions-button')]//span[text()='Remove']"),
		Reject("//ex-item-details-header//button[contains(@class, 'sel-record-actions-button')]//span[text() = 'Reject']"),
		MoreActions("//ex-item-details-header//button[contains(@class, 'sel-record-actions-more-actions')]"),
		ArrowAction("//ex-item-details-header//button[contains(@class, 'sel-record-actions-arrow-button')]"),
		Title("//a[contains(@class,'cart-details-title')]"),
		Price("//cart-details//span[contains(@class,'sel-rialto-price')]"),
		Author("//span[contains(@class,'cart-details-author')]/span"),
		TemplateBanner("//span[contains(@class, 'sel-template') and contains(text(), '{0}')]"), 
		FundValue("//fund-item//span[contains(@class, 'sel-combobox-selected-value') and contains(text(), '{0}')]"),
		FundWarning("//fund-item//button[contains(@class, 'sel-combobox-warning')]"),
		ChooseLocation("//button//span[text() = ' Choose location']"),
		LocationValue("//locations-combobox//span[contains(@class, 'sel-combobox-selected-value')]"),
		LocationWarning("//location-item//button[contains(@class, 'sel-combobox-warning')]"),
		LibraryWarning("//ex-combobox[contains(@class, 'sel-ordering-library-combobox')]//button[contains(@class, 'sel-combobox-warning')]"),
		InterestedUserWarning("//div[contains(@class, 'sel-interested-users')]//span[contains(@class, 'ex-autocomplete-warning-label') and text() = 'Please choose a valid user']"),
		QuantityValue("//qty//span[contains(@class, 'sel-combobox-selected-value')and contains(@class, '{0}')]"),
		QuantityOption("//mat-option/span[text()= ' {0} ']"),
		ChooseReportingCode("//reporting-code[contains(@class, 'sel-reporting-code')]//button[contains(@class, 'sel-ex-combobox-selected-button')]"),
		RemoveReportingCode(ChooseReportingCode.getName() + "//span[text() = '{0}']/../..//button[contains(@class, 'sel-ex-close')]//i"),
		ReportingCodeValue("//button[contains(@class, 'sel-ex-combobox-selected-button')]//span[contains(text(), '{0}')]"),
		InterestedUsersComponent("//div[contains(@class, 'sel-interested-users')]"),
		AddInterestedUser("//button[contains(text(), 'Add interested user')]"),
		RemoveInterestedUser("//div[contains(@class, 'sel-interested-users-item')]//span[text()='{0}']/../ex-close//i"),
		RemoveInvalidInterestedUser("//div[contains(@class, 'sel-interested-users')]//div[contains(@class, 'interested-users-wrapper-autocomplete-wrapper')]//i"),
		InterestedUserInput("//div[contains(@class, 'sel-interested-users')]//input"),
		InterestedUserOption("//mat-option[contains(@class, 'sel-ex-autocomplete-option')]//mark[text()='{0}']"),
		InterestedUserSelected("//div[contains(@class, 'sel-interested-users-item')]//span[text()='{0}']"),
		ResizeInterestedUserPopUp("//div[contains(@class, 'sel-interested-users')]//i[contains(@class, 'interested-users-item-resize-icon')]"),
		ItemPolicyValue("//item-policy//span[contains(@class, 'sel-combobox-selected-value')]");

		private String name;

		CartSplitViewField(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum SplitViewCheckbox implements CodeNameable {
		
		ShareTemplate("Share Template", "sel-share-template"),
		OverrideExistingCartValues("Override Existing Cart Values", "sel-keep-or-overlay"),
		NoPhysicalProcessing("No Physical Processing", "sel-shelf-ready-bypass"),
		Rush("Rush", "sel-shelf-ready-rush"),
		NoMarcRecord("No MARC Record", "sel-shelf-ready-no-cataloging"),
		NoSpineLabel("No spine label", "sel-shelf-ready-no-spine"),
		OverrideCallNumber("Override call number", "sel-shelf-ready-call-number-checkbox"),
		NotifyItemIsReceived("Notify Item Is Received", "sel-send-email-checkbox"),
		PlaceOnHold("Place on Hold", "sel-create-request-checkbox");

		private String name;
		private String code;

		SplitViewCheckbox(String name, String code) {
			this.name = name;
			this.code = code;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getCode() {
			return code;
		}
	};
	
	public enum NoteOption implements Nameable {
		NoteToApprover("Note to approver"),
		NoteToVendor("Note to vendor"),
		ReceivingNotes("Receiving notes"),
		InternalNote("Internal note");

		private String name;

		NoteOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	private Button addNote = new Button(pages, "//cart-notes//button[contains(@class, 'sel-ex-button') and text()='Add note']", LocatorType.Xpath);
	private AngDropdown chooseLocation = new AngDropdown(pages, "//button//span[text() = ' Choose location']", LocatorType.Xpath);
	private AngDropdown chooseQuantity = new AngDropdown(pages, "//qty//button[contains(@class, 'sel-ex-combobox')]", LocatorType.Xpath);
	private AngDropdown chooseFund = new AngDropdown(pages, "//fund-item//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private AngDropdown chooseItemPolity = new AngDropdown(pages, "//item-policy//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private AngDropdown chooseReportingCode = new AngDropdown(pages, CartSplitViewField.ChooseReportingCode.getName(), LocatorType.Xpath);
//	private RialtoCheckbox shareTemplate = new RialtoCheckbox(pages, SplitViewCheckbox.ShareTemplate.getCode());
//	private RialtoCheckbox overrideCartValues = new RialtoCheckbox(pages, SplitViewCheckbox.OverrideExistingCartValues.getCode());
//	private RialtoCheckbox rush = new RialtoCheckbox(pages, SplitViewCheckbox.Rush.getCode());
//	private RialtoCheckbox noCataloging =  new RialtoCheckbox(pages, SplitViewCheckbox.NoMarcRecord.getCode());
	private RialtoCheckbox notifyItemIsReceived =  new RialtoCheckbox(pages, SplitViewCheckbox.NotifyItemIsReceived.getCode());
	private RialtoCheckbox placeOnHold =  new RialtoCheckbox(pages, SplitViewCheckbox.PlaceOnHold.getCode());
	private RialtoCheckbox overrideCallNumber = new RialtoCheckbox(pages, SplitViewCheckbox.OverrideCallNumber.getCode());
	private String overrideCallNumberInput = "//ex-editable-text[contains(@class, 'sel-shelf-ready-call-number-input')]//input";
	private String selectNoteOption = "//button[contains(@class, 'sel-record-actions-button')]//span[contains(@class, 'sel-value') and text()='{0}']";
	private String noteContainer = "//div[contains(@class, 'sel-note')]//span[contains(@class, 'note-title') and text()='{0}']";
	private String noteTextarea = noteContainer + "/..//textarea";
	private String noteValue = noteContainer + "//..//p";
	private String removeNote = noteContainer + "/..//ex-icon//i";
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void removeItem() {
		log.info("Remove item");
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.Remove.getName());
	}
	
	public void rejectItem() {
		log.info("Reject item");
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.Reject.getName());
	}
	
	public void clickMoreActions() {
		log.info("Click on More Actions button");
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.MoreActions.getName());
	}
	
	public void clickArrowAction() {
		log.info("Click on Arrow Action button");
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.ArrowAction.getName());
	}
	
	public void chooseFund(String fundName) {
		log.info("Choose fund: " + fundName);
		chooseFund.setBySendingKeys(fundName);
	}

	public void chooseLocation(String location) {
		log.info("Choose location: " + location);
		chooseLocation.setBySendingKeysSelectFirstOption(location);
	}

	public void chooseQuantity(int quantity) {
		log.info("Choose quantity: " + quantity);
		chooseQuantity.setBySendingKeys(String.valueOf(quantity));
	}
	
	public void chooseItemPolicy(String policy) {
		log.info("Choose Item Policy: " + policy);
		chooseItemPolity.setBySendingKeys(policy);
	}
	
	public void chooseReportingCode(CodeNameable code) {
		log.info("Choose Reporting Code: " + code.getName());
		chooseReportingCode.setBySendingKeys(code.getName());
	}
	
	public void removeReportingCode(CodeNameable code) {
		log.info("Remove Reporting Code: " + code.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.RemoveReportingCode.getName(), code.getName());
		ensurePageLoaded();
	}
	
	public void setCheckbox(SplitViewCheckbox locator, boolean check) {
		log.info(check ? "Select" : "Unselect" + " the checkbox '" + locator.getName() + "'");
		
		RialtoCheckbox checkbox = new RialtoCheckbox(pages, locator.getCode());
		checkbox.set(check);
		
//		switch(checkbox) {
//			case ShareTemplate:
//				shareTemplate.set(check);
//				break;
//			case OverrideExistingCartValues:
//				overrideCartValues.set(check);
//				break;
//			case Rush:
//				rush.set(check);
//				break;
//			case NoMarcRecord:
//				noCataloging.set(check);
//				break;
//			case NotifyItemIsReceived:
//				notifyItemIsReceived.set(check);
//				break;
//			case PlaceOnHold:
//				placeOnHold.set(check);
//				break;
//		}
	}
	
	public void verifyCheckboxSelected(SplitViewCheckbox locator, boolean expected) {
		log.info("Verify if the checkbox '" + locator.getName() +"' is selected");
		RialtoCheckbox checkbox = new RialtoCheckbox(pages, locator.getCode());
		GenUtils.verifyResult(checkbox.isChecked(), expected);
	}
	
	public void overrideCallNumber(String callNumber) {
		log.info("Override call number to: " +  callNumber);
		overrideCallNumber.set(true);
		su.clearAndSendKeys(callNumber, LocatorType.Xpath, overrideCallNumberInput);
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.Author.getName());
	}
	
	public void addNote(NoteOption option, String note) {
		log.info("Add '" + option.getName() + "'");
		addNote.waitElementClickableAndClick();
		su.waitElementClickableAndClick(LocatorType.Xpath, selectNoteOption, option.getName());
		editNote(option, note);
	}
	
	public void editNote(NoteOption option, String note) {
		log.info("Edit '" + option.getName() + "'");
		su.waitElementClickableAndClick(LocatorType.Xpath, noteTextarea, option.getName());
		su.waitElementClickable(LocatorType.Xpath, noteTextarea, option.getName());
		su.clearAndSendKeys(note, LocatorType.Xpath, noteTextarea, option.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, noteContainer, option.getName());
	}
	
	public void removeNote(NoteOption option) {
		log.info("Remove " + option.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, noteTextarea, option.getName());
		su.waitElementClickableAndClickJS(LocatorType.Xpath, removeNote, option.getName());
	}

	public void addInterestedUser(String user, boolean itemReceived, boolean hold) {
		log.info("Add Interested User: " + user);
		selectInterestedUser(user);
		notifyItemIsReceived.set(itemReceived);
		placeOnHold.set(hold);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, CartSplitViewField.ResizeInterestedUserPopUp.getName());
		waitMedium.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(CartSplitViewField.ResizeInterestedUserPopUp.getName())));
	}
	
	public void selectInterestedUser(String user) {
		log.info("Select Interested User: " + user);
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.AddInterestedUser.getName());
		su.waitElementVisibleAndSendKeys(user, LocatorType.Xpath, CartSplitViewField.InterestedUserInput.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.InterestedUserOption.getName(), user);
	}
	
	public void removeInterestedUser(String user) {
		log.info("Remove Interested User: " + user);
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.RemoveInterestedUser.getName(), user);
	}
	
	public void removeInvalidInterestedUser() {
		log.info("Remove invalid Interested User");
		su.waitElementClickableAndClick(LocatorType.Xpath, CartSplitViewField.RemoveInvalidInterestedUser.getName());
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyTemplateLibraryWarning(boolean expected) {
		log.info("Verify library warning in Template");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.LibraryWarning.getName()), expected);
	}
	
	public void verifyLocationWarning(boolean expected) {
		log.info("Verify location warning");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.LocationWarning.getName()), expected);
	}

	public void verifyFundWarning(boolean expected) {
		log.info("Verify fund warning");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.FundWarning.getName()), expected);
	}
	
//	public void verifyShareTemplateCheckboxSelected(boolean expected) {
//		log.info("Verify if the 'Share Template' checkbox is selected");
//		GenUtils.verifyResult(shareTemplate.isChecked(), expected);
//	}
	
	public void verifyInteresterUserWarning(boolean expected) {
		log.info("Verify Interested User warning");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.InterestedUserWarning.getName()), expected);
	}
	
	public void verifyInterestedUserExists(String user, boolean expected) {
		log.info("Verify the interested user '" + user + "' exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.InterestedUserSelected.getName(), user), expected);
	}
	
	public void verifyTemplateBanner(boolean expected, String... templateName) {
		log.info("Verify if the template banner exists");		
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.TemplateBanner.getName(), 
																	templateName.length > 0 ? templateName[0] : "" ), expected);
	}
	
	public void verifyQuantity(String expected) {
		log.info("Verify the quantity is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, CartSplitViewField.QuantityValue.getName(), ""), expected);
	}

	public void verifyQuantityViewMode() {
		log.info("Verify the quantity is in View Mode");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.QuantityValue.getName(), "ex-combobox-view-mode"), true);
	}
	
	public void verifyLocation(String expected) {
		log.info("Verify the location is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, CartSplitViewField.LocationValue.getName()), expected, Compare.CONTAINS);
	}
	
	public void verifyFund(String expected) {
		log.info("Verify the fund is: " + expected);
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(CartSplitViewField.FundValue.getName(), expected))));
	}
	
	public void verifyItemPolicy(String expected) {
		log.info("Verify the Item policy is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, CartSplitViewField.ItemPolicyValue.getName()), expected);
	}
	
	public void verifyReportingCode(String code, boolean expected) {
		log.info("Verify if the Reporting Code '" + code + "' exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, CartSplitViewField.ReportingCodeValue.getName(), code), expected);
	}
	
	public void verifyCallNumber(String expected) {
		log.info("Verify 'Override call number' is: " +  expected);
		GenUtils.verifyResult(overrideCallNumber.isChecked(), true);
		GenUtils.verifyResult(su.getElementAndGetValue(LocatorType.Xpath, overrideCallNumberInput), expected);
	}
	
	public void verifyNoteOptionExists(NoteOption option, boolean expected) {
		log.info("Verify if " + option.getName() + " option exists");
		addNote.waitElementClickableAndClick();
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, selectNoteOption, option.getName()), expected);
		su.pressKeyboard(HotKey.Enter);
	}
	
	public void verifyNoteEditMode(NoteOption option, String expectedNote) {
		log.info("Verify " + option.getName());
		GenUtils.verifyResult(su.getElementAndGetValue(LocatorType.Xpath, noteTextarea, option.getName()), expectedNote);
	}
	
	public void verifyNoteViewMode(NoteOption option, String expectedNote) {
		log.info("Verify " + option.getName());
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, noteValue, option.getName()), expectedNote);
	}
	
//	public void verifyRush(boolean expected) {
//		log.info("Verify if 'Rush' checkbox is selected");
//		GenUtils.verifyResult(rush.isChecked(), expected);
//	}
//	
//	public void verifyNoCataloging(boolean expected) {
//		log.info("Verify if 'No cataloging' checkbox is selected");
//		GenUtils.verifyResult(noCataloging.isChecked(), expected);
//	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public String getField(CartSplitViewField expectedField) {
		if (su.isElementExist(LocatorType.Xpath, expectedField.getName())) {
			return su.getElementAndGetText(LocatorType.Xpath, expectedField.getName());
		}
		return "";
	}

	public RialtoItem getItem() {
		log.info("Get details of item in Split View");
		return new RialtoItem(getField(CartSplitViewField.Title), getField(CartSplitViewField.Author), getField(CartSplitViewField.Price));
	}
}