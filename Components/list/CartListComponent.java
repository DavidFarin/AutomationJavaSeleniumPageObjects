package util.components.rialto.rialtoList;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import com.google.common.collect.Ordering;
import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.rialto.RialtoInternalPage.RialtoBadge;
import pages.rialto.RialtoInternalPage.RialtoSort;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Codeable;

public class CartListComponent extends RialtoListComponent {

	public CartListComponent(MainPageManager pages) {
		super(pages);
	}

	public enum CartPageListItemDetail implements Codeable {
		Title("ex-component-loader[contains(@class, 'sel-title')]//a"),
		Title03("ex-label[contains(@class, 'sel-title')]//span[contains(@class, 'sel-value')]"),
		Author("ex-label[contains(@class, 'sel-author')]/div/span"),
		PublisherAndYear("ex-label[contains(@class, 'sel-pubyearAndPublisher')]/div/span"),
		Binding("ex-label[contains(@class, 'sel-binding')]/div/span"),
		Isbn("ex-label[contains(@class, 'sel-isbn')]/div/span"),
		PriceBeforeDiscount("span[contains(@class, 'rialto-price-before-discount')]"),
		Price("span[contains(@class, 'sel-rialto-price')]"),
		Platform("ex-component-loader[contains(@class, 'sel-ddaLicenseAndPlatform')]//div[contains(@class, 'dda-and-license-platform')]"),
		Quantity("ex-component-loader[contains(@class, 'sel-fundAndlocation')]//div[1]"),																					//URM-134381
		Checkbox("input[contains(@class, 'mat-checkbox-input')]"),
		Toggle("ex-component-loader[contains(@class, 'el-enabled')]//div[contains(@class, 'mat-slide-toggle-ripple')]"),
		ToggleEnabled("ex-component-loader[contains(@class, 'el-enabled')]//mat-slide-toggle[contains(@class, 'mat-checked')]"),
		WarningIcon("i[contains(@class, 'ex-list-layout-warning-icon')]"),
		RemoveItem("button[contains(@class, 'sel-record-actions-button')]//span[text() = 'Remove']"),
		RejectItem("button[contains(@class, 'sel-record-actions-button')]//span[text() = 'Reject']"),
		ArrowAction("button[contains(@class, 'sel-record-actions-arrow-button')]"),
		MoreActions("button[contains(@class, 'sel-record-actions-more-actions')]"),
		TemplateName("ex-label[contains(@class, 'sel-name')]//span[contains(@class, 'sel-value')]");

		private String code;

		CartPageListItemDetail(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}


	//////////////////////////////////////////////////////////////////////////
	///////////////////////////////// CLICKS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void clickRemoveItem(int row) {
		log.info("Click on remove button of item in row " + row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.RemoveItem.getCode(), row);
	}

	public void clickItemCheckbox(int row) {
		log.info("Select item in row " + row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.Checkbox.getCode(), row);
	}
	
	public void clickItemToggle(int row) {
		log.info("Click on item's toggle in row " + row);
		su.waitElementClickableAndClickJS(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.Toggle.getCode(), row);
	}
	
	public void clickRejectItem(int row) {
		log.info("Reject item in row - " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.RejectItem.getCode(), row);
	}
	
	public void clickArrowAction(int row) {
		log.info("Click on Arrow Action button - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.ArrowAction.getCode(), row);
	}
	
	public void clickMoreActions(int row) {
		log.info("Click on More Actions button - row " + row);
		su.waitElementClickableAndClick(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.MoreActions.getCode(), row);
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

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
			case TemplateNameAsc:
				GenUtils.verifyResult(Ordering.natural().isOrdered(getAllTemplateNames()), true);
				break;	
			case TemplateNameDesc:
				GenUtils.verifyResult(Ordering.natural().reverse().isOrdered(getAllTemplateNames()), true);
				break;
		}
	}
	
	public void verifyItemInCart(RialtoItem item, RialtoBadge... badges) {
		log.info("Verify the item in the Cart List");
		GenUtils.verifyResult(isItemInList(item, badges), true);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		log.info("Get item in row " + row);
		return new RialtoItem("", getItemTitle(row), getItemAuthor(row), getItemPublicationYear(row), getItemPublisher(row),
				getItemBinding(row), getItemIsbn(row), getItemPrice(row), getItemPlatform(row));
	}

	private String getItemTitle(int row) {
		if(pages.getSessionParams().getCurrentEnvironment().equals("QAC01"))
			return getItemDetailText(CartPageListItemDetail.Title, row);
		return getItemDetailText(CartPageListItemDetail.Title03, row);
	}

	public String getItemQuantity(int row) {
		String quantity = getItemDetailText(CartPageListItemDetail.Quantity, row);
		return (quantity.equals("")) ? "1" :  quantity.substring(10);
	}

	private String getItemAuthor(int row) {
		String author = getItemDetailText(CartPageListItemDetail.Author, row);
		if(!author.equals(""))
			author = author.substring(2);
		return author;
	}

	private String getItemPublicationYear(int row) {
		String publicationYear = getItemDetailText(CartPageListItemDetail.PublisherAndYear, row);
		if (!publicationYear.equals(""))
			publicationYear = publicationYear.split(", ")[0];
		return publicationYear;
	}

	private String getItemPublisher(int row) {
		String publisher = getItemDetailText(CartPageListItemDetail.PublisherAndYear, row);
		if (!publisher.equals(""))
			publisher = publisher.replace(publisher.split(", ")[0] + ", ", "");
		return publisher;
	}

	private String getItemBinding(int row) {
		return getItemDetailText(CartPageListItemDetail.Binding, row);
	}

	private String getItemIsbn(int row) {
		return getItemDetailText(CartPageListItemDetail.Isbn, row).split(" ")[1];
	}

	private String getItemPrice(int row) {
		if(!pages.getSessionParams().getCurrentEnvironment().equals("QAC01"))
			return getItemDetailText(CartPageListItemDetail.Price, row);
		return getItemDetailText(CartPageListItemDetail.PriceBeforeDiscount, row);
	}

	private String getItemPlatform(int row) {
		return getItemDetailText(CartPageListItemDetail.Platform, row);
	}

	public ArrayList<Double> getAllPrices() {
		ArrayList<Double> values = new ArrayList<Double>();
		List<WebElement> elements = su.getListOfElements(LocatorType.Xpath, allItems + "//" + CartPageListItemDetail.Price.getCode());
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
		List<WebElement> elements = su.getListOfElements(LocatorType.Xpath, allItems + "//" + CartPageListItemDetail.PublisherAndYear.getCode());
		for (int i = 0; i < elements.size(); i++) {
			try {
				values.add(Double.parseDouble(elements.get(i).getText().split(", ")[0]));
			} catch (java.lang.NumberFormatException e) {
				values.add(0.0);
			}
		}
		return values;
	}
	
	public String getTemplateName(int row) {
		return getItemDetailText(CartPageListItemDetail.TemplateName, row);
	}
	
	public ArrayList<String> getAllTemplateNames() {
		ArrayList<String> names = new ArrayList<String>();
		List<WebElement> elements = su.getListOfElements(LocatorType.Xpath, allItems + "//" + CartPageListItemDetail.TemplateName.getCode());
		for (int i = 0; i < elements.size(); i++) {
			names.add(elements.get(i).getText());	
		}
		return names;
	}
	
	public double getOrderTotalSum() {
		log.info("Get order amount calculating Quantity x Price in all rows");
		int length = getPageSize();
		double orderAmount = 0;
		for(int i = 0; i < length; i++) {
			orderAmount += Double.parseDouble(getItemQuantity(i)) * Double.parseDouble(getItemPrice(i));
		}
		return Math.round(orderAmount* 100.0) / 100.0;
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

	public void verifyWarningInItem(int row, boolean expected) {
		log.info("Verify if exists warning icon in row " + row);
		su.moveToElement(LocatorType.Xpath, ITEM_IN_ROW, 0);		//NGS-3508
		if(expected)
			waitSmall.until(ExpectedConditions.presenceOfElementLocated(By.xpath(su.createDynamicLocator(ITEM_IN_ROW + "//" + CartPageListItemDetail.WarningIcon.getCode(), row))));
		else
			waitSmall.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(su.createDynamicLocator(ITEM_IN_ROW + "//" + CartPageListItemDetail.WarningIcon.getCode(), row))));
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.WarningIcon.getCode(), row), expected);
	}
	
	public void verifyToggleEnabled(int row, boolean expected) {
		log.info("Verify if the toggle is enabled in row " + row);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + CartPageListItemDetail.ToggleEnabled.getCode(), row), expected);
	}
}