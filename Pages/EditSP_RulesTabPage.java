package pages.rialto;

import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;
import util.components.common.Button;

public class EditSP_RulesTabPage extends RialtoInternalPage {

    private Button upperAddNewRule = new Button(pages,
            "//button[contains(@class, 'sel-ex-button') and text()= ' Add New Rule ']", LocatorType.Xpath);
    private Button innerAddNewRule = new Button(pages,
            "//button[contains(@class, 'sel-ex-button') and text()= ' Add purchasing rule']", LocatorType.Xpath);
    private String removeRule = "//button[contains(@class, 'sel-record-actions-button')]//span[text()= 'Remove']";
    private String upperRuleButton = "//button[contains(@class, 'sel-ex-button') and text()= ' Add New Rule ']";
	private String ruleInRow = "//div[contains (@class , 'sel-row-{0}-item')]";


    public EditSP_RulesTabPage(MainPageManager pages) {
        super(pages);
    }

    @Override
    public EditSP_RulesTabPage ensurePageLoaded() {
        su.waitElementVisible(LocatorType.Xpath, upperRuleButton);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// CLICKS /////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public void clickUpperAddRule() {
        this.ensurePageLoaded();
        upperAddNewRule.click();
    }

    public void clickRemoveRule() {
        su.waitElementClickableAndClickJS(LocatorType.Xpath, removeRule);
    }

    public void clickInnerAddRule() {
        this.ensurePageLoaded();
        innerAddNewRule.click();
    }
}
