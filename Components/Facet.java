package util.components.rialto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.components.common.Button;
import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.CodeNameable;
import util.interfaces.GeneralInterface.Nameable;

public class RialtoFacet extends AnyPage {

	public RialtoFacet(MainPageManager pages) {
		super(pages);
	}

	@Override
	public RialtoFacet ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(hideFacets)));
		return this;
	}


	public enum FacetGroup implements Nameable {
		Format("format"),
		Binding("binding"),
		ElectronicBooksTypes("rights"),
		Publisher("publisher"),
		Platform("platform"),
		PublicationYear("pubyear"),
		ReadershipLevel("readershiplevel"),
		Country("country"),
		Language("language"),
		License("license"),
		OrderHistory(""),
		;

		private String name;

		FacetGroup(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum FormatFacets implements Nameable {
		ElectronicBook("EBook"),
		PrintBook("Print Book"),
		Other("Other"),;

		private String name;

		FormatFacets(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum ElectronicBooksTypesFacets implements Nameable {
		Downloadable("Downloadable"),
		DRMFree("DRM Free"),;

		private String name;

		ElectronicBooksTypesFacets(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	public enum PlatformFacets implements Nameable {
		EBC("EBC"),
		EBSCO("EBSCO"),
		AlexanderStreet("Alexander Street"),
		Cairn("Cairn"),
		CambridgeCore("Cambridge Core"),
		DeGruyterOnline("De Gruyter Online"),
		EbookCentral("Ebook Central"),
		EBSCOeBooks("EBSCO eBooks"),
		ElsevierScienceDirect("Elsevier: Science Direct"),
		IGIGlobalInfoSciOnDemand("IGI Global: InfoSci OnDemand"),
		Karger("Karger"),
		OxfordUniversityPressUPSO("Oxford University Press: UPSO"),
		ProjectMUSE("Project MUSE"),
		RittenhouseR2Digital("Rittenhouse: R2 Digital"),
		SageKnowledge("Sage Knowledge"),
		TaylorandFranciseBooks("Taylor and Francis eBooks"),
		WileyOnlineLibrary("Wiley Online Library"),
		WorldScientificEbooks("World Scientific E-books"),;

		private String name;

		PlatformFacets(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};
	
	public enum LicenseFacets implements CodeNameable {
		SingleUser("Single User", "Single"),
		ThreeUser("Three User", "Three"),
		NonLinearLending("Non Linear Lending", "Non"),
		ConcurrentAccess("Concurrent Access", "Concurrent"),
		UnlimitedAccess("Unlimited Access", "Unlimited");

		private String name;
		private String code;

		LicenseFacets(String name, String code) {
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

	public enum StatusFacets implements Nameable {
		OrderPlaced("Order Placed"),
		Cancelled("Cancelled");

		private String name;

		StatusFacets(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	};

	private Button applyFacets = new Button(pages, "//button[contains(@class, 'sel-apply-facets')]", LocatorType.Xpath);
	private Button clearCheckbox = new Button(pages, "//button[contains(@class, 'sel-clear-facets')]", LocatorType.Xpath);
	
	private String facetsBarHidden = "//mat-sidenav[contains(@class, 'ex-sidenav-page-left-sidenav') and contains(@style, 'visibility: hidden')]";	//NGS-3453
	private String hideFacets = "//div[contains(@class, 'ex-sidenav-page-sidebar-close-bar')]//button";
	private String showFacets = "//div[contains(@class, 'ex-sidenav-page-open-btn-wrapper-div')]//button";
	private String moreBtnInGroup = "//button[contains(@class, 'sel-more-{0}')]";
	private String facet = "//span[contains(@class, 'sel-value-{0}') and contains(text(), \"{1}\")]";
	private String facetCheckbox = "//mat-checkbox[contains(@class, 'sel-checkbox-{0}-{1}')]//input";
	private String facetCheckboxSelected = "//mat-checkbox[contains(@class, 'sel-checkbox-{0}-{1} mat-checkbox-checked')]";
	private String facetAmount = facet + "/../..//span[contains(@class,'side-margin')]";
	private String facetsInGroup = "//span[contains(@class, 'sel-value-{0}')]";
	private String selectedFacet = "//span[contains(@class, 'ex-selected-facet-value')]/span[text()=\"{0}\"]";
	private String removeFacet = selectedFacet + "/following-sibling::button[1]";
	private String clearFacets = "//button[contains(@class, 'ex-selected-facets-wrapper-clear-button')]";
	private String facetSideNavBar = "//mat-sidenav[contains(@class, 'ex-sidenav-page-full-height')]";


	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void clickHideFacetsIfVisible() {
		log.info("Hide facets bar if visible");
		if(!su.isElementExist(LocatorType.Xpath, facetsBarHidden))
			su.waitElementClickableAndClick(LocatorType.Xpath, hideFacets);
		waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(facetsBarHidden)));
	}

	public void clickShowFacetsIfNotVisible() {
		log.info("Show facets bar if not visible");
		if(su.isElementExist(LocatorType.Xpath, facetsBarHidden))
			su.waitElementClickableAndClick(LocatorType.Xpath, showFacets);
		su.waitElementInvisible(LocatorType.Xpath, facetsBarHidden);
	}

	public void clickMore(FacetGroup group) {
		log.info("Click on 'More' button in the facet group: " + group.getName());
		su.getElementAndClickJSIfExists(LocatorType.Xpath, moreBtnInGroup, group.getName());
	}

	public void clickClearAll() {
		log.info("Clear all active facets");
		su.getElementAndClickIfExists(LocatorType.Xpath, clearFacets);
	}
	
	public void clickClear() {
		log.info("Click on 'Clear' button");
		clearCheckbox.waitElementClickableAndClick();
	}
	
	public void clickApply() {
		log.info("Click on 'Apply' button");
		applyFacets.waitElementClickableAndClick();
	}

	public void applyFacetIfExists(FacetGroup group, String facetName) {
		log.info("Click on Facet '" + facetName + "' if exist");
		if (su.isElementExist(LocatorType.Xpath, facet, group.getName(), facetName) && !facetName.equals("") && !facetName.equals("0"))	// NGS-1425
			su.waitElementClickableAndClickJS(LocatorType.Xpath, facet, group.getName(), facetName);
	}

	public void clickFacetCheckboxIfExists(FacetGroup group, String facetName) {
		log.info("Click on checkbox '" + facetName + "' if exists");
		if (su.isElementExist(LocatorType.Xpath, facetCheckbox, group.getName(), facetName) && !facetName.equals("0")) 	// NGS-1425
			su.waitElementClickableAndClickJS(LocatorType.Xpath, facetCheckbox, group.getName(), facetName);
	}
	
	public void removeSelectedFacet(String facetName) {
		log.info("Remove selected facet " + facetName);
		su.getElementAndClick(LocatorType.Xpath, removeFacet, facetName);
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public int getFacetAmount(FacetGroup group, String facetName) {
		log.info("Get the amount in facet " + facetName);
		int amount = 0;
		if(su.isElementExist(LocatorType.Xpath, facet, group.getName(), facetName)) {
			String value = su.getElementAndGetText(LocatorType.Xpath, facetAmount, group.getName(), facetName);
			amount = Integer.parseInt(value.substring(1, value.length()-1));
		}
		return amount;
	}
	
	public ArrayList<String> getVisibleFacetsInGroup(FacetGroup group) {
		log.info("Get the names of visible facets in group " + group.getName());
		ArrayList<String> facetsNames = new ArrayList<String>();
		List<WebElement> facets = su.getListOfElements(LocatorType.Xpath, facetsInGroup, group.getName());
		for (int i = 0; i < facets.size(); i++) {
			facetsNames.add(facets.get(i).getText());
		}
		return facetsNames;
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void verifyFacetApplied(String facetName, boolean expected) {
		if (facetName != "") {
			log.info("Verify if the facet " + facetName + " is applied");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, selectedFacet, facetName), expected);
		}
	}

	public void verifyFacetCheckboxSelected(FacetGroup group, String facetName, boolean expected) {
		if (facetName != "") {
			log.info("Verify if the checkbox " + facetName + " is selected");
			GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, facetCheckboxSelected, group.getName(), facetName), expected);
		}
	}
	
	public void verifyFacetPanelVisible(boolean expected) {
		log.info("Verify if the Facet panel is visible");
		String actual = su.getElement(LocatorType.Xpath, facetSideNavBar).getCssValue("visibility");
		GenUtils.verifyResult(actual.equals("visible") ? true : false, expected);
	}
}