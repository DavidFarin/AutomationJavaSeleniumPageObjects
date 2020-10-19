package util.components.rialto;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.SelUtils.LocatorType;

public class RialtoCheckbox extends AnyPage{

	public RialtoCheckbox(MainPageManager pages, String locator) {
		super(pages);
		this.locator = locator;
	}
	
	private String locator = "";
	private String checkbox = "//mat-checkbox[contains(@class, '{0}') and contains(@class, '{1}')]//div[contains(@class, 'mat-checkbox-inner-container')]";

	
	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void set(boolean check) {
		log.info("Set the checkbox to " + (check ? "selected" : "unselected"));
		if(check != isChecked()) {
			su.waitElementClickable(LocatorType.Xpath, checkbox, locator, "");
			su.moveToElement(LocatorType.Xpath, checkbox, locator, "");
			su.waitElementClickableAndClickJS(LocatorType.Xpath, checkbox, locator, "");
		}
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public boolean isChecked() {
		return su.isElementExist(LocatorType.Xpath, checkbox, locator, "checkbox-checked");
	}
}
