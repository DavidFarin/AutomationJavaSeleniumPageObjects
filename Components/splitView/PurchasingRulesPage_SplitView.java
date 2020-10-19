package util.components.rialto.splitView;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.angular.AngDropdown;
import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.GenUtils.Compare;
import util.SelUtils.LocatorType;
import util.components.rialto.RialtoDropdown;
import util.interfaces.GeneralInterface.Nameable;


public class PurchasingRulesPage_SplitView extends RialtoSplitView {

	
	
public PurchasingRulesPage_SplitView(MainPageManager pages) {
			super(pages);
		}

		@Override
		public PurchasingRulesPage_SplitView ensurePageLoaded() {
			super.ensurePageLoaded();
			return this;
		}

		public enum PRSplitViewField implements Nameable {
			Remove("//ex-item-details-header//button[contains(@class, 'sel-record-actions-button')]//span[text()='Remove']"),
			Title("//a[contains(@class,'cart-details-title')]"),
			Price("//cart-details//span[contains(@class,'sel-rialto-price')]"),
			FundValue("//fund-item//span[contains(@class, 'sel-combobox-selected-value') and contains(text(), '{0}')]"),
			FundWarning("//fund-item//button[contains(@class, 'sel-combobox-warning')]"),
			ChooseLocation("//button//span[text() = ' Choose location']"),
			LocationValue("//locations-combobox//span[contains(@class, 'sel-combobox-selected-value')]"),
			LocationWarning("//location-item//button[contains(@class, 'sel-combobox-warning')]"),
			LibraryWarning("//ex-combobox[contains(@class, 'sel-ordering-library-combobox')]//button[contains(@class, 'sel-combobox-warning')]"),
			QuantityValue("//qty//span[contains(@class, 'sel-combobox-selected-value')and contains(@class, '{0}')]"),
			QuantityOption("//mat-option/span[text()= ' {0} ']");

			private String name;

			PRSplitViewField(String name) {
				this.name = name;
			}

			@Override
			public String getName() {
				return name;
			}
		};
		
	private AngDropdown orderingLibrary = new AngDropdown(pages,
			"//div[contains(@class, 'ng-tns-c202-81')]//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);
		private RialtoDropdown dropdown = new RialtoDropdown(pages);
		private AngDropdown chooseQuantity = new AngDropdown(pages, "//qty//button[contains(@class, 'sel-ex-combobox')]", LocatorType.Xpath);
		private AngDropdown chooseFund = new AngDropdown(pages, "//fund-item//button[contains(@class, 'sel-ex-combobox-selected-button')]", LocatorType.Xpath);

		
		
		
		//////////////////////////////////////////////////////////////////////////
		/////////////////////////// GENERAL METHODS //////////////////////////////
		//////////////////////////////////////////////////////////////////////////

		public void removeItem() {
			log.info("Remove item");
			su.waitElementClickableAndClick(LocatorType.Xpath, PRSplitViewField.Remove.getName());
		}
		
		public void chooseFund(String fundName) {
			log.info("Choose fund: " + fundName);
			chooseFund.setBySendingKeys(fundName);
		}

		public void chooseLocation(String locationName) {
			log.info("Choose location: " + locationName);
			su.waitElementClickableAndClick(LocatorType.Xpath, PRSplitViewField.ChooseLocation.getName());
			dropdown.setText(locationName);
		}

	public PurchasingRulesPage_SplitView selectOrderingLibrary(String library) {
		log.info("Choose Ordering Library: " + library);
		orderingLibrary.setBySendingKeys(library);
		return this.ensurePageLoaded();
	}
		public void chooseQuantity(int quantity) {
			log.info("Choose quantity: " + quantity);
			chooseQuantity.setBySendingKeys(String.valueOf(quantity));
		}
		
		//////////////////////////////////////////////////////////////////////////
		/////////////////////////////// VALIDATIONS //////////////////////////////
		//////////////////////////////////////////////////////////////////////////

		public void verifyTemplateLibraryWarning(boolean expected) {
			log.info("Verify library warning in Template");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, PRSplitViewField.LibraryWarning.getName()), expected);
		}
		
		public void verifyLocationWarning(boolean expected) {
			log.info("Verify location warning");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, PRSplitViewField.LocationWarning.getName()), expected);
		}

		public void verifyFundWarning(boolean expected) {
			log.info("Verify fund warning");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, PRSplitViewField.FundWarning.getName()), expected);
		}
		
		public void verifyQuantity(String expected) {
			log.info("Verify the quantity is: " + expected);
			GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, PRSplitViewField.QuantityValue.getName(), ""), expected);
		}

		public void verifyQuantityViewMode() {
			log.info("Verify the quantity is in View Mode");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, PRSplitViewField.QuantityValue.getName(), "ex-combobox-view-mode"), true);
		}
		
		public void verifyLocation(String expected) {
			log.info("Verify the location is: " + expected);
			GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, PRSplitViewField.LocationValue.getName()), expected, Compare.CONTAINS);
		}
		
		public void verifyFund(String expected) {
			log.info("Verify the fund is: " + expected);
			waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(PRSplitViewField.FundValue.getName(), expected))));
		}
		
		//////////////////////////////////////////////////////////////////////////
		//////////////////////////////// GETTERS /////////////////////////////////
		//////////////////////////////////////////////////////////////////////////

		public String getField(PRSplitViewField expectedField) {
			if (su.isElementExist(LocatorType.Xpath, expectedField.getName())) {
				return su.getElementAndGetText(LocatorType.Xpath, expectedField.getName());
			}
			return "";
		}

}
