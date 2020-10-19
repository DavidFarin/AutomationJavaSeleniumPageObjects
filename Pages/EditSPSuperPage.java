package pages.rialto;

import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;
import util.components.common.Button;
import util.components.rialto.rialtoPanel.ScheduledPurchasePanel;

public class EditSPSuperPage extends RialtoInternalPage {

    private String queryTab = "//span[contains(@class, 'sel-tab-ng.scheduled-purchases.purchasing_query')]";
    private String rulesTab = "//span[contains(@class, 'sel-tab-ng.scheduled-purchases.purchasing_rules')]";
    private String savePlanButton = "//button[contains(@class, 'sel-ex-button') and text()= ' Save ']";
    private Button saveButton = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()= ' Save ']",
            LocatorType.Xpath);
    private String activatePlan = "//div[contains(@class, 'mat-slide-toggle-ripple')]";
    private ScheduledPurchasePanel spPanel = new ScheduledPurchasePanel(pages);




    public EditSPSuperPage(MainPageManager pages) {
            super(pages);
    }

    @Override
    public EditSPSuperPage ensurePageLoaded() {
        su.waitElementVisible(LocatorType.Xpath, savePlanButton);
        return this;
    }

    public EditSPSuperPage closePanelIfVisible() {
        spPanel.closePanelIfVisible();
        return this.ensurePageLoaded();
    }

    public EditSPSuperPage showSPPanel() {
        spPanel.showPanel();
        return this.ensurePageLoaded();
    }

    public EditSPSuperPage clickOnRulesTab() {
        this.ensurePageLoaded();
        su.waitElementClickableAndClick(LocatorType.Xpath, rulesTab);
        return this;

    }

    public EditSPSuperPage clickOnQueryTab() {
        return null;

    }

    public EditSPSuperPage savePlan() {
        saveButton.click();
        return null;

    }

	public EditSPSuperPage activatePlan() {
        this.ensurePageLoaded();
        su.waitElementClickableAndClickJS(LocatorType.Xpath, activatePlan);
		return this.ensurePageLoaded();
    }

}
