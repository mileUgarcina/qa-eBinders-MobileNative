package testing.appium.eBinders;

import testing.appium.pageObjects.eBindersTesting.nativeApp.*;
import testing.appium.pageObjects.eBindersTesting.webApp.*;
import testing.appium.runner.Init_Android;
import testing.appium.runner.Init_iOS;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.runner.jiraXrayReporting.JiraXrayAPI.setTcRun_Xray;
import static testing.appium.runner.propertyFile.DataProvider.*;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.getAppStorageFile;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.getTestRunDetailsRD;


public class BaseTestSet {

    public AppiumDriver<MobileElement> driver;
    public String suiteName;
    String sauceLabsTestRunDetails;
    Init_Android initAndroid;
    Init_iOS iniOS;
    SignInPage sip;
    HomePage hp;
    BindersPage bp;
    PersonCirclePage pcp;
    SettingsPage sp;
    NeedHelpPage nhp;
    ResetPasswordPage rpp;
    PrivacyPolicyPage ppp;
    TermsAndUsePage taup;
    SoftwareLicensesPage slp;



    @Parameters({"deviceParameter", "platformParameter", "browserName", "crete_Xray_Test_Run"})
    @BeforeSuite(alwaysRun=true,
                 description = "Set capabilities depending on the platformParameter, Initiate driver and Page Objects, Create Xray Test Run")
    public void startSession(String deviceParameter, String platformParameter, String browserName, boolean crete_Xray_Test_Run, ITestContext context, ITestResult result) {


//        Crete Xray Test Run
        setTcRun_Xray(crete_Xray_Test_Run, platformParameter);
//        Get Application File ID, Name, Environment from SauceLabs
        getAppStorageFile(platformParameter, platformParameter);
//        Get Suite Name from TestNG xml file
        suiteName = context.getCurrentXmlTest().getSuite().getName();
        LoggerInformation("Running Suite: " + suiteName + ", one \"" + ENVIRONMENT + "\" environment" );

        initAndroid = new Init_Android();
        iniOS = new Init_iOS();

//        Driver initialization
        if (platformParameter.contains("Android")) {
            driver = initAndroid.getDriverAndroid(deviceParameter, suiteName, platformParameter, browserName);
        } else {
            driver = iniOS.getDriveriOS(deviceParameter, suiteName, platformParameter, browserName);
        }

//        Get Details of Sauce Labs Run - device name, platform name, OS version...
        sauceLabsTestRunDetails = getTestRunDetailsRD();

        sip = new SignInPage(driver);
        hp = new HomePage(driver);
        bp = new BindersPage(driver);
        pcp = new PersonCirclePage(driver);
        sp = new SettingsPage(driver);
        nhp = new NeedHelpPage(driver);
        rpp = new ResetPasswordPage(driver);
        ppp = new PrivacyPolicyPage(driver);
        taup = new TermsAndUsePage(driver);
        slp = new SoftwareLicensesPage(driver);

    }

    @Parameters({"browserName", "deviceParameter", "platformParameter"})
    @BeforeMethod(alwaysRun=true,
                  description = "Set the Diver attribute for the needs of the result")
    public void beforeMethod(Method method, String browserName, String deviceParameter, String platformParameter,  ITestResult result) {
        result.setAttribute("platformParameter", platformParameter);
        result.setAttribute("driver", driver);
        result.setAttribute("platformParameter", platformParameter);
        result.setAttribute("sauceLabsTestRunDetails", sauceLabsTestRunDetails);
        result.setAttribute("browserName", browserName);
        result.setAttribute("deviceParameter", deviceParameter);
    }


    @AfterTest(alwaysRun=true,
               description = "Quit the driver and close the session")
    public void endSession(){

        driver.quit();
        LoggerAction("Driver quit");


//        System.out.println("getPageSource" + driver.getPageSource());
    }

 }

