package pages.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.common.Button;
import util.components.rialto.RialtoTable;
import util.interfaces.GeneralInterface.Nameable;

public class ScheduledPurchasePage extends RialtoInternalPage {

    public ScheduledPurchasePage(MainPageManager pages) {
        super(pages);
    }

    @Override
    public ScheduledPurchasePage ensurePageLoaded() {
        ensurePageLoadedEmptyList();
        return this;
    }

    public ScheduledPurchasePage ensurePageLoadedEmptyList() {
        super.ensurePageLoaded();
        SPPlansTable.ensureTableLoadedBasic();
        waitMedium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createNewPlan)));
        return this;
    }

    public enum FilterListOption implements Nameable {
        All("All"), Inactive("Inactive"), Active("Active");

        private String name;

        FilterListOption(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    };

    private RialtoTable SPPlansTable = new RialtoTable(pages);

    protected Button add = new Button(pages, "//button[contains(@class, 'sel-ex-button') and text()= 'Add']",
            LocatorType.Xpath);
    private String filterLists = "//ex-filters//i[contains(@class, 'sel-combobox-arrow')]";
    private String removeBtn = "//button[text() = 'Remove']";
    private String createNewPlan = "//button[contains(@class, 'sel-ex-button') and text()=' Add New Plan ']";
    private String listName = "//ex-link/a[contains(text(), '{0}')]";
    private String newPlanName = "//div[@class = 'choose-name-input']//input[contains(@class,'sel-search-input')]";
    private String numberOfLists = "//span[contains(@class, 'sel-pagination-summary')]";

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////// GENERAL METHODS //////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public EditSPSuperPage createNewPlan(String name) {
        log.info("Create a new list");
        su.waitElementClickableAndClick(LocatorType.Xpath, createNewPlan);
        log.info("Set List Name to: '" + name + "'");
        su.getElementAndClick(LocatorType.Xpath, newPlanName);
        su.clearAndSendKeys(name, LocatorType.Xpath, newPlanName);
        add.click();
        return pages.rialto.editSPSuperPage.ensurePageLoaded();
    }

    public ScheduledPurchasePage filterLists(FilterListOption category) {
        log.info("Filter the lists by " + category);
        su.waitElementClickableAndClick(LocatorType.Xpath, filterLists);
        dropdown.setText(category.getName());
        return this.ensurePageLoadedEmptyList();
    }

    public ScheduledPurchasePage editEmptyList(String name) {
        log.info("Edit empty list " + name);
        su.getElementAndClick(LocatorType.Xpath, listName, name);
        return pages.rialto.scheduledPurchasePage.ensurePageLoadedEmptyList();
    }

    public ScheduledPurchasePage editList(String name) {
        log.info("Edit list " + name);
        su.getElementAndClick(LocatorType.Xpath, listName, name);
        return pages.rialto.scheduledPurchasePage.ensurePageLoaded();
    }

    public ScheduledPurchasePage removeList(String listName) {
        log.info("Remove list: " + listName);
        SPPlansTable.clickMoreActionsByValue(listName);
        su.waitElementClickableAndClickJS(LocatorType.Xpath, removeBtn);
        return this.ensurePageLoadedEmptyList();
    }

    public ScheduledPurchasePage selectPagination(RialtoPaginationOption paginationOption) {
        super.selectPagination(paginationOption);
        return this.ensurePageLoaded();
    }

    public ScheduledPurchasePage jumpToPageFixed(String pageNum) {
        super.jumpToPageFixed(pageNum);
        return this.ensurePageLoaded();
    }
    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////// VALIDATIONS //////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public ScheduledPurchasePage verifyListExist(String name, boolean expected) {
        log.info("Verify if the list '" + name + "' exists");
        GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, listName, name), expected);
        return this;
    }

    public ScheduledPurchasePage verifyNumberOfLists(int expected) {
        log.info("Verify number of lists is " + expected);
        GenUtils.verifyResult(getNumberOfListsInPageSummary(), expected);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////// GETTERS /////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public int getNumberOfListsInPageSummary() {
        return Integer.parseInt(
                su.getElementAndGetText(LocatorType.Xpath, numberOfLists).split(" of ")[1].replaceAll("\\D+", ""));
    }

    public int getListLength() {
        return SPPlansTable.getListLength();
    }
}