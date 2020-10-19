package util.components.rialto.rialtoPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.CodeNameable;

public class ScheduledPurchasePanel extends RialtoPanelSuperPage {

    public ScheduledPurchasePanel(MainPageManager pages) {
        super(pages);
    }

    @Override
    public ScheduledPurchasePanel ensurePageLoaded() {
        super.ensurePageLoaded();
        waitMedium.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//details-summary-line//label[contains(text(), 'Creation date')]")));
        return this;
    }

    public enum ScheduledPurchasePanelDetail implements CodeNameable {
        CreationDate("Creation date", "sel-label"),
        ModificationDate("Modification date", "sel-modification-date"),
        ModificatedBy("Modificated by", "sel-modificated-by");

        private String name, code;

        ScheduledPurchasePanelDetail(String name, String code) {
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
    };

    private String panelDetail = "//details-summary-line//label[contains(text(), 'Creation Date')]";
    private String shareList = "//mat-slide-toggle[contains(@class, 'sel-share-list-toggle')]//input";
    private String addUser = "//button[contains(@class, 'sel-ex-button') and text()=' Add user']";
    private String selectUser = "//div[contains(@class, 'sel-ex-autocomplete')]//input";
    private String userOption = "//mat-option[contains(@class, 'sel-ex-autocomplete-option')]//mark[text()='{0}']";
    private String listOwner = "//div[contains(@class, 'sel-user-badge')]//span[contains(text(),'{0}')]";
    private String removeOwner = listOwner + "/parent::div[contains(@class, 'sel-user-badge')]//ex-close//i";

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////// GENERAL METHODS //////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public ScheduledPurchasePanel setListShared() {
        log.info("Set list as 'Shared'");
        su.waitElementClickableAndClickJS(LocatorType.Xpath, shareList);
        return this.ensurePageLoaded();
    }

    public ScheduledPurchasePanel addOwner(String user) {
        su.waitElementClickableAndClick(LocatorType.Xpath, addUser);
        shareListWithUser(user);
        return this;
    }

    public ScheduledPurchasePanel shareListWithUser(String user) {
        log.info("Share list with user: " + user);
        su.waitElementVisibleAndSendKeys(user, LocatorType.Xpath, selectUser);
        su.waitElementClickableAndClick(LocatorType.Xpath, userOption, user);
        return this.ensurePageLoaded();
    }

    public ScheduledPurchasePanel removeOwner(String user) {
        log.info("Remove the owner: " + user);
        su.waitElementClickableAndClickJS(LocatorType.Xpath, removeOwner, user);
        waitMedium.until(
                ExpectedConditions.invisibilityOfElementLocated(By.xpath(su.createDynamicLocator(listOwner, user))));
        return this.ensurePageLoaded();
    }

    public void removeYourselfAsOwner() {
        log.info("Remove yourself as owner of the list");
        su.waitElementClickableAndClickJS(LocatorType.Xpath, removeOwner, "You");
    }

    //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// CLICKS /////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public ScheduledPurchasePanel showPanel() {
        super.showPanelIfNotVisible(RialtoPanels.ScheduledPurchase);
        return this.ensurePageLoaded();
    }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////// VALIDATIONS //////////////////////////////
    //////////////////////////////////////////////////////////////////////////


}