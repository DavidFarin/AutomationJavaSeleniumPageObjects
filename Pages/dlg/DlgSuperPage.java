package pages.rialto.dlg;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.rialto.CartPage;
import pages.super_pages.AnyPage;
import util.SelUtils.LocatorType;

public class RialtoDlgSuperPage extends AnyPage {

	public RialtoDlgSuperPage(MainPageManager pages) {
		super(pages);
	}
	
	public RialtoDlgSuperPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(cancel)));
		return this;
	}

	public String cancel = "//button/span[text()='Cancel']";
	protected String remove = "//button/span[text()='Remove']";

	public CartPage clickCancel() {
		log.info("Click on Cancel button");
		su.getElementAndClickJS(LocatorType.Xpath, cancel);
		return pages.rialto.cartPage.ensurePageLoadedWithoutSplitView();
	}
}
