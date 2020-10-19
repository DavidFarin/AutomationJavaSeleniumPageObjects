package pages.rialto.dlg;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.rialto.CartPage;
import util.SelUtils.LocatorType;

public class RialtoDlgChangeOwningLibrary extends RialtoDlgSuperPage {


	public RialtoDlgChangeOwningLibrary(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoDlgChangeOwningLibrary ensurePageLoaded() {
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(change)));
		return this;
	}

	private String change = "//button/span[text() = 'Change']";

	public CartPage clickChange() {
		log.info("Click on Change button");
		su.getElementAndClickJS(LocatorType.Xpath, change);
		return pages.rialto.cartPage.ensurePageLoadedWithoutSplitView();
	}
}
