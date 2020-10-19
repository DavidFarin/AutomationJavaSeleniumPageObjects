package util.components.rialto.rialtoPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages._pages_mngt.MainPageManager;
import util.SelUtils.LocatorType;
import util.interfaces.GeneralInterface.CodeNameable;
import util.GenUtils;


public class PurchaseRequestPanel extends RialtoPanelSuperPage {

	public PurchaseRequestPanel(MainPageManager pages) {
		super(pages);
	}

	@Override
	public PurchaseRequestPanel ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.presenceOfElementLocated(By.xpath(PurchaseRequestDetail.Title.getCode())));
		return this;
	}
	
	public enum PurchaseRequestDetail implements CodeNameable {
		Title("Title", "//div[contains(@class, 'purchase-request-details')]/span"),
		Author("Author", "//span[contains(@class, 'sel-author')]"),
		Isbn("ISBN", "//details-summary-line[contains(@class, 'sel-isbn')]//label[contains(@class, 'sel-value')]"),
		Publisher("Publisher", "//details-summary-line[contains(@class, 'sel-publisher')]//label[contains(@class, 'sel-value')]"),
		CreatedFrom("Created from", "//details-summary-line[contains(@class, 'sel-source-type')]//label[contains(@class, 'sel-value')]"),
		Requester("Requester", "//details-summary-line[contains(@class, 'sel-requester-name')]//label[contains(@class, 'sel-value')]");

		private String name, code;

		PurchaseRequestDetail(String name, String code) {
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

	public enum CourseDetail implements CodeNameable {
		OpenCourse("Open course session", "//span[contains(@style, 'rotate(0deg)')]//..//mat-panel-title[contains(text(), '{0}')]"),
		CloseCourse("Close course session", "//span[contains(@style, 'rotate(180deg)')]//..//mat-panel-title[contains(text(), '{0}')]"),
		Code("Code", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.course_code')]//label[contains(@class, 'sel-value')]"),
		Name("Name", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.course_name')]//label[contains(@class, 'sel-value')]"),
		Instructor("Instructor", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.instructor')]//label[contains(@class, 'sel-value')]"),
		Campus("Campus", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.campus')]//label[contains(@class, 'sel-value')]"),
		Year("Year", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.course_year')]//label[contains(@class, 'sel-value')]"),
		NumberOfParticipants("Number of participants", "//mat-panel-title[contains(text(), '{0}')]/../../..//details-summary-line[contains(@label, 'marketplace.number_of_participants')]//label[contains(@class, 'sel-value')]");

		private String name, code;

		CourseDetail(String name, String code) {
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

	public void showPurchaseRequestPanel() {
		super.showPanelIfNotVisible(RialtoPanels.PurchaseRequest);
		ensurePageLoaded();
	}
	
	public void openCourseSession(String courseName) {
		log.info("Open the details of the course: " + courseName);
		su.scrollToElement(LocatorType.Xpath, CourseDetail.OpenCourse.getCode(), courseName.toUpperCase());
		su.waitElementClickableAndClick(LocatorType.Xpath, CourseDetail.OpenCourse.getCode(), courseName.toUpperCase());
	}
	
	public void closeCourseSession(String courseName) {
		log.info("Close the details of the course: " + courseName);
		su.waitElementClickableAndClick(LocatorType.Xpath, CourseDetail.CloseCourse.getCode(), courseName.toUpperCase());
	}

	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////// VALIDATIONS //////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	public void verifyPurchaseRequestDetail(PurchaseRequestDetail detail, String expected) {
		log.info("Verify the " + detail.getName() + " is: " + expected);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, detail.getCode()), expected);
	}
	
	public void verifyCourseDetail(String courseName, CourseDetail detail, String expected) {
		log.info("Verify the " + detail.getName() + " is: '" + expected + "' in the course: " + courseName);
		GenUtils.verifyResult(su.getElementAndGetText(LocatorType.Xpath, detail.getCode(), courseName.toUpperCase()), expected);
	}
}