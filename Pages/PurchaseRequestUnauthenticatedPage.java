package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.ElementType;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.CodeNameable;

public class PurchaseRequestUnauthenticatedPage extends AnyPage {

	public PurchaseRequestUnauthenticatedPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public PurchaseRequestUnauthenticatedPage ensurePageLoaded() {
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(submitRequest))); 
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(submitSpinner)));
		return this;
	}
	
	public enum FormTextField implements CodeNameable {
		Title("Tile", "sel-form-title"),
		Author("Author", "sel-form-author"),
		Edition("Edition", "sel-form-edition"),
		Isbn("ISBN", "sel-form-isbn"),
		Lccn("lccn", "sel-form-lccn"),
		OtherSystemNumber("Other system number", "sel-form-other-system-number"),
		Publisher("Publisher", "sel-form-publisher"),
		PublicationYear("Publication Year", "sel-form-publication-year"),
		PlaceOfPublication("Place of publication", "sel-form-place-of-publication"),
		Volume("Volume", "sel-form-volume");

		private String code, name;

		FormTextField(String name, String code) {
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

		public ElementType getType() {
			return ElementType.Label;
		}
	}
	
	public enum PurchaseRequestFormat implements CodeNameable {
		Electronic("Electronic", "//form//div[contains(@class, 'sel-e-format')]//input/.."),
		Physical("Physical", "//form//div[contains(@class, 'sel-p-format')]//input/..");

		private String code, name;

		PurchaseRequestFormat(String name, String code) {
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

		public ElementType getType() {
			return ElementType.Label;
		}
	}	
	
	private String textField = "//input[contains(@class, '{0}')]";
	private String myName = "//input[@placeholder = 'My name']";
	private String email = "//input[@placeholder = 'Email']";
	private String submitRequest = "//button[contains(@class, 'sel-form-submit-button')]";
	private String submitRequestDisabled = "//button[contains(@class, 'sel-form-submit-button') and @disabled = 'true']";
	private String submitSpinner = submitRequest + "//mat-spinner";
	private String purchaseRequestSuccessfullyCreated = "//span[text()='Purchase request successfully created']";
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public PurchaseRequestUnauthenticatedPage selectFormat(PurchaseRequestFormat format) {
		log.info("Choose format: " + format.getName());
		su.waitElementClickableAndClick(LocatorType.Xpath, format.getCode());
		return ensurePageLoaded();
	}
	
	public PurchaseRequestUnauthenticatedPage setTextbox(FormTextField textbox, String value) {
		log.debug("Set: " + textbox.name() + " to: " + value);
		switch (textbox) {
			case Title:
			case Author:
			case Edition:
			case Isbn:
			case Lccn:
			case OtherSystemNumber:
			case Publisher:
			case PublicationYear:
			case PlaceOfPublication:
			case Volume:
				su.clearAndSendKeys(value, LocatorType.Xpath, textField, textbox.getCode());
				break;
			default:
				log.info("Please select a text field");
				break;
		}
		return ensurePageLoaded();
	}
	
	public PurchaseRequestUnauthenticatedPage setMyName(String value) {
		log.info("Set 'My name' to: " + value);
		su.clearAndSendKeys(value, LocatorType.Xpath, myName, value);
		return ensurePageLoaded();
	}
	
	public PurchaseRequestUnauthenticatedPage setEmail(String value) {
		log.info("Set 'Email' to: " + value);
		su.clearAndSendKeys(value, LocatorType.Xpath, email, value);
		return ensurePageLoaded();
	}
	
	public PurchaseRequestUnauthenticatedPage submitRequest() {
		log.info("Submit Request");
		su.waitElementClickableAndClick(LocatorType.Xpath, submitRequest);
		return ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public PurchaseRequestUnauthenticatedPage verifySubmitRequestDisabled(boolean expected) {
		log.info("Verify if the 'Submit Request' button is disabled");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, submitRequestDisabled), expected);
		return ensurePageLoaded();
	}
	
	public PurchaseRequestUnauthenticatedPage verifyNotificationPurchaseRequestCreated() {
		log.info("Verify the notification 'Purchase request successfully created' appears");
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(purchaseRequestSuccessfullyCreated)));
		return ensurePageLoaded();
	}
}
