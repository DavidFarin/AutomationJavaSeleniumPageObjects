package util.components.rialto.rialtoList;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Ordering;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage.RialtoBadge;
import pages.rialto.RialtoInternalPage.RialtoSort;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.CodeNameable;
import util.interfaces.GeneralInterface.Codeable;

public class OfferingListComponent extends RialtoListComponent {

	public OfferingListComponent(MainPageManager pages) {
		super(pages);
	}
	
	public OfferingListComponent ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(ITEM_IN_ROW + "//" + OfferingListItemDetails.PriceNotEmptyValue.getCode(), 0))));
		return this;
	}

	public enum OfferingListItemDetails implements Codeable {
		Title("ex-label[contains(@class, 'sel-title')]//span[contains(@class,'sel-value')]"),
		Author("ex-label[contains(@class, 'sel-author')]//span[contains(@class,'sel-value')]"),
		PublisherAndYear("ex-label[contains(@class, 'sel-pubyearAndPublisher')]//span[contains(@class,'sel-value')]"),
		PublisherAndYearValue("ex-label[contains(@class, 'sel-pubyearAndPublisher')]/div/span/span[contains(text(), '{0}')]"),
		Binding("ex-label[contains(@class, 'sel-binding')]//span[contains(@class,'sel-value')]"),
		Isbn("ex-label[contains(@class, 'sel-isbn')]//span[contains(@class,'sel-value')]"),
		Price("span[contains(@class, 'sel-rialto-price')]"),
		PriceValue("ex-component-loader[contains(@class, 'sel-price')]//span[contains(@class, 'sel-rialto-price') and text()='{0}']"),
		PriceNotEmptyValue("span[contains(@class, 'sel-rialto-price') and not(text()=\"\")]"),
		Platform("ex-component-loader[contains(@class, 'sel-ddaLicenseAndPlatform')]//div[contains(@class, 'dda-and-license-platform')]"),
		PlatformValue("ex-component-loader[contains(@class, 'sel-ddaLicenseAndPlatform')]//div[contains(text(),'{0}')]"),
		LicenseValue("ex-component-loader[contains(@class, 'sel-ddaLicenseAndPlatform')]//i[contains(@class, '{0}')]"),
		AddToCart("span[text()='Add to Cart']/../.."),
		LinkAndAddToCart("span[text()='Link and Add to Cart']/../.."),
		RelinkAndAddToCart("span[text()='Relink and Add to Cart']/../.."),
		ReplaceTitle("span[text()='Replace Title']/../.."),
		AddToDDA("//button[contains(@class, 'sel-record-actions-button')]//span[text()='Add to DDA']"),
		AddToCartDisabled("button[contains(@class, 'ex-record-actions-disabled')]//span[text()='Add to Cart']"),
		ReplaceTitleDisabled("button[contains(@class, 'ex-record-actions-disabled')]//span[text()='Replace Title']"),
		MoreActions("button[contains(@class, 'sel-record-actions-more-actions')]"),
		WorkTitle("//span[@class='work-details-title']");

		private String code;

		OfferingListItemDetails(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}

	}
	
	public enum ItemLicense implements CodeNameable {
		SingleUser("Single User", "icon-alma-1-user-license"),;

		private String name, code;

		ItemLicense(String name, String code) {
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

	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void clickAddToCart(int row) {
		log.info("Click on Add to Cart - row " + row);
		su.scrollToElement(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.AddToCart.getCode(), row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.AddToCart.getCode(), row);
	}
	
	public void clickLinkAndAddToCart(int row) {
		log.info("Click on Link and Add to Cart - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.LinkAndAddToCart.getCode(), row);
	}
	
	public void clickRelinkAndAddToCart(int row) {
		log.info("Click on Relink and Add to Cart - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.RelinkAndAddToCart.getCode(), row);
	}
	
	public void clickReplaceTitle(int row) {
		log.info("Click on Replace Title - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.ReplaceTitle.getCode(), row);
	}
	
	public void clickAddToDDA(int row) {
		log.info("Click on Add to DDA - row " + row);
		clickMoreActions(row);
		su.waitElementClickableAndClick(LocatorType.Xpath, OfferingListItemDetails.AddToDDA.getCode());
	}
	
	public void clickPurchaseRequestBadge() {
		log.info("Click on the 'Purchase Request' badge of the linked item in the list");
		su.waitElementClickableAndClick(LocatorType.Xpath, allItems + badgeInItem, "", RialtoBadge.PurchaseRequest.getName());
	}

	public void clickMoreActions(int row) {
		log.info("Click on More Actions button - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.MoreActions.getCode(), row);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		log.info("Get details of item row " + row);
		return new RialtoItem(getWorkTitle(), getItemTitle(row), getItemAuthor(row), getItemPublicationYear(row), getItemPublisher(row),
				getItemBinding(row), getItemIsbn(row), getItemPrice(row), getItemPlatform(row));
	}

	private String getWorkTitle() {
		if(su.isElementExist(LocatorType.Xpath, OfferingListItemDetails.WorkTitle.getCode()))
			return su.getElementAndGetText(LocatorType.Xpath, OfferingListItemDetails.WorkTitle.getCode()).trim();
		return "";
	}

	private String getItemTitle(int row) {
		return getItemDetailText(OfferingListItemDetails.Title, row);
	}

	private String getItemAuthor(int row) {
		return getItemDetailText(OfferingListItemDetails.Author, row);
	}

	private String getItemPublicationYear(int row) {
		String publicationYear = getItemDetailText(OfferingListItemDetails.PublisherAndYear, row);
		if (!publicationYear.equals(""))
			publicationYear = publicationYear.split(", ")[0];
		return publicationYear;
	}

	private String getItemPublisher(int row) {
		String publisher = getItemDetailText(OfferingListItemDetails.PublisherAndYear, row);
		if (!publisher.equals(""))
			publisher = publisher.replace(publisher.split(", ")[0] + ", ", "");
		return publisher;
	}

	private String getItemBinding(int row) {
		return getItemDetailText(OfferingListItemDetails.Binding, row);
	}

	private String getItemIsbn(int row) {
		String isbn = getItemDetailText(OfferingListItemDetails.Isbn, row);
		if (!isbn.equals(""))
			isbn = isbn.split(" ")[1];
		return isbn;
	}

	private String getItemPrice(int row) {
		return getItemDetailText(OfferingListItemDetails.Price, row);
	}

	private String getItemPlatform(int row) {
		return getItemDetailText(OfferingListItemDetails.Platform, row);
	}

	public ArrayList<Double> getAllPrices() {
		ArrayList<Double> values = new ArrayList<Double>();
		List<WebElement> elements = su.getListOfElements(LocatorType.Xpath, allItems + "//" + OfferingListItemDetails.Price.getCode());
		for (int i = 0; i < elements.size(); i++) {
			try {
				values.add(Double.parseDouble(elements.get(i).getText()));
			} catch (java.lang.NumberFormatException e) {
				values.add(0.0);
			}
		}
		return values;
	}

	public ArrayList<Double> getAllPublicationYear() {
		ArrayList<Double> values = new ArrayList<Double>();
		List<WebElement> elements;
		elements = su.getListOfElements(LocatorType.Xpath, allItems + "//" + OfferingListItemDetails.PublisherAndYear.getCode());
		
		for (int i = 0; i < elements.size(); i++) {
			try {
				values.add(Double.parseDouble(elements.get(i).getText().split(", ")[0]));
			} catch (java.lang.NumberFormatException e) {
				values.add(0.0);
			}
		}
		return values;
	}

	public int getRowOfItem(RialtoItem item, RialtoBadge... badges) {
		log.info("Get item's row");
		int length = getPageSize();
		for (int row = 0; row < length; row++) {
			if (getItem(row).deepEquals(item) && isBadgesInItemExist(row, badges))
				return row;
		}
		Assert.fail("Didn't find item!!!");
		return -1;
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyItemInList(RialtoItem item, boolean expected, RialtoBadge... badges) {
		log.info("Verify the item is in list");
		GenUtils.verifyResult(isItemInList(item, badges), expected);
	}
	
	public void verifyAddToCartButtonIsDisabled(int row, boolean expected) {
		log.info("Verify if the 'Add to Cart' button is disabled in row " + row);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.AddToCartDisabled.getCode(), row), expected);
	}

	public void verifyAddToCartButtonDisabledInMoreActions(boolean expected) {
		log.info("Verify if the 'Add to Cart' button is disabled in More Actions");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, "//" + OfferingListItemDetails.AddToCartDisabled.getCode()), expected);
	}
	
	public void verifyAddToDdaInMoreActionsExists(boolean expected) {
		log.info("Verify if the 'Add to DDA' button in More Actions exists");
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, OfferingListItemDetails.AddToDDA.getCode()), expected);
	}
	
	public void verifyReplaceTitleButtonDisabled(int row, boolean expected) {
		log.info("Verify if the 'Replace Title' button is disabled in row " + row);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + OfferingListItemDetails.ReplaceTitleDisabled.getCode(), row), expected);
	}

	public void verifyListSorted(RialtoSort option) {
		switch (option) {
			case PriceAsc:
				GenUtils.verifyResult(Ordering.natural().isOrdered(getAllPrices()), true);
				break;
			case PriceDesc:
				GenUtils.verifyResult(Ordering.natural().reverse().isOrdered(getAllPrices()), true);
				break;
			case PublicationYearAsc:
				GenUtils.verifyResult(Ordering.natural().isOrdered(getAllPublicationYear()), true);
				break;
			case PublicationYearDesc:
				GenUtils.verifyResult(Ordering.natural().reverse().isOrdered(getAllPublicationYear()), true);
				break;
		}
	}
	
	public void verifyResults(OfferingListItemDetails category, int numberOfItems, String expectedValue) {
		log.info("Verify Query test results for category: " + category);
		int displayedItems = su.getListOfElements(LocatorType.Xpath, allItems + "//" + category.getCode(), expectedValue).size();
		GenUtils.verifyResult(displayedItems, numberOfItems);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	private boolean isItemInList(RialtoItem item, RialtoBadge... badges) {
		int length = getPageSize();
		for (int row = 0; row < length; row++) {
			if (getItem(row).deepEquals(item) && isBadgesInItemExist(row, badges))
				return true;
		}
		return false;
	}
}