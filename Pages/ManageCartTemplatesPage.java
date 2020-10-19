package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.interfaces.GeneralInterface.CodeNameable;
import pages._pages_mngt.MainPageManager;
import pages.rialto.dlg.RialtoDlgRemoveTemplates;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.angular.AngDropdown;
import util.components.rialto.rialtoList.CartListComponent;
import util.components.rialto.splitView.CartPage_SplitView;
import util.components.rialto.splitView.CartPage_SplitView.SplitViewCheckbox;

public class ManageCartTemplatesPage extends RialtoInternalPage {

	public ManageCartTemplatesPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public ManageCartTemplatesPage ensurePageLoaded() {
		ensurePageLoadedWithoutSplitView();
		manageCartTemplatesPageSplitView.ensurePageLoaded();
		return this;
	}

	public ManageCartTemplatesPage ensurePageLoadedWithoutSplitView() {
		super.ensurePageLoaded();
		try {
			manageCartTemplatesPageList.ensurePageLoaded();
		} catch (Exception e) {
			//if the list is empty
			verifyEmptyPage();
		}
		return this;
	}

	private CartListComponent manageCartTemplatesPageList = new CartListComponent(pages);
	private CartPage_SplitView manageCartTemplatesPageSplitView = new CartPage_SplitView(pages);

	private AngDropdown filterTemplates = new AngDropdown(pages, "//ex-filters//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
	private String chooseOrderingLibraryLocator = "//ex-combobox[contains(@class, 'sel-ordering-library-combobox')]//button";
	private AngDropdown chooseOrderingLibrary = new AngDropdown(pages, chooseOrderingLibraryLocator, LocatorType.Xpath);

	private String emptyPage = "//p[contains(@class, 'ex-list-layout-no-records') and text()=' No records were found. ']";		//NGS-3516
	private String addTemplate = "//button[contains(@class, 'sel-ex-button') and contains(text(),'Add template')]";
	private String editName = "//ex-editable-text[contains(@class, 'title')]//input[contains(@class, 'sel-input')]";
	private String libraryValue = "//ex-combobox[contains(@class, 'sel-ordering-library-combobox')]//span[contains(@class, 'sel-combobox-selected-value')]";
	private String removeSelected = "//button[contains(@class, 'sel-ex-button') and text()=' Remove selected ']";
	private String selectAllItems = "//mat-checkbox[contains(@class, 'sel-check-all')]//input";
	

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public ManageCartTemplatesPage addTemplate() {
		log.info("Add template");
		su.waitElementClickableAndClick(LocatorType.Xpath, addTemplate);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage editName(String name) {
		log.info("Set name to: " + name);
		su.clearAndSendKeys(name, LocatorType.Xpath, editName);
		su.getElementAndClick(LocatorType.Xpath, numberOfResults);
		return this.ensurePageLoaded();
	}

	public ManageCartTemplatesPage selectOrderingLibrary(String library) {
		log.info("Set Ordering Library to: " + library);
		chooseOrderingLibrary.setBySendingKeys(library);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage chooseLocation(String locationName) {
		manageCartTemplatesPageSplitView.chooseLocation(locationName);
		return this.ensurePageLoaded();
	}

	public ManageCartTemplatesPage chooseQuantity(int quantity) {
		manageCartTemplatesPageSplitView.chooseQuantity(quantity);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage chooseFund(String fundName) {
		manageCartTemplatesPageSplitView.chooseFund(fundName);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage chooseReportingCode(CodeNameable code) {
		manageCartTemplatesPageSplitView.chooseReportingCode(code);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage removeReportingCode(CodeNameable code) {
		manageCartTemplatesPageSplitView.removeReportingCode(code);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage addInterestedUser(String user, boolean itemReceived, boolean placeOnHold) {
		manageCartTemplatesPageSplitView.addInterestedUser(user, itemReceived, placeOnHold);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage setCheckbox(SplitViewCheckbox checkbox, boolean check) {
		manageCartTemplatesPageSplitView.setCheckbox(checkbox, check);
		return this.ensurePageLoaded();
	}

	@Override
	public ManageCartTemplatesPage sortList(RialtoSort option) {
		super.sortList(option);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage filterTemplates(String filterTerm) {
		log.info("Filter the templates by " + filterTerm);
		filterTemplates.setBySendingKeys(filterTerm);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage selectPagination(RialtoPaginationOption paginationOption) {
		super.selectPagination(paginationOption);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage jumpToPageFixed(String pageNum) {
		super.jumpToPageFixed(pageNum);
		return this.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public ManageCartTemplatesPage clickSplitViewButton() {
		super.clickSplitViewButton();
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage clickTemplateInRow(int row) {
		manageCartTemplatesPageList.clickItem(row);
		return this.ensurePageLoaded();
	}

	public ManageCartTemplatesPage removeTemplateInRow(int row) {
		manageCartTemplatesPageList.clickRemoveItem(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage removeTemplateFromSplitView() {
		manageCartTemplatesPageSplitView.removeItem();
		return this.ensurePageLoadedWithoutSplitView();
	}

	public RialtoDlgRemoveTemplates removeSelected() {
		log.info("Click on Remove selected");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, removeSelected);
		return pages.rialto.dlg.dlgRialtoRemoveTemplates.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage clickAllCheckbox() {
		log.info("Click on all items checkbox");
		su.waitElementClickableAndClickJS(LocatorType.Xpath, selectAllItems);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public ManageCartTemplatesPage clickTemplateCheckbox(int row) {
		manageCartTemplatesPageList.clickItemCheckbox(row);
		return this.ensurePageLoadedWithoutSplitView();
	}
	
	public ManageCartTemplatesPage clickTemplateToggle(int row) {
		manageCartTemplatesPageList.clickItemToggle(row);
		return this.ensurePageLoadedWithoutSplitView();
	}

	public ManageCartTemplatesPage clickCloseSplitViewButton() {
		manageCartTemplatesPageSplitView.closeSplitView();
		return this.ensurePageLoadedWithoutSplitView();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public ManageCartTemplatesPage verifyTemplateInList(String name, String library, boolean expected) {
		log.info("Verify if the template '" + name + "' with library '" + library + "' is displayed in the list");
		GenUtils.verifyResult(isTemplateExists(name, library), expected);
		return this.ensurePageLoaded();
	}
	
	public ManageCartTemplatesPage verifyTemplateInViewMode(boolean expected) {
		log.info("Verify the template is in view mode");
		GenUtils.verifyResult(!su.isElementExist(LocatorType.Xpath, editName), expected);
		GenUtils.verifyResult(!su.isElementExist(LocatorType.Xpath, chooseOrderingLibraryLocator), expected);
		return this.ensurePageLoaded();
	}
	
	@Override
	public ManageCartTemplatesPage verifyMsgAppears(RialtoMessage expectedMessage, String... placeHolders) {
		super.verifyMsgAppears(expectedMessage, placeHolders);
		return this;
	}
	
	public ManageCartTemplatesPage verifyAlertAppears(RialtoAlert notification) {
		log.info("Verify the alert '" + notification.getName() + "' appears");
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(notification.getCode())));
		return this.ensurePageLoaded();
	}

	public ManageCartTemplatesPage verifyWarningInTemplate(int row, boolean expected) {
		manageCartTemplatesPageList.verifyWarningInItem(row, expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifyTemplateEnabled(int row, boolean expected) {
		manageCartTemplatesPageList.verifyToggleEnabled(row, expected);
		return this;
	}

	public ManageCartTemplatesPage verifyLibraryWarning(boolean expected) {
		manageCartTemplatesPageSplitView.verifyTemplateLibraryWarning(expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifyTemplateLibrary(String expected) {
		log.info("Verify the template's library is " + expected);
		GenUtils.verifyResult(getTemplateLibrary(), expected);
		return this;
	}

	public ManageCartTemplatesPage verifyTemplateFocused(int row, boolean expected) {
		manageCartTemplatesPageList.verifyItemFocused(row, expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifyShareTemplateCheckboxSelected(boolean expected) {
		manageCartTemplatesPageSplitView.verifyCheckboxSelected(SplitViewCheckbox.ShareTemplate, expected);
		return this;
	}

	public ManageCartTemplatesPage verifyListSorted(RialtoSort option) {
		log.info("Verify the list is sorted by " + option.getName());
		manageCartTemplatesPageList.verifyListSorted(option);
		return this;
	}
	
	public ManageCartTemplatesPage verifyListLength(int expected) {
		log.info("Verify the list length is: " + expected);
		GenUtils.verifyResult(manageCartTemplatesPageList.getPageSize(), expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifyNumberOfTemplates(int expected) {
		log.info("Verify the total number of templates is: " + expected);
		GenUtils.verifyResult(getTotalNumberOfTemplates(), expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifySelectedPaginationPage(int expected) {
		super.verifySelectedPaginationPage(expected);
		return this;
	}
	
	public ManageCartTemplatesPage verifyEmptyPage() {
		log.info("Verify the page is empty");
		waitSmall.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(emptyPage)));
		return this;
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public boolean isTemplateExists(String name, String library) {		
		int numberOfTemplatesInPage = manageCartTemplatesPageList.getPageSize();
		for(int row = 0; row < numberOfTemplatesInPage; row++) {
			clickTemplateInRow(row);
			if(getTemplateName(row).equals(name) && getTemplateLibrary().equals(library))
				return true;
		}
		return false;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	private int getTotalNumberOfTemplates() {
		String totalNumberOfTemplates = su.getElementAndGetText(LocatorType.Xpath, numberOfResults);
		int length = totalNumberOfTemplates.length();
		totalNumberOfTemplates = totalNumberOfTemplates.substring(length - 3, length - 1).trim();
		log.info("The total number of template is " + totalNumberOfTemplates);
		return Integer.parseInt(totalNumberOfTemplates);
	}
	
	public String getTemplateName(int row) {
		return manageCartTemplatesPageList.getTemplateName(row);
	}
	
	public String getTemplateLibrary() {
		return su.getElementAndGetText(LocatorType.Xpath, libraryValue);
	}
}