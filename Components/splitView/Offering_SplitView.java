package util.components.rialto.splitView;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.regex.Pattern;
import util.GenUtils.Compare;
import models.acq.POL.POLStatus;
import models.rialto.RialtoItem;
import util.GenUtils;
import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Codeable;

public class Offering_SplitView extends RialtoSplitView {

	public Offering_SplitView(MainPageManager pages) {
		super(pages);
	}

	@Override
	public Offering_SplitView ensurePageLoaded() {
		super.ensurePageLoaded();
		waitVeryBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(offeringSplitViewLoadingBlocker)));
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(horizontalLoadingBlocker)));
		try{
			waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(OfferingSplitView.Title.getCode()))));
		}catch(Exception e){
			waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OfferingSplitView.DismissedOffer.getCode())));
		}
		return this;
	}

	public enum OfferingSplitView implements Codeable {
		Title("//span[contains(@class, 'sel-offering-title')]"),
		Isbn10("//span[@class='sel-isbn-10']"),
		Isbn13("//span[@class='sel-isbn-13']"),
		Quantity("//span[contains(@class, 'sel-price')]/span/span"),
		Price("//div[contains(@class, 'ex-list-layout-right')]//span[contains(@class,'sel-rialto-price')]"),
		Author("//span[contains(@class,'sel-offering-author')]"),
		Binding("//span[contains(@class, 'sel-binding')]"),
		Publisher("//span[@class='sel-publisher']"),
		PublicationYear("//span[@class='sel-pubyear']"),
		WorkTitle("//span[@class='work-details-title']"),
		Platform("//span[contains(@class, 'sel-platform')]"),
		PolReference("//nav-to-alma[contains(@class, 'sel-poline-ref')]/a"),
		PolStatus("//span[contains(@class, 'sel-poline-status')]"),
		PoReference("//nav-to-alma[contains(@class, 'sel-po-ref')]/a"),
		Selector("//div[contains(@class, 'sel-order-history-details')]//span[contains(@class, 'sel-details-selected-by') and contains(text(), '{0}')]"),
		Approver("//span[contains(@class, 'sel-details-approved-by') and contains(text(), '{0}')]"),
		ApprovedBy("//div[contains(@class, 'sel-order-history-details')]//span[text() = ' Approved by']/.."),
		RejectedBy("//div[contains(@class, 'sel-order-history-details')]//span[text() = ' Rejected by']/.."),
		AlmaOrderCreatedStatusTracking("//div[contains(@class, 'sel-tracking') and text() = 'Alma order created']"),
		DismissedOffer("//alma-icon[contains(@class, 'sel-dismissed-offer-icon')]"),
		RejectReason("//div[contains(@class, 'sel-reject-reason')]");

		private String code;

		OfferingSplitView(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	};

	private String offeringSplitViewLoadingBlocker = "//div[contains(@class, 'offering-details-spinner')]";
	private String horizontalLoadingBlocker = "//div[@class='syndeticsunbound_shimmer']";
	private String addToCart = "//ex-item-details-header//button[contains(@class,'sel-record-actions-button')]//span[text()='Add to Cart']";
	private String addToCartDisabled = "//div[contains(@class, 'ex-list-layout-right')]//button[contains(@class, 'ex-record-actions-disabled')]//span[text()='Add to Cart']";
	private String moreActions = "//ex-item-details-header//button[contains(@class,'sel-record-actions-more-actions')]";
	private String restore = "//button[contains(@class, 'sel-ex-button')]//label[text()=' Restore ']";
	private String refreshOrderStatus = "//ex-icon[contains(@class, 'sel-refresh-button')]//i";

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public Offering_SplitView clickAddToCart() {
		su.getElementAndClickJS(LocatorType.Xpath, addToCart);
		return this.ensurePageLoaded();
	}
	
	public void clickMoreActions() {
		log.info("Click on More Actions button");
		su.waitElementClickableAndClick(LocatorType.Xpath, moreActions);
	}
	
	public void clickPol() {
		log.info("Click on the POL reference");
		su.waitElementClickableAndClick(LocatorType.Xpath, OfferingSplitView.PolReference.getCode());
	}
	
	private Offering_SplitView refreshOrderStatus() {
		log.info("Refresh order status");
		su.waitElementClickableAndClick(LocatorType.Xpath, refreshOrderStatus);
		return this.ensurePageLoaded();
	}
	
	public Offering_SplitView clickRestore() {
		su.waitElementClickableAndClick(LocatorType.Xpath, restore);
		return this.ensurePageLoaded();
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void verifySelectedBy(String selector) {
		log.info("Verify the item was selected by: " + selector);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, OfferingSplitView.Selector.getCode(), selector), true);
	}
	
	public void verifyApprovedBy(String approver) {
		log.info("Verify the item was approved by: " + approver);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath,  OfferingSplitView.ApprovedBy.getCode() + 
																	OfferingSplitView.Approver.getCode(), approver), true);
	}
	
	public void verifyRejectedBy(String approver) {
		log.info("Verify the item was rejected by: " + approver);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath,  OfferingSplitView.RejectedBy.getCode() + 
																	OfferingSplitView.Approver.getCode(), approver), true);
	}
	
	public void verifyPolCreated() {
		log.info("Verify the POL was created");
		int count = 0;
		String pol =  getPolReference();
		while(pol.equals("") && count < 60) {
			refreshOrderStatus();
			GenUtils.sleepSeconds(5);
			count++;
			pol =  getPolReference();
		}
		GenUtils.verifyResult(pol, "", Compare.DIFFERENT);
	}
	
	public void verifyPolStatus(POLStatus status) {
		log.info("Verify the Pol Status is '" + status.getName() + "'");
		int count = 0;
		String polStatus =  getPolStatus();
		while(!polStatus.equals(status.getName()) && count < 60) {
			refreshOrderStatus();
			GenUtils.sleepSeconds(5);
			count++;
			polStatus =  getPolStatus();
		}
		GenUtils.verifyResult(polStatus, status.getName());
	}
	
	public void verifyPoCreated() {
		log.info("Verify the PO was created");
		int count = 0;
		String po =  getPoReference();
		while(po.equals("") && count < 60) {
			refreshOrderStatus();
			GenUtils.sleepSeconds(5);
			count++;
			po =  getPoReference();
		}
		GenUtils.verifyResult(po, "", Compare.DIFFERENT);
	}
	
	public void verifyAlmaOrderCreated() {
		log.info("Verify the 'Alma Order Created' badge is displayed");
		int count = 0;
		boolean statusTracking = su.isElementExist(LocatorType.Xpath, OfferingSplitView.AlmaOrderCreatedStatusTracking.getCode());
		while(!statusTracking && count < 60) {
			refreshOrderStatus();
			GenUtils.sleepSeconds(5);
			count++;
			statusTracking = su.isElementExist(LocatorType.Xpath, OfferingSplitView.AlmaOrderCreatedStatusTracking.getCode());
		}
		GenUtils.verifyResult(statusTracking, true);
	}
	
	public void verifyRejectReason(String expected) {
		log.info("Verify the 'Reject Reason' is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.RejectReason.getCode()), expected);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public String getField(OfferingSplitView expectedField) {
		if (su.isElementExist(LocatorType.Xpath, expectedField.getCode())) {
			switch (expectedField) {
				case PublicationYear:
					return su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.PublicationYear.getCode()).split(",")[0];
				case Publisher:
					return su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.Publisher.getCode());
				case WorkTitle:
					return su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.WorkTitle.getCode()).split(Pattern.quote(" (1 - "))[0];
				case Isbn13:
					return su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.Isbn13.getCode()).split(" ")[1];
				case Title:
				case Author:
				case Price:
				case Binding:
					return su.getElementAndGetText(LocatorType.Xpath, expectedField.getCode());
				case Platform:
					return su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.Platform.getCode()).substring(10);
			}
		}
		return "";
	}

	public RialtoItem getItem() {
		log.info("Get details of item in Split View");
		return new RialtoItem(getField(OfferingSplitView.WorkTitle), getField(OfferingSplitView.Title), getField(OfferingSplitView.Author),
				getField(OfferingSplitView.PublicationYear), getField(OfferingSplitView.Publisher),	getField(OfferingSplitView.Binding), 
				getField(OfferingSplitView.Isbn13), getField(OfferingSplitView.Price), getField(OfferingSplitView.Platform));
	}
	
	public String getPolReference() {
		String polReference = "";
		if(su.isElementExist(LocatorType.Xpath, OfferingSplitView.PolReference.getCode()))
			polReference = su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.PolReference.getCode());
		log.info("The POL Reference is: " + polReference);
		return polReference;
	}
	
	public String getPolStatus() {
		String status = "";
		if(su.isElementExist(LocatorType.Xpath, OfferingSplitView.PolStatus.getCode()))
				status = su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.PolStatus.getCode());
		log.info("The POL status is: " + status);
		return status;
	}
	
	public String getPoReference() {
		String poReference = "";
		if(su.isElementExist(LocatorType.Xpath, OfferingSplitView.PoReference.getCode()))
			poReference = su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.PoReference.getCode());
		log.info("The PO Reference is: " + poReference);
		return poReference;
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyAddToCartButtonIsDisabled() {
		log.info("Verify the 'Add to Cart' button is disabled in Split View");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, addToCartDisabled), true);
	}
	
	public void verifyDismissedOfferNotificationExists() {
		log.info("Verify the 'Dismissed Offer' notification exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, OfferingSplitView.DismissedOffer.getCode()), true);
	}
	
	public void verifyQuantity(String expected) {
		log.info("Verify the quantity is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, OfferingSplitView.Quantity.getCode()).split(" ")[0], expected);
	}
}