package pages.rialto.dlg;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.rialto.ManageCartTemplatesPage;
import util.SelUtils.LocatorType;

public class RialtoDlgRemoveTemplates extends RialtoDlgSuperPage {

	public RialtoDlgRemoveTemplates(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoDlgRemoveTemplates ensurePageLoaded() {
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(remove)));
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(confirmationMessage)));
		return this;
	}
	
	private String confirmationMessage = "//mat-dialog-content//h3[contains(text(), 'Remove selected')]";

	public ManageCartTemplatesPage remove() {
		log.info("Click on Remove button");
		su.getElementAndClick(LocatorType.Xpath, remove);
		return pages.rialto.manageCartTemplatesPage.ensurePageLoadedWithoutSplitView();
	}
}
