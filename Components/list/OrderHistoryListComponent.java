package util.components.rialto.rialtoList;

import models.rialto.RialtoItem;
import pages._pages_mngt.MainPageManager;
import pages.rialto.OrderHistoryPage.OrderHistoryStatus;
import util.GenUtils;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.Codeable;

public class OrderHistoryListComponent extends RialtoListComponent {

	public OrderHistoryListComponent(MainPageManager pages) {
		super(pages);
	}

	public enum OrderHistoryListItemDetails implements Codeable {
		Title("ex-label[contains(@class, 'sel-title')]/div/span"),
		Author("ex-label[contains(@class, 'sel-author')]/div/span"),
		Price("span[contains(@class, 'sel-rialto-price')]"),
		Status("span[contains(@class, 'sel-status') and text() = '{1} ']"),
		AdditionalStatus("/span[text()='{2}']"),
		Selector("span[contains(@class, 'sel-record-selected-by') and contains(text(), '{1}')]"),
		Approver("span[contains(@class, 'sel-record-approved-by') and contains(text(), '{1}')]"),
		ApprovedBy("span[text() = ' Approved by']/.."),
		RejectedBy("span[text() = ' Rejected by']/..");

		private String code;

		OrderHistoryListItemDetails(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}


	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public RialtoItem getItem(int row) {
		log.info("Get details of item row " + row);
		return new RialtoItem(getItemTitle(row), getItemAuthor(row), getItemPrice(row));
	}

	private String getItemTitle(int row) {
		return getItemDetailText(OrderHistoryListItemDetails.Title, row);
	}

	private String getItemAuthor(int row) {
		String author = getItemDetailText(OrderHistoryListItemDetails.Author, row);
		if(author.length()!=0)
			author = author.substring(2);
		return author;
	}

	private String getItemPrice(int row) {
		return getItemDetailText(OrderHistoryListItemDetails.Price, row);
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public void verifyItemInList(RialtoItem item, OrderHistoryStatus... status) {
		log.info("Verify the item is in the list");
		GenUtils.verifyResult(isItemInList(item, status), true);
	}
	
	public void verifySelectedByInRow(String selector, int row) {
		log.info("Verify the item in row " + row + " was selected by: " + selector);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + 
												OrderHistoryListItemDetails.Selector.getCode(), row, selector), true);
	}
	
	public void verifyApprovedByInRow(String approver, int row) {
		log.info("Verify the item in row " + row + " was approved by: " + approver);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" +
												OrderHistoryListItemDetails.ApprovedBy.getCode() + "//" +
												OrderHistoryListItemDetails.Approver.getCode(), row, approver), true);
	}
	
	public void verifyRejectedByInRow(String approver, int row) {
		log.info("Verify the item in row " + row + " was rejected by: " + approver);
		GenUtils.verifyResult(su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" +
												OrderHistoryListItemDetails.RejectedBy.getCode() + "//" +
												OrderHistoryListItemDetails.Approver.getCode(), row, approver), true);
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	private boolean isItemInList(RialtoItem item, OrderHistoryStatus... status) {
		int length = getPageSize();
		for (int row = 0; row < length; row++) {
			if(getItem(row).basicEquals(item)){
				switch(status.length) {
					case 0:
						return true;
					case 1:
						return su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + OrderHistoryListItemDetails.Status.getCode(), 
													row, status[0].getName());
					default:
						return su.isElementExist(LocatorType.Xpath, ITEM_IN_ROW + "//" + OrderHistoryListItemDetails.Status.getCode() + 
													OrderHistoryListItemDetails.AdditionalStatus.getCode(), 
													row, status[0].getName(), status[1].getName());
				}
			} 
		}
		return false;
	}
}
