package pages.rialto;

import org.openqa.selenium.By;
import util.components.angular.AngDropdown;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.common.Button;
import util.interfaces.GeneralInterface.CodeNameable;
import models.rialto.RialtoItem;
import models.rmModels.PhysicalItem.ItemPolicy;
import pages._pages_mngt.MainPageManager;
import pages.rialto.dlg.RialtoDlgRemoveItems;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.rialto.rialtoList.CartListComponent;
import util.components.rialto.splitView.CartPage_SplitView;
import util.components.rialto.splitView.CartPage_SplitView.NoteOption;
import util.components.rialto.splitView.CartPage_SplitView.SplitViewCheckbox;
import util.enums.HotKeyData.HotKey;
import util.interfaces.GeneralInterface.Nameable;

public class CartPage extends RialtoInternalPage {

	public CartPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public CartPage ensurePageLoaded() {
		ensurePageLoadedWithoutSplitView();
		cartPageSplitView.ensurePageLoaded();
		return this;
	}

	public CartPage ensurePageLoadedWithoutSplitView() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(cartPageLoadingBlocker)));
		try {
			waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(orderSummaryAreaBtn)));
			cartPageList.ensurePageLoaded();
		} catch (Exception e) {
			//if cart empty
			verifyCartEmpty();
		}
		return this;
	}
	
	public enum CartTemplateOption implements Nameable {
		ApplyToAll("Apply to all"),
		ApplyToSelected("Apply to selected");

		private String name;

		CartTemplateOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	public enum CartNoteOption implements Nameable {
		NoteToApprover("Note to approver"),
		NoteToVendor("Note to vendor"),
		ReceivingNotes("Receiving notes"),;

		private String name;

		CartNoteOption(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum RejectReason implements CodeNameable {
		Cost("COST", "Cost "),
		Duplicate("DUPLICATE", "Duplicate "),
		Irrelevant("IRRELEVANT", "Irrelevant "),
		CanceledByRequester("CANCELLED_BY_REQUESTER", "Canceled by requester "),;

		private String name;
		private String code;

		RejectReason(String name, String code) {
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
	}

	private CartListComponent cartPageList = new CartListComponent(pages);
	private CartPage_SplitView cartPageSplitView = new CartPage_SplitView(pages);

	
	private Button rejectBulkBtn = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()=' Reject ']", LocatorType.Xpath);
	private Button done = new Button(pages, "//button[text()=' Done ']", LocatorType.Xpath);
	private Button checkout = new Button(pages, "//button[contains(@class, 'sel-ex-button')and text()= ' Checkout ']", LocatorType.Xpath);
	private Button sendToApproval = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()='Send to Approval ']", LocatorType.Xpath);
	private Button approve = new Button(pages, "//button[text()='Approve ']", LocatorType.Xpath);
	
	private AngDropdown orderingLibrary = new AngDropdown(pages, "//div[contains(@class, 'selector-details-location')]//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private AngDropdown templates = new AngDropdown (pages, "//span[contains(@class, 'sel-combobox-selected-value') and text() = ' Please select a value']", LocatorType.Xpath);
	
	private String emptyCart = ".cart-placeholder-text";
	private String orderSummaryAreaBtn = "//div[contains(@class, 'sel-cart-price-summary')]//button";
	private String removeBulkBtn = "//button[contains(@class, 'sel-ex-button') and text()=' Remove ']";
	private String skipChooseOwningLibrary = "//div[contains(@class, 'sel-selector-details')]//span[contains(@class,'selector-details-skip')]";
	private String cartPageLoadingBlocker = "//mat-spinner[contains(@class, 'mat-progress-spinner')]";
	private String selectNoteOption = "//button[contains(@class, 'sel-record-actions-button')]//span[contains(@class, 'sel-value') and text()='{0}']";
	
	
	private String orderSummaryNumOfItems = ".cart-price-summary-qty";
	private String orderTotalAmount= ".cart-price-summary-order-total .rialto-price-calculated-price";
	private String targetList = "//button[contains(@class, 'sel-record-actions-button')]//span[text() = '{0}']";
	private String selectAllItems = "//mat-checkbox[contains(@class, 'sel-check-all')]//input";
	private String headerQuantity = "//span[contains(@class, 'cart-title')]";
	private String applyTemplate = "//button[contains(@class, 'sel-ex-button') and text() = ' {0} ']";
	private String applyTemplateInRow = "//button[contains(@class, 'sel-record-actions-button') and text()=' Use a template ']";
	private String targetTemplate = "//button[contains(@class, 'sel-record-actions-button')]//span[contains(@class, 'sel-value') and text() = '{0}']";
	private String displayTitleInMarket = "//button[contains(@class, 'sel-record-actions-button')]//span[contains(@class, 'sel-value') and text()='Display Title in Market']";
	private String rejectReason = "//div[text() = '{0}']/parent::label//div[contains(@class, 'mat-radio-outer-circle')]";
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public CartPage skipSelectOrderingLibraryIfExists() {
		log.info("Click on Skip - select ordering library");
		su.getElementAndClickIfExists(LocatorType.Xpath, skipChooseOwningLibrary);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage selectOrderingLibrary(String library) {
		log.info("Choose Ordering Library: " + library);
		orderingLibrary.setBySendingKeys(library);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage changeOrderingLibrary(String library) {
		log.info("Change Ordering Library to: " + library);
		orderingLibrary.setBySendingKeys(library);
		pages.rialto.dlg.dlgRialtoChangeOwningLibrary.ensurePageLoaded().clickChange();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage applyTemplate(String templateName, CartTemplateOption option) {
		log.info("Apply the template: " + templateName);
		templates.setBySendingKeys(templateName);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, applyTemplate, option.getName());
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage applyTemplateToItemInRow(int row, String templateName) {
		log.info("Apply the template: " + templateName + " to item in row: " + row);
		cartPageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, applyTemplateInRow);		
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetTemplate, templateName);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage rejectAllItems() {
		log.info("Reject all items");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, selectAllItems);
		rejectBulkBtn.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage removeAllItemsIfExist() {
		log.info("Remove all items");
		if (su.isElementExist(LocatorType.Xpath, removeBulkBtn)) {
			su.waitElementClickableAndClickJS(LocatorType.Xpath, selectAllItems);
			clickRemoveBulkBtn().clickRemove();
		}
		return verifyCartEmpty();
	}

	public CartPage chooseFund(String fundName) {
		cartPageSplitView.chooseFund(fundName);
		return this.ensurePageLoaded();
	}
	
	public CartPage addInterestedUser(String user, boolean itemReceived, boolean placeOnHold) {
		cartPageSplitView.addInterestedUser(user, itemReceived, placeOnHold);
		return this.ensurePageLoaded();
	}
	
	public CartPage selectInterestedUser(String user) {
		cartPageSplitView.selectInterestedUser(user);
		return this.ensurePageLoaded();
	}
	
	public CartPage removeInterestedUser(String user) {
		cartPageSplitView.removeInterestedUser(user);
		return this.ensurePageLoaded();
	}
	
	public CartPage removeInvalidInterestedUser() {
		cartPageSplitView.removeInvalidInterestedUser();
		return this.ensurePageLoaded();
	}
	
	public CartPage chooseLocation(String locationName) {
		cartPageSplitView.chooseLocation(locationName);
		return this.ensurePageLoaded();
	}

	public CartPage chooseQuantity(int quantity) {
		cartPageSplitView.chooseQuantity(quantity);
		return this.ensurePageLoaded();
	}
	
	public CartPage chooseItemPolicy(ItemPolicy policy) {
		cartPageSplitView.chooseItemPolicy(policy.getName());
		return this.ensurePageLoaded();
	}
	
	public CartPage chooseReportingCode(CodeNameable code) {
		cartPageSplitView.chooseReportingCode(code);
		return this.ensurePageLoaded();
	}
	
	public CartPage removeReportingCode(CodeNameable code) {
		cartPageSplitView.removeReportingCode(code);
		return this.ensurePageLoaded();
	}
	
	public CartPage setCheckbox(SplitViewCheckbox checkbox, boolean check) {
		cartPageSplitView.setCheckbox(checkbox, check);
		return this.ensurePageLoaded();
	}
	
	public CartPage overrideCallNumber(String callNumber) {
		cartPageSplitView.overrideCallNumber(callNumber);
		return this.ensurePageLoaded();
	}

	@Override
	public CartPage sortList(RialtoSort option) {
		clickItem(0);
		super.sortList(option);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage addNote(NoteOption option, String note) {
		cartPageSplitView.addNote(option, note);
		return this.ensurePageLoaded();
	}
	
	public CartPage editNote(NoteOption option, String note) {
		cartPageSplitView.editNote(option, note);
		return this.ensurePageLoaded();
	}
	
	public CartPage removeNote(NoteOption option) {
		cartPageSplitView.removeNote(option);
		return this.ensurePageLoaded();
	}
	
	public CartPage rejectItemInRow(int row) {
		log.info("Reject item in Row - " + row);
		cartPageList.clickRejectItem(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage rejectItemByReasonFromList(int row, RejectReason reason) {
		log.info("Reject item in row - " + row + " by reason: " + reason.getName());
		cartPageList.clickArrowAction(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, rejectReason, reason.getCode());
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage rejectItemFromSplitView() {
		log.info("Reject item from the Split View");
		cartPageSplitView.rejectItem();
		return this.ensurePageLoaded();
	}
	
	public CartPage rejectItemByReasonFromSplitView(RejectReason reason) {
		log.info("Reject item from the Split View by reason: " + reason.getName());
		cartPageSplitView.clickArrowAction();
		su.waitElementClickableAndClickJS(LocatorType.Xpath, rejectReason, reason.getCode());
		return this.ensurePageLoaded();
	}
	
	public CartsListPage done() {
		log.info("Done");
		done.waitElementClickableAndClick();
		return pages.rialto.cartsListPage.ensurePageLoadedEmptyList();
	}
	
	public CartPage moveItemToList(int row, String list) {
		log.info("Move the item in row " + row + " to the list '" + list + "'");
		cartPageList.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, moveToList);		
		su.waitElementClickableAndClickJS(LocatorType.Xpath, targetList, list);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage navigateToWorkFromRowAction(int row) {
		log.info("Navigate to work of the item in row - " + row);
		cartPageList.clickMoreActions(row);
		su.waitElementClickableAndClick(LocatorType.Xpath, displayTitleInMarket);
		return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
	}
	
	public WorkPage navigateToWorkFromSplitView() {
		log.info("Navigate to work of the item from Split View");
		cartPageSplitView.clickMoreActions();
		su.waitElementClickableAndClick(LocatorType.Xpath, displayTitleInMarket);
		return pages.rialto.workPage.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public CartPage clickItem(int row) {
		cartPageList.clickItem(row);
		cartPageSplitView.ensurePageLoaded();
		return this;
	}

	public CartPage clickRemoveItem(int row) {
		cartPageList.clickRemoveItem(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage clickRemoveItemFromSplitView(int row) {
		cartPageList.clickItem(row);
		cartPageSplitView.removeItem();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public RialtoDlgRemoveItems clickRemoveBulkBtn() {
		log.info("Click on Remove bulk button");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, removeBulkBtn);
		return pages.rialto.dlg.dlgRialtoRemoveItems.ensurePageLoaded();
	}
	
	public CartPage clickRejectBulkBtn() {
		log.info("Click on Reject bulk button");
		rejectBulkBtn.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CartPage clickAllCheckbox() {
		log.info("Click on all items checkbox");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, selectAllItems);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage clickItemCheckbox(int row) {
		cartPageList.clickItemCheckbox(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ConfirmationPage sendToApprovalReturnsConfirmationPage() {
		log.info("Click on Send to Approval");
		sendToApproval.waitElementClickableAndClick();
		return pages.rialto.confirmationPage.ensurePageLoaded();
	}
	
	public CartPage sendToApprovalReturnsCartPage() {
		log.info("Click on Send to Approval");
		sendToApproval.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CheckoutPage approveReturnsCheckoutPage() {
		log.info("Click on Approve");
		approve.waitElementClickableAndClick();
		return pages.rialto.checkoutPage.ensurePageLoaded();
	}
	
	public CartPage approveReturnsCartPage() {
		log.info("Click on Approve");
		approve.waitElementClickableAndClick();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public CheckoutPage checkoutReturnsCheckoutPage() {
		log.info("Click on Checkout");
		checkout.waitElementClickableAndClickJS();
		return pages.rialto.checkoutPage.ensurePageLoaded();
	}

	public CartPage checkoutReturnsCartPage() {
		log.info("Click on Checkout");
		checkout.waitElementClickableAndClickJS();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage clickPreviousItem() {
		cartPageSplitView.clickPreviousItem();
		return this.ensurePageLoaded();
	}

	public CartPage clickNextItem() {
		cartPageSplitView.clickNextItem();
		return this.ensurePageLoaded();
	}
	
	public CartPage clickListViewButton() {
		super.clickListViewButton();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public CartPage clickSplitViewButton() {
		super.clickSplitViewButton();
		return this.ensurePageLoaded();
	}

	public CartPage clickCloseSplitViewButton() {
		cartPageSplitView.closeSplitView();
		return this.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Override
	public CartPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		super.verifyMsgAppears(expectedMessage, placeHolders);
		return this;
	}

	public CartPage verifyItemInCart(RialtoItem item, RialtoBadge... badges) {
		cartPageList.verifyItemInCart(item, badges);
		return this;
	}

	public CartPage verifyWarningInItem(int row, boolean expected) {
		cartPageList.verifyWarningInItem(row, expected);
		return this;
	}
	
	public CartPage verifyBadgeInItem(int row, RialtoBadge badge) {
		cartPageList.isBadgesInItemExist(row, badge);
		return this;
	}

	public CartPage verifyLocationWarning(boolean expected) {
		cartPageSplitView.verifyLocationWarning(expected);
		return this;
	}

	public CartPage verifyFundWarning(boolean expected) {
		cartPageSplitView.verifyFundWarning(expected);
		return this;
	}

	public CartPage verifyItemFocused(int row, boolean expected) {
		cartPageList.verifyItemFocused(row, expected);
		return this;
	}

	public CartPage verifySplitViewDetails(int row) {
		log.info("Verify the details in the Split View correspond to the focused item");
		GenUtils.verifyResult(cartPageList.getItem(row).basicEquals(cartPageSplitView.getItem()), true);
		return this;
	}

	public CartPage verifySplitViewClosed() {
		cartPageSplitView.verifySplitViewClosed();
		return this;
	}

	public CartPage verifyCartEmpty() {
		log.info("Verify Cart is Empty");
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(emptyCart)));
		return this;
	}

	public CartPage verifyListSorted(RialtoSort option) {
		log.info("Verify the list is sorted by " + option.getName());
		cartPageList.verifyListSorted(option);
		return this;
	}

	public CartPage verifyItemsNumberInCart(int expected) {
		super.verifyItemsNumberInCart(expected);
		return this;
	}
	
	public CartPage verifyTemplateBanner(boolean exists, String... templateName) {
		cartPageSplitView.verifyTemplateBanner(exists, templateName);
		return this;
	}
	
	public CartPage verifyQuantity(String expected) {
		cartPageSplitView.verifyQuantity(expected);
		return this;
	}
	
	public CartPage verifyQuantityViewMode() {
		cartPageSplitView.verifyQuantityViewMode();
		return this;
	}
	
	public CartPage verifyLocation(String expected) {
		cartPageSplitView.verifyLocation(expected);
		return this;
	}
	
	public CartPage verifyFund(String expected) {
		cartPageSplitView.verifyFund(expected);
		return this;
	}
	
	public CartPage verifyItemPolicy(String expected) {
		cartPageSplitView.verifyItemPolicy(expected);
		return this;
	}
	
	public CartPage verifyReportingCode(String code, boolean expected) {
		cartPageSplitView.verifyReportingCode(code, expected);
		return this;
	}
	
//	public CartPage verifyRushCheckbox(boolean expected) {
//		cartPageSplitView.verifyRush(expected);	
//		return this;
//	}
	
//	public CartPage verifyNoCatalogingCheckbox(boolean expected) {
//		cartPageSplitView.verifyNoCataloging(expected);	
//		return this;
//	}
	
	public CartPage verifyCheckbox(SplitViewCheckbox locator, boolean expected) {
		cartPageSplitView.verifyCheckboxSelected(locator, expected);	
		return this;
	}
	
	public CartPage verifyCallNumber(String expected) {
		cartPageSplitView.verifyCallNumber(expected);
		return this.ensurePageLoaded();
	}
	
	public CartPage verifyNoteEditMode(NoteOption option, String expectedNote) {
		cartPageSplitView.verifyNoteEditMode(option, expectedNote);
		return this;
	}
	
	public CartPage verifyNoteViewMode(NoteOption option, String expectedNote) {
		cartPageSplitView.verifyNoteViewMode(option, expectedNote);
		return this;
	}

	public CartPage verifyNoteOptionExists(NoteOption option, boolean expected) {
		cartPageSplitView.verifyNoteOptionExists(option, expected);
		return this.ensurePageLoaded();
	}
	
	public CartPage verifyInterestedUserExists(String user, boolean expected) {
		cartPageSplitView.verifyInterestedUserExists(user, expected);
		return this.ensurePageLoaded();
	}
	
	public CartPage verifyInterestedUserWarning(boolean expected) {
		cartPageSplitView.verifyInteresterUserWarning(expected);
		return this.ensurePageLoaded();
	}
	
	public CartPage verifyOrderSummaryNumOfItems(int expected) {
		log.info("Verify the number of items in the Order Summary is " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.CSS, orderSummaryNumOfItems), String.valueOf(expected));
		return this;
	}

	public CartPage verifyHeaderQuantity(int expected) {
		log.info("Verify the Header quantity is " + expected);
		GenUtils.verifyResult(getHeaderQuantity(), String.valueOf(expected));
		return this;
	}
	public CartPage verifyOrderTotal() {
		log.info("Verify the Order Total Amount");
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.CSS, orderTotalAmount).replaceAll(",", ""), String.format("%.02f",cartPageList.getOrderTotalSum()));
		return this;
	}

	public CartPage verifyAllRowsQuantity(int expected) {
		log.info("Verify the quantity for all the rows is " + expected);
		int totalQuantity = 0;
		int size = cartPageList.getPageSize();
		for (int row = 0; row < size; row++) {
			totalQuantity = totalQuantity + Integer.parseInt(cartPageList.getItemQuantity(row));
		}
		GenUtils.verifyResult(totalQuantity, expected);
		return this;
	}
	
	public CartPage verifyTemplateAvailable(String templateName, boolean expected) {
		log.info("Verify if the template '" + templateName + "' is available");
		templates.openDropdown().verifyOptionExists(templateName, expected).closeDropdown();
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		return cartPageList.getItem(row);
	}

	public String getHeaderQuantity() {
		log.info("Return header quantity");
		return su.getElementAndGetText(LocatorType.Xpath, headerQuantity).split(" ")[0];
	}
}