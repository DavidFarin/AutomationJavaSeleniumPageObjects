package pages.rialto;

import pages._pages_mngt.MainPageManager;
import pages.rialto.editProfile.EditProfileSuperPage;
import pages.rialto.editProfile.EditRecommendationsProfilePage;
import util.SelUtils.LocatorType;

public class EditSP_QueryTabPage extends EditProfileSuperPage {

    private String rankingDropdown = "//ex-dropdown[contains(@class, 'sel-sort-list-Ranking ')]";
    private String resultsList = "//span[contains(@class, 'scheduled-purchasing-profile-query-test-results')]";

    public EditSP_QueryTabPage(MainPageManager pages) {
        super(pages);
    }

    @Override
    public EditSP_QueryTabPage ensurePageLoaded() {
        su.waitElementVisible(LocatorType.Xpath, rankingDropdown);
        return this;
    }

    public void runAndVerifyBasicQuery(String firstQueryId, String groupId, String randomYear, String randomNumber) {
        this.ensurePageLoaded();
        setCategoryInRow(firstQueryId, QueryCategoryOption.PublishedXYearsAgo)
                .setOperatorInRow(firstQueryId, QueryOperatorOption.Equals)
                .setValueInRow(firstQueryId, QueryValueOption.Input, randomNumber).runQueryInGroup(groupId);
        su.waitElementVisible(LocatorType.Xpath, resultsList);
        super.verifyQueryTestResults(QueryCategoryOption.PublishedXYearsAgo, 20, randomYear);
    }

}
