package applogic1;

import java.util.List;
import static util.ParamsUtils.CONNECTION_URL;
import static util.ParamsUtils.DB_PASSWORD;
import static util.ParamsUtils.DB_USER;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.DB;
import java.util.ArrayList;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.rialto.WorkPage;
import models.Role;
import models.User;
import models.Role.RoleDetails;
import models.RoleParameter.RoleParamType;
import models.RoleParameter;
import models.User.UserCommunicationType;
import models.User.UserGroup;
import models.User.UserType;
import pages.rialto.MarketPage;
import pages.rialto.MarketPage.WorkFormats;
import util.Constants;
import util.GenUtils;
import util.MailUtils;
import util.SelUtils.LocatorType;
import util.components.rialto.RialtoFacet.FacetGroup;
import util.components.rialto.rialtoList.MarketListComponent;
import util.exceptions.RialtoItemNotFoundException;

public class RialtoHelper1 extends DriverBasedHelper {

	public RialtoHelper1(ApplicationManager1 manager) {
		super(manager);
	}

	public enum RialtoRecordCondition {
		NUM_OF_ITEMS_IN_WORK,
		MINIMUM_NUM_OF_ITEMS_IN_WORK,
		MINIMUM_NUM_OF_ITEMS_IN_WORK_WITH_FORMAT,
		MINIMUM_NUM_OF_ITEMS_IN_WORK_AFTER_APPLY_FACETS,
		WORK_WITH_HOLDINGS,
		OFFERING_WITH_DDA_AVAILABLE,
		OFFERING_IN_DDA_POOL;
	};

	private final static int MAX_NUMBER_OF_PAGES = 30;
	private String wordRecordFormats = MarketListComponent.ITEM_IN_ROW + "//" + MarketListComponent.WORK_FORMAT_AMOUNT;

	public int getWorkRowByCondition(RialtoRecordCondition condition, boolean isConditionDesired, Object... placeholders)
			throws RialtoItemNotFoundException {
		log.debug("Get record row by " + (isConditionDesired ? "" : "non") + " existing condition: " + condition.name());

		MarketPage mp = app.pages().rialto.marketPage;
		int numberOfPages = mp.getNumberOfPages();
		int pageSize = mp.getListLength();

		for (int page = 1; page <= numberOfPages && page <= MAX_NUMBER_OF_PAGES; page++) {
			if (page == numberOfPages)
				pageSize = mp.getListLength();
			for (int row = 0; row < pageSize; row++) {
				if (isConditionExists(row, condition, placeholders) == isConditionDesired)
					return row;
			}
			if (page == numberOfPages)
				break;

			mp.clickNextPage();
			mp.ensurePageLoaded();

		}
		throw new RialtoItemNotFoundException("Record with condition not found!");
	}

	public int getWorkRowByMultipleConditions(RialtoRecordCondition[] conditions, Object[]... placeholders) throws RialtoItemNotFoundException {
		log.debug("Get record row by conditions...");
		MarketPage mp = app.pages().rialto.marketPage;
		int numberOfPages = mp.getNumberOfPages();
		int pageSize = mp.getListLength();

		for (int page = 1; page <= numberOfPages && page <= MAX_NUMBER_OF_PAGES; page++) {
			if (page == numberOfPages)
				pageSize = mp.getListLength();

			for (int row = 0; row < pageSize; row++) {
				if (areConditionsExist(row, conditions, placeholders)) {
					return row;
				}
			}

			if (page == numberOfPages)
				break;

			mp.clickNextPage();
			mp.ensurePageLoaded();
		}

		throw new RialtoItemNotFoundException("Record with conditions not found!");
	}

	public boolean isConditionExists(int row, RialtoRecordCondition condition, Object... placeholders) {
		log.debug("Checking row " + row + " condition: " + condition.name());
		switch (condition) {
			case NUM_OF_ITEMS_IN_WORK:
				return isWorkHasNumOfItems(row, Integer.parseInt("" + placeholders[0]));
				
			case MINIMUM_NUM_OF_ITEMS_IN_WORK:
				return isWorkHasMinNumOfItems(row, Integer.parseInt("" + placeholders[0]));

			case MINIMUM_NUM_OF_ITEMS_IN_WORK_WITH_FORMAT:
				if (placeholders.length < 1)
					Assert.fail("Please supply number of items!");
				return isWorkHasMinNumOfItemsInFormat(row, Integer.parseInt("" + placeholders[0]), (WorkFormats) placeholders[1]);
			
			case MINIMUM_NUM_OF_ITEMS_IN_WORK_AFTER_APPLY_FACETS:
				return isWorkHasMinNumOfItemsAfterApplyFacets(row, (int) placeholders[0], (FacetGroup[]) placeholders[1], (String[]) placeholders[2]);
				
			case WORK_WITH_HOLDINGS:
				return isWorkHasHoldings(row, (int) placeholders[0]);
				
			case OFFERING_WITH_DDA_AVAILABLE:
				return isOfferingHasDdaAvailable(row, (boolean) placeholders[0]);
			
			case OFFERING_IN_DDA_POOL:
				return isOfferingInDdaPool(row, (boolean) placeholders[0]);
				
			default:

				Assert.fail("Please provide some condition!");
				return false;
		}
	}

	public boolean areConditionsExist(int row, RialtoRecordCondition[] condition, Object[]... placeholders) throws RialtoItemNotFoundException{
		log.debug("Checking multiple conditions...");
		for (int i = 0; i < condition.length; i++) {
			if (!isConditionExists(row, condition[i], placeholders[i]))
				return false;
		}
		return true;
	}

	public int getOfferingRowByCondition(RialtoRecordCondition condition, boolean isConditionDesired) throws RialtoItemNotFoundException {
		log.debug("Get offering's row by " + (isConditionDesired ? "" : "non") + " existing condition: " + condition.name());
		MarketPage mp = app.pages().rialto.marketPage;
		WorkPage wp = app.pages().rialto.workPage;
		int numberOfPages = mp.getNumberOfPages();
		int marketPageSize = mp.getListLength();
		for (int page = 1; page <= numberOfPages && page <= MAX_NUMBER_OF_PAGES; page++) {
			if (page == numberOfPages)
				marketPageSize = mp.getListLength();
			for (int workRow = 0; workRow < marketPageSize; workRow++) {
				wp = mp.clickViewAllOffers(workRow).closePanelIfVisible();
				int workPageSize = wp.getListLength();
				for(int offeringRow = 1; offeringRow <= workPageSize; offeringRow++) {;
					if (isConditionExists(offeringRow, condition) == isConditionDesired)
						return offeringRow;
				}
				wp.clickBack().ensurePageLoaded();
			}
			if (page == numberOfPages)
				break;	
			mp.clickNextPage().ensurePageLoaded();
		}
		throw new RialtoItemNotFoundException("Offering with condition not found!");
	}
	
	public int getOfferingRowByMultipleConditions(RialtoRecordCondition[] conditions, Object[]... isConditionDesired) throws RialtoItemNotFoundException {
		log.debug("Get offering's row by conditions...");
		MarketPage mp = app.pages().rialto.marketPage;
		WorkPage wp = app.pages().rialto.workPage;
		int numberOfPages = mp.getNumberOfPages();
		int marketPageSize = mp.getListLength();
		for (int page = 1; page <= numberOfPages && page <= MAX_NUMBER_OF_PAGES; page++) {
			if (page == numberOfPages)
				marketPageSize = mp.getListLength();
			for (int workRow = 0; workRow < marketPageSize; workRow++) {
				wp = mp.clickViewAllOffers(workRow).closePanelIfVisible();
				int workPageSize = wp.getListLength();
				for(int offeringRow = 0; offeringRow < workPageSize; offeringRow++) {
					if (areConditionsExist(offeringRow, conditions, isConditionDesired))
						return offeringRow;
				}
				wp.clickBack().ensurePageLoaded();
			}
			if (page == numberOfPages)
				break;	
			mp.clickNextPage().ensurePageLoaded();
		}
		throw new RialtoItemNotFoundException("Offering with condition not found!");
	}
	
	//CHECKING SPECIFIC CONDITIONS METHODS

	private boolean isWorkHasMinNumOfItems(int row, int desiredNumOfItems) {
		return getWorkNumOfItems(row) >= desiredNumOfItems;
	}
	
	private boolean isWorkHasNumOfItems(int row, int desiredNumOfItems) {
		return getWorkNumOfItems(row) == desiredNumOfItems;
	}
	
	private int getWorkNumOfItems(int row) {
		List<WebElement> formatsInWork = su.getListOfElements(LocatorType.Xpath, wordRecordFormats, row, "");
		int numOfFormatsInWork = formatsInWork.size();
		int numOfItemsInWork = 0;
		for (int i = 0; i < numOfFormatsInWork; i++) {
			numOfItemsInWork += Integer.parseInt(formatsInWork.get(i).getText().trim().split(" ")[0]);
		}
		return numOfItemsInWork;
	}

	private boolean isWorkHasMinNumOfItemsInFormat(int row, int desiredNumOfItems, WorkFormats workFormat) {
		if (su.isElementExist(LocatorType.Xpath, wordRecordFormats, row, workFormat.getName())) {
			String formatAmmount = su.getElementAndGetText(LocatorType.Xpath, wordRecordFormats, row, workFormat.getName()).split(" ")[0];
			return (Integer.parseInt(formatAmmount) >= desiredNumOfItems);
		}
		return false;
	}
	
	private boolean isWorkHasMinNumOfItemsAfterApplyFacets(int row, int desiredNumOfItems, FacetGroup[] groups, String[] facets) {
		app.pages().rialto.marketPage.clickViewAllOffers(row).showFacets().clearAllFacets();		
		for(int index = 0; index < groups.length; index++) {
			app.pages().rialto.workPage.clickFacetCheckboxIfExists(groups[index], facets[index]);
		}		
		int numberOfItems = app.pages().rialto.workPage.clickApply().getNumberOfResults();
		app.pages().rialto.workPage.clickBack();
		return numberOfItems >= desiredNumOfItems;
	}
	
	private boolean isWorkHasHoldings(int row, int expected) {
		int numberOfHoldings = app.pages().rialto.marketPage.clickViewAllOffers(row).getNumberOfHoldingsInWork();
		app.pages().rialto.workPage.clickBack();
		return numberOfHoldings == expected;
	}
	
	private boolean isOfferingHasDdaAvailable(int row, boolean desired) {
		return su.isElementExist(LocatorType.Xpath, MarketListComponent.ITEM_IN_ROW + "//" + "div[@class='ex-chip-text' and text()='DDA']", row) == desired;
	}
	
	private boolean isOfferingInDdaPool(int row, boolean desired) {
		return su.isElementExist(LocatorType.Xpath, MarketListComponent.ITEM_IN_ROW + "//span[text() = 'In dda pool']", row) == desired;
	}

	//CREATE RIALTO USER
	
	public User createRialtoUser(RoleDetails... roles) {
		String instName, instCode, libraryName, libraryCode, ts, firstName, lastName, primaryIdentifier;
		ts = GenUtils.getCurrentTimestamp(GenUtils.TIME_FORMAT_ddMMyyHHmmSSS);
		libraryName = app.getParamsUtils().getPrimaryLibraryName();
		libraryCode = app.getParamsUtils().getPrimaryLibraryCode();
		instName = app.paramsUtils.getCurrentInstitutionName();
		instCode = app.paramsUtils.getCurrentInstitutionCode();
		firstName = "Rialto User" + ts;
		lastName = "AutomationTest";
		primaryIdentifier = "RialtoUser" + ts;
		ArrayList<Role> userRoles = new ArrayList<Role>();
		for(int index = 0; index < roles.length; index++) {
			switch (roles[index]) {
			case SELECTOR:
				//URM-138519
				if(pages.getSessionParams().getCurrentEnvironment().equals("QAC01"))
					userRoles.add(new Role(RoleDetails.SELECTOR, instName, instCode));
				else
					userRoles.add(new Role(RoleDetails.SELECTOR03, instName, instCode));
				break;
			case RECEIVING_OPERATOR:
				userRoles.add(new Role(roles[index], libraryName, libraryCode, new RoleParameter(RoleParamType.ServiceUnit, "", app.paramsUtils.getPrimaryAcqDeptType())));
				break;
			default:
				userRoles.add(new Role(roles[index], instName, instCode));
				break;	
			}
		}
		User user = new User(primaryIdentifier, firstName, lastName, Constants.RIALTO_MNGR_PSWD, UserCommunicationType.Work,
				MailUtils.NO_MAIL, UserType.STAFF, UserGroup.Staff.getCode(), Constants.LANGUAGE_ENGLISH_CODE,
				Constants.LANGUAGE_ENGLISH_NAME, userRoles, "");
		app.getREST_API_Admin_Helper().createNewUserWithRoles(user);
		log.info("The user " + user.getPrimaryID() + " was created.");
		return user;
	}	
	
	public void removeCartsAwaitingApprovalViaDB() {
		log.info("Remove all carts awaiting approval via DB");
		String db_url = app.getParamsUtils().getPropValue(CONNECTION_URL);
		String user = app.getParamsUtils().getPropValue(DB_USER);
		String password = app.getParamsUtils().getPropValue(DB_PASSWORD);
		DB db = new DB(db_url, user, password, log);		
		String query = "Delete from Rialto_List WHERE type='CART' and STATUS = 'WAITING_FOR_APPROVAL'";	
		try {
			db.openConnectionNoFailure();
			ResultSet rs = db.executeQueryReturnResultSetNoFailure(query);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
		db.closeConnection();
	}
}
