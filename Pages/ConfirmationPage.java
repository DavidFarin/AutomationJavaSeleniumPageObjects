package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Nameable;

public class ConfirmationPage extends RialtoInternalPage {

	public ConfirmationPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public ConfirmationPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.presenceOfElementLocated(By.xpath(confirmationMessage)));
		return this;
	}

	public enum ConfirmationMessage implements Nameable {
		YourOrderWasSentToApproval("Thank you! Your order was sent to approval."),
		YourOrderWasPlaced("Thank you! Your order was placed."),;

		private String name;

		ConfirmationMessage(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	private String confirmationMessage = "//span[@class = 'cart-confirmation-box-title']";
	private String viewItInOrderHistory = "//div[@class='cart-confirmation-box-links']/a[text()='View it in order history']";
	private String backToCartsList = "//a[text()='Back to Carts List']";

	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public OrderHistoryPage clickViewItInOrderHistory() {
		log.info("Click on 'View it in order history' link");
		su.getElementAndClick(LocatorType.Xpath, viewItInOrderHistory);
		return pages.rialto.orderHistoryPage.ensurePageLoaded();
	}
	
	public CartsListPage clickBackToCartsList() {
		log.info("Click on 'Back to Carts List'");
		su.getElementAndClick(LocatorType.Xpath, backToCartsList);
		return pages.rialto.cartsListPage.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public ConfirmationPage verifyConfirmationMessage(ConfirmationMessage message) {
		log.info("Verify the confirmation message");
		GenUtils.verifyResult(message.getName(), su.getElementAndGetText(LocatorType.Xpath, confirmationMessage));
		return this;
	}

}