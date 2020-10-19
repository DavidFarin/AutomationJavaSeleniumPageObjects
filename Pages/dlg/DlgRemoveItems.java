package pages.rialto.dlg;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.rialto.CartPage;
import util.SelUtils.LocatorType;

public class RialtoDlgRemoveItems extends RialtoDlgSuperPage {

	public RialtoDlgRemoveItems(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoDlgRemoveItems ensurePageLoaded() {
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(remove)));
		return this;
	}

	public CartPage clickRemove() {
		log.info("Click on Remove button");
		su.getElementAndClick(LocatorType.Xpath, remove);
		return pages.rialto.cartPage.ensurePageLoadedWithoutSplitView();
	}
}
