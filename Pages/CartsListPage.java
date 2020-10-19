package pages.rialto;

import pages._pages_mngt.MainPageManager;
import util.components.rialto.RialtoTable;
import util.GenUtils;
import util.SelUtils.LocatorType;

public class CartsListPage extends RialtoInternalPage {

	public CartsListPage(MainPageManager pages) {
		super(pages);
	}

	@Override
	public CartsListPage ensurePageLoaded() {
		ensurePageLoadedEmptyList();
		cartsListTable.ensureTableLoaded();
		return this;
	}
	
	public CartsListPage ensurePageLoadedEmptyList() {
		super.ensurePageLoaded();
		cartsListTable.ensureTableLoadedBasic();
		backBtn.waitBigElementClickable();
		return this;
	}
	
	private RialtoTable cartsListTable = new RialtoTable(pages);
	
	private String editbutton = "//button[text() = 'Edit']";
	
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////// GENERAL METHODS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public CartPage editCart(int row) {
		log.info("Edit cart in row - " + row);
		cartsListTable.clickMoreActions(row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, editbutton);	
		return pages.rialto.cartPage.ensurePageLoaded();
	}
	
	public CartsListPage removeAllCarts() {
		log.info("Remove all Carts");
		int length = cartsListTable.getListLength();
		for(int i = length; i > 0; i--) {
			editCart(i).skipSelectOrderingLibraryIfExists().rejectAllItems().done();
		}
		return this.ensurePageLoadedEmptyList();
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public CartsListPage verifyNumberOfCarts(int expected) {
		log.info("Verify the number of carts in the list is " + expected);
		GenUtils.verifyResult(cartsListTable.getListLength(), expected);
		return this;
	}
}