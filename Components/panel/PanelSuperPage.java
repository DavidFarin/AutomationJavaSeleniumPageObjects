package util.components.rialto.rialtoPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Nameable;

public class RialtoPanelSuperPage extends AnyPage{

	public RialtoPanelSuperPage(MainPageManager pages) {
		super(pages);
	}
	
	@Override
	public RialtoPanelSuperPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitVeryBig.until(ExpectedConditions.elementToBeClickable(By.xpath(pinPanel)));
		return this;
	}
	
    public enum RialtoPanels implements Nameable {
        Holdings("HOLDINGS"),
        PurchaseRequest("PURCHASE REQUEST"),
        List("LIST"),
        Feed("FEED"),
        ScheduledPurchase("SCHEDULED PURCHASE");

        private String name;

        RialtoPanels(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    };

	protected String closePanel = "//mat-sidenav//i[contains(@class, 'resize-small')]";			//NGS-3453
	protected String openPanel = "//span[contains(text(),'{0}')]/../../..//i[contains(@class, 'resize-large')]";	//NGS-3453
	private String pinPanel = "//mat-sidenav//i[contains(@class, 'pin')]";						//NGS-3453

	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void closePanelIfVisible() {
		log.info("Close panel if visible");
		if(su.isElementVisible(LocatorType.Xpath, pinPanel)) {
			su.getElementAndClickJS(LocatorType.Xpath, closePanel);
		}
		su.waitElementInvisible(LocatorType.Xpath, pinPanel);
	}
	
	protected void showPanelIfNotVisible(RialtoPanels panelName) {
		log.info("Show panel '" + panelName.getName() + "' if not visible");
		if(!su.isElementVisible(LocatorType.Xpath, pinPanel))
			su.waitElementClickableAndClickJS(LocatorType.Xpath, openPanel, panelName.getName());
		su.waitElementVisible(LocatorType.Xpath, pinPanel);
	}
}
