package util.components.rialto.rialtoPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import models.rialto.RialtoBib;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.components.rialto.rialtoList.HoldingsPanelListComponent;
import pages._pages_mngt.MainPageManager;

public class HoldingsPanel extends RialtoPanelSuperPage {

	public HoldingsPanel(MainPageManager pages) {
		super(pages);
	}
	
	@Override
	public HoldingsPanel ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath(holdingsSpinner))));
		return this;
	}

	private HoldingsPanelListComponent panelList = new HoldingsPanelListComponent(pages);
	private String holdingsSpinner = "//mat-spinner[contains(@class, 'sel-holdings-list-spinner')]";
	private String availableFor = "//ex-component-loader[contains(@class, 'sel-afAvailabality')]";
	private String groupName = availableFor + "//span[contains(@class, 'sel-af-group-name')]";
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public HoldingsPanel showHoldingsPanel() {
		super.showPanelIfNotVisible(RialtoPanels.Holdings);
		return this.ensurePageLoaded();
	}
	
	public HoldingsPanel showAvailableForSessionIfClosed() {
		log.info("Open available for in holding panel if closed");
		if (!su.isElementVisible(LocatorType.Xpath, groupName))
			su.getElementAndClick(LocatorType.Xpath, availableFor);
		return this;
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void verifyBibInHoldingsPanel(RialtoBib bib, boolean expected) {
		panelList.verifyBibInList(bib, expected);
	}
	
	public void verifyGroupName(String name) {
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, groupName), name);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public int getListLength() {
		int listLengh = panelList.getPageSize(); 
		log.info("The Holdings Panel list length is: " + listLengh);
		return listLengh;
	}
	
	public RialtoBib getBib(int row) {
		return panelList.getBib(row);
	}	
}