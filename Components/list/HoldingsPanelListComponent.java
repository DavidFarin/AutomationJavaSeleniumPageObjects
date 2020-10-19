package util.components.rialto.rialtoList;

import models.rialto.RialtoBib;
import pages._pages_mngt.MainPageManager;
import pages.super_pages.AnyPage;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Codeable;

public class HoldingsPanelListComponent extends AnyPage {

	public HoldingsPanelListComponent(MainPageManager pages) {
		super(pages);
	}

	public enum BibDetails implements Codeable {
		Title("//ex-label[contains(@class, 'sel-title')]/div/span"),
		Author("//ex-label[contains(@class, 'sel-author')]/div/span"),
		Isbn("//ex-label[contains(@class, 'sel-isbn')]/div/span"),
		AllLocations("//li//label[contains(@class, 'sel-holding') and contains(@class, 'location')]"),
		Location("//li//label[contains(@class, 'sel-holding-{1}-location')]"),
		AllAvailability("//li//span[contains(@class, 'sel-holdings-availabillity-text')]"),
		Availability("//li[{1}]//span[contains(@class, 'sel-holdings-availabillity-text')]"),;

		private String code;

		BibDetails(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
	
	private String bibInRow = "//holdings-list//li[contains(@class, 'sel-row-{0}-item')]";
	private String allBibs = "//holdings-list//li[contains(@class, 'sel-row')]";

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////


	public int getPageSize() {
		if(su.isElementExist(LocatorType.Xpath, allBibs))
			return su.getListOfElements(LocatorType.Xpath, allBibs).size();
		return 0;
	}
	
	public RialtoBib getBib(int row) {
		log.info("Get Bib's details in row " + row);
		return new RialtoBib(getTitle(row), getAuthor(row), getIsbn(row), getLocations(row), getAvailabilities(row));
	}

	private String getTitle(int row) {
		if(su.isElementExist(LocatorType.Xpath, bibInRow + BibDetails.Title.getCode(), row))
			return su.getElementAndGetText(LocatorType.Xpath, bibInRow + BibDetails.Title.getCode(), row);
		return "";
	}

	private String getAuthor(int row) {
		if(su.isElementExist(LocatorType.Xpath, bibInRow + BibDetails.Author.getCode(), row))
			return su.getElementAndGetText(LocatorType.Xpath, bibInRow + BibDetails.Author.getCode(), row).replace("by", "");
		return "";
	}

	private String getIsbn(int row) {
		if(su.isElementExist(LocatorType.Xpath, bibInRow + BibDetails.Isbn.getCode(), row))
			return su.getElementAndGetText(LocatorType.Xpath, bibInRow + BibDetails.Isbn.getCode(), row).split(" ")[1];
		return "";
	}
	
	private String[] getLocations(int row) {
		int numberOfLocations = su.getListOfElements(LocatorType.Xpath, bibInRow + BibDetails.AllLocations.getCode(), row).size();
		String[] locations = new String[numberOfLocations];
		for(int index = 0; index < numberOfLocations; index++) {
			locations[index] = su.getElementAndGetText(LocatorType.Xpath, bibInRow + BibDetails.Location.getCode(), row, index);
		}
		return locations;
	}
	
	private String[] getAvailabilities(int row) {
		int numberOfLocations = su.getListOfElements(LocatorType.Xpath, bibInRow + BibDetails.AllLocations.getCode(), row).size();
		String[] availabilities = new String[numberOfLocations];
		for(int index = 0; index < numberOfLocations; index++) {
			if(su.isElementExist(LocatorType.Xpath, bibInRow + BibDetails.Availability.getCode(), row, index + 1))
				availabilities[index] = su.getElementAndGetText(LocatorType.Xpath, bibInRow + BibDetails.Availability.getCode(), row, index + 1);
			else 
				availabilities[index] = "";
		}
		return availabilities;
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyBibInList(RialtoBib bib, boolean expected) {
		log.info("Verify the bib " + bib.getIsbn() + " is in list");
		GenUtils.verifyResult(isBibInList(bib), expected);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	private boolean isBibInList(RialtoBib bib) {
		int length = getPageSize();
		for (int row = 0; row < length; row++) {
			if (getBib(row).equals(bib))
				return true;
		}
		return false;
	}
}