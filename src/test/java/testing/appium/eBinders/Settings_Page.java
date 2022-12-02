package testing.appium.eBinders;

import org.testng.Assert;
import org.testng.annotations.*;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import testing.appium.runner.TestListener_Xray;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.resetApp;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.iOS_BUNDLE_ID;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.entitiesVersion;


@Listeners(TestListener_Xray.class)
public class Settings_Page extends BaseTestSet {

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
        hp.clickOn_bindersBtn();
        bp.clickOn_personCircleBtn();
        pcp.clickOn_settingsBtn();
        sp.assert_pageTitle();
    }

    @Parameters({"browserName"})
    @AfterMethod(description = "Restore the application to the minimum common state for all Test Cases")
    public void postcondition(String browserName){

        switch (testCaseName) {
            case "mobile_Settings_Page_Software_Licenses":
                LoggerInformation("Postcondition procedure for TC: " + testCaseName);
                slp.clickOn_closeBnt();
                break;
            case "mobile_Settings_Page_Privacy_Policy":
                LoggerInformation("Postcondition procedure for TC: " + testCaseName);
                resetApp(driver, iOS_BUNDLE_ID);
                hp.clickOn_bindersBtn();
                bp.clickOn_personCircleBtn();
                pcp.clickOn_settingsBtn();
                sp.assert_pageTitle();
                break;
            default:
                LoggerInformation("There si no postcondition procedure for TC: " + testCaseName);
        }
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-461")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - UAT Symbol ",
            description = "Test case checks if The Environment symbol is not clickable and the user can't change the environment in Settings Page",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_UAT_Symbol (){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.assert_envNameLabel(ENVIRONMENT);
        sp.clickOn_envBtn(ENVIRONMENT);
        sp.assert_pageTitle();
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-579")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - Account",
            description = "Test case checks Account name",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_Account (){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.assert_account(username);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-580")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - App Version",
            description = "Test case checks App Version",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_App_Version (){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.assert_AppVersion(entitiesVersion);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-581")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - Contact Support",
            description = "Test case checks if Contact Support link works",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_Contact_Support(){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.clickOn_supportBtn();
//        TODO Waiting for a fix from Nolan
        LoggerAssert_Failed("Support Page does not open");
        Assert.fail();
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-582")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - Privacy Policy",
            description = "Test case checks if Privacy Policy link work",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_Privacy_Policy(){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.clickOn_privacyPolicyBtn();
        ppp.assert_privacyPageTitle();
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-583")
    @Test(groups= {"Android", "iOS"},
            testName = "Mobile - Settings Page - Privacy Policy",
            description = "Test case checks if Privacy Policy link work",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_Software_Licenses(){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sp.clickOn_softwareLicensesBtn();
        slp.assert_pageTitle();
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-584")
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Settings Page - Sign Out",
            description = "Test case checks if Sign Out function work",
            retryAnalyzer = TcRetry.class)
    public void mobile_Settings_Page_Sign_Out(){

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();


        sp.clickOn_signOutBtn();
        sip.assert_envNameLabel(ENVIRONMENT);
    }




}
