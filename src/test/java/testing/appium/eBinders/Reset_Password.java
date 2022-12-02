package testing.appium.eBinders;

import org.testng.ITestContext;
import org.testng.annotations.*;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import testing.appium.runner.TestListener_Xray;

import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.Utils.resetApp;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.iOS_BUNDLE_ID;
import static testing.appium.runner.propertyFile.DataProvider.randomString;
import static testing.appium.runner.propertyFile.DataProvider.tcData.*;


@Listeners(TestListener_Xray.class)
public class Reset_Password extends BaseTestSet {

    private String testCaseName;
    private String tcId;
    String username = "mobile.automation@florencehc.com";
    String password = "Password123*";
    String firstName = "Mobile";
    String inactiveUsername = "inactive." + randomString + "@florencehc.com";


    @BeforeClass(alwaysRun=true,
                  description = "Wait for the application to boot up")
    public void precondition() {
        sip.assert_WelcomeMsg(welcomeMsg);
        sip.selectEnvProcedure(ENVIRONMENT, true);
    }

    @Parameters({"browserName"})
    @AfterMethod(description = "Restore the application to the minimum common state for all Test Cases")
    public void postcondition(String browserName){

        LoggerInformation("Postcondition procedure for TC: " + testCaseName);
        switch (testCaseName) {
            case "mobile_Log_In_Valid_Account_Different_Env":
                resetApp(driver, iOS_BUNDLE_ID);
                sip.selectEnvProcedure(ENVIRONMENT, false);
                break;
        }
        resetApp(driver, iOS_BUNDLE_ID);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-463")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Reset password inactive account",
            description = "Test case checks if it is possible to reset the password if the account is inactive",
            retryAnalyzer = TcRetry.class)
    public void mobile_Reset_password_inactive_account(String platformParameter, ITestContext context) {
        context.setAttribute("platformParameter", platformParameter);

       testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
       tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.insert_emailField(inactiveUsername);
        sip.clickOn_nextBtn();
        sip.clickOn_forgotPasswordBtn();
        rpp.assert_WelcomeMsg();
        rpp.clickOn_requestPasswordResetBtn();
        rpp.assert_toastMessage();
//    TODO Email Verification
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-464")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Reset password active account",
            description = "Test case checks if it is possible to reset the password if the account is active",
            retryAnalyzer = TcRetry.class)
    public void mobile_Reset_password_active_account(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

       testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
       tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.insert_emailField(username);
        sip.clickOn_nextBtn();
        sip.clickOn_forgotPasswordBtn();
        rpp.assert_WelcomeMsg();
        rpp.clickOn_requestPasswordResetBtn();
        rpp.assert_toastMessage();
//    TODO Email Verification

    }





}
