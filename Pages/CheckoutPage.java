package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.SelUtils.LocatorType;
import pages._pages_mngt.MainPageManager;

public class CheckoutPage extends RialtoInternalPage {

	public CheckoutPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public CheckoutPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitMedium.until(ExpectedConditions.elementToBeClickable(By.xpath(confirm)));
		return this;
	}

	private String confirm = "//ex-button[contains(@class, 'cart-confirm-btn')]/button[text()=' Confirm']";
	private String backToCart = "//div[contains(@class, 'cart-checkout-buttons')]//a";

	public ConfirmationPage confirm() {
		log.info("Confirm checkout");
		su.waitElementClickableAndClick(LocatorType.Xpath, confirm);
		return pages.rialto.confirmationPage.ensurePageLoaded();
	}

	public CartPage clickBackToCart() {
		log.info("Click on button Back to cart");
		su.getElementAndClick(LocatorType.Xpath, backToCart);
		return pages.rialto.cartPage.ensurePageLoadedWithoutSplitView();
	}
}
