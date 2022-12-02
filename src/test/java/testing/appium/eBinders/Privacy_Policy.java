package testing.appium.eBinders;

import org.testng.annotations.*;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import testing.appium.runner.TestListener_Xray;

import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.Utils.scrollDown;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;


@Listeners(TestListener_Xray.class)
public class Privacy_Policy extends BaseTestSet {

    private String testCaseName;
    private String tcId;
    String username = "mobile.automation+firstlogin@florencehc.com";
    String password = "Password123*";
    String firstName = "First";


    @BeforeClass(alwaysRun=true,
                  description = "Wait for the application to boot up")
    public void precondition() {
        sip.waitForApp();
        sip.selectEnvProcedure(ENVIRONMENT, true);
        sip.logInProcedure(username, password);
    }

    @Parameters({"browserName"})
    @AfterMethod(description = "Restore the application to the minimum common state for all Test Cases")
    public void postcondition(String browserName){

        switch (testCaseName) {
            case "mobile_Settings_Page_Software_Licenses":
                LoggerInformation("Postcondition procedure for TC: " + testCaseName);
                break;
            default:
                LoggerInformation("There si no postcondition procedure for TC: " + testCaseName);
        }
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-557")
    @Test(groups= {"Smoke", "Android", "iOS"},
            testName = "Mobile - Privacy Policy Agreement EEA UK and Europe Privacy Policy Back Button",
            description = "Test case checks if Privacy Policy Agreement EEA UK and Europe Privacy Policy Back Button works",
            retryAnalyzer = TcRetry.class)
    public void mobile_Privacy_Policy_Agreement_EEA_UK_and_Europe_Privacy_Policy_Back_Button () throws InterruptedException {

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        toup.assert_pageTitle();
        scrollDown(driver, 4, 40);
        toup.clickOn_iAgreeBtn();

        Thread.sleep(5000);
        System.out.println("getPageSource" + driver.getPageSource());
    }







}
