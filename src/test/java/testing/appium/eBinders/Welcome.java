package testing.appium.eBinders;

import org.testng.ITestContext;
import org.testng.annotations.*;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import testing.appium.runner.TestListener_Xray;

import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.Utils.*;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.iOS_BUNDLE_ID;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.entitiesVersion;


@Listeners(TestListener_Xray.class)
public class Welcome extends BaseTestSet {

    private String testCaseName;
    private String tcId;


    @BeforeClass(alwaysRun=true,
                  description = "Wait for the application to boot up")
    public void precondition() {
    }

    @Parameters({"browserName"})
    @AfterMethod(description = "Restore the application to the minimum common state for all Test Cases")
    public void postcondition(String browserName){

        LoggerInformation("Postcondition procedure for TC: " + testCaseName);
        resetApp(driver, iOS_BUNDLE_ID);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-453")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Welcome Sign In",
            description = "Test case checks if 'Sign In' link works",
            retryAnalyzer = TcRetry.class)
    public void mobile_welcome_Sign_In(String platformParameter, ITestContext context) {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.clickOn_signInBtn();
        sip.assert_nextBtn();
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-453")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Welcome Need Help",
            description = "Test case checks if 'Need Help?' link works",
            retryAnalyzer = TcRetry.class)
    public void mobile_welcome_Need_Help(String platformParameter, ITestContext context) {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.clickOn_needHelpBtn();
        nhp.assert_needHelpPageTitle();

    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-454")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Welcome Privacy Policy",
            description = "Test case checks if 'Privacy Policy' link works",
            retryAnalyzer = TcRetry.class)
    public void mobile_welcome_Privacy_Policy(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.clickOn_privacyPolicyBtn();
        ppp.clickOn_pageLink();
        ppp.assert_pageLink("https://florencehc.com/privacy/");

    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-455")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Welcome Terms and Conditions",
            description = "Test case checks if 'Terms and Conditions' link works",
            retryAnalyzer = TcRetry.class)
    public void mobile_welcome_Terms_And_Conditions(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.clickOn_termsAndConditionsBtn();
        taup.assert_termsOfUsePageTitle();
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-456")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Welcome App Version",
            description = "Test case checks for App Version",
            retryAnalyzer = TcRetry.class)
    public void mobile_welcome_App_Version(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.assert_AppVersion(entitiesVersion);
    }


}
