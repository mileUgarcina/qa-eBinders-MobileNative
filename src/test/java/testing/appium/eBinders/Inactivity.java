package testing.appium.eBinders;

import org.testng.ITestContext;
import org.testng.annotations.*;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import testing.appium.runner.TestListener_Xray;

import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.Utils.printingTheRemainingMinutes;
import static testing.appium.helpers.Utils.resetApp;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.iOS_BUNDLE_ID;
import static testing.appium.runner.propertyFile.DataProvider.tcData.*;


@Listeners(TestListener_Xray.class)
public class Inactivity extends BaseTestSet {

    private String testCaseName;
    private String tcId;
    String username = "mobile.automation@florencehc.com";
    String password = "Password123*";
    String firstName = "Mobile";


    @BeforeClass(alwaysRun=true,
                  description = "Wait for the application to boot up")
    public void precondition() {
        sip.waitForApp();
        sip.selectEnvProcedure(ENVIRONMENT, true);
        sip.logInProcedure(username, password);
        hp.assert_WelcomeMsg(firstName);
    }

    @Parameters({"browserName"})
    @AfterMethod(description = "Restore the application to the minimum common state for all Test Cases")
    public void postcondition(String browserName){

        LoggerInformation("Postcondition procedure for TC: " + testCaseName);
        resetApp(driver, iOS_BUNDLE_ID);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-455")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Inactivity Sign Out",
            description = "Test case checks if the session closes after 5 minutes of inactivity",
            retryAnalyzer = TcRetry.class)
    public void mobile_Inactivity_Sign_Out(String platformParameter, ITestContext context) throws InterruptedException {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

//        TODO shorten the session timeout for testing purposes
        printingTheRemainingMinutes(60000, 5);
        System.out.println("getPageSource" + driver.getPageSource());
        Thread.sleep(50000);
        System.out.println("getPageSource" + driver.getPageSource());
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-456")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Valid Face Id Log In after Inactivity ",
            description = "Test case checks if the login with biometrics is possible after the session closes after 5 minutes of inactivity - Valid Biometrics",
            retryAnalyzer = TcRetry.class)
    public void mobile_Valid_Face_Id_Log_In_after_Inactivity(String platformParameter, ITestContext context) throws InterruptedException {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

//        TODO shorten the session timeout for testing purposes
        printingTheRemainingMinutes(60000, 5);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-457")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Invalid Face Id Log In after Inactivity",
            description = "Test case checks if the login with biometrics is possible after the session closes after 5 minutes of inactivity - Invalid Biometrics",
            retryAnalyzer = TcRetry.class)
    public void mobile_Invalid_Face_Id_Log_In_after_Inactivity(String platformParameter, ITestContext context) throws InterruptedException {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

//        TODO shorten the session timeout for testing purposes
        printingTheRemainingMinutes(60000, 5);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-458")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Email Log In after Inactivity ",
            description = "Test case checks if the login with email is possible after the session closes after 5 minutes of inactivity",
            retryAnalyzer = TcRetry.class)
    public void mobile_Email_Log_In_after_Inactivity(String platformParameter, ITestContext context) throws InterruptedException {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

//        TODO shorten the session timeout for testing purposes
        printingTheRemainingMinutes(60000, 5);
    }





}
