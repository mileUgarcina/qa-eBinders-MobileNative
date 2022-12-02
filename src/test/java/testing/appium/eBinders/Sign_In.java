package testing.appium.eBinders;

import testing.appium.eBinders.BaseTestSet;
import testing.appium.helpers.TcRetry;
import testing.appium.helpers.jiraTicket.Bug;
import testing.appium.helpers.testCaseId.TcID;
import org.testng.ITestContext;
import org.testng.annotations.*;
import testing.appium.runner.TestListener_Xray;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.Utils.*;

import static testing.appium.runner.databaseSiding.eConsentAPI.seedData_API;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.*;
import static testing.appium.runner.propertyFile.DataProvider.*;
import static testing.appium.runner.propertyFile.DataProvider.tcData.*;


@Listeners(TestListener_Xray.class)
public class Sign_In extends BaseTestSet {

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
            case "mobile_Valid_Login":
                hp.clickOn_bindersBtn();
                bp.clickOn_personCircleBtn();
                pcp.clickOn_settingsBtn();
                sp.clickOn_signOutBtn();
                sip.assert_envNameLabel(ENVIRONMENT);
                break;
            default:
                resetApp(driver, iOS_BUNDLE_ID);
        }
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-453")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Valid Login",
            description = "Test case checks if logging into the application is possible",
            retryAnalyzer = TcRetry.class)
    public void mobile_Valid_Login(String platformParameter, ITestContext context) {
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.logInProcedure(username, password);
        hp.assert_WelcomeMsg(firstName);


    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-454")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Inactive Account Login",
            description = "Test case checks if logging into the application is possible with Inactive Account",
            retryAnalyzer = TcRetry.class)
    public void mobile_Inactive_Account_Login(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();
        String inactiveUsername = "inactive.accounta@florencehc.com";

        sip.logInProcedure(inactiveUsername, password);
        sip.assert_wrongInputErrorMsg(errorMsg_wrongInput);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-465")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Log In Valid Account Different Env",
            description = "Test case checks if logging into the application is possible with Valid Account on invalid dEnv",
            retryAnalyzer = TcRetry.class)
    public void mobile_Log_In_Valid_Account_Different_Env(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.clickOn_pageLogo();
        sip.assert_environmentPopUTitle("Choose An Environment");
        sip.clickOn_fateEnvBtn();
        sip.assert_envNameLabel("FATE");
        sip.logInProcedure(username, password);
        sip.assert_wrongInputErrorMsg(errorMsg_wrongInput);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-466")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Incorrect email",
            description = "Test case checks if logging into the application is possible with incorrect email",
            retryAnalyzer = TcRetry.class)
    public void mobile_Incorrect_email(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();
        String incorrectEmailFormat = "incorrectEmailFormat";

        sip.insert_emailField(incorrectEmailFormat);
        sip.assert_wrongInputEmailErrorMsg(errorMsg_incorrectEmail);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-454")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Incorrect password correct email",
            description = "Test case checks if logging into the application is possible with incorrect Password",
            retryAnalyzer = TcRetry.class)
    public void mobile_Incorrect_password_correct_email(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();
        String incorrectPassword = "Password1234*";

        sip.logInProcedure(username, incorrectPassword);
        sip.assert_wrongInputErrorMsg(errorMsg_wrongInput);
    }


    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-556")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile - Invalid Password Regex",
            description = "Test case checks if logging into the application is possible with incorrect Password format",
            retryAnalyzer = TcRetry.class)
    public void mobile_Invalid_Password_Regex(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();
        String incorrectPassword = "incorrectPassword";

        sip.insert_emailField(username);
        sip.clickOn_nextBtn();
        sip.insert_passwordField(incorrectPassword);
        sip.assert_wrongInputPasswordErrorMsg(errorMsg_incorrectPasswordFormat);
    }

    @Bug(androidTicket ="null", iOSTicket="null")
    @TcID(tcId = "MOB-578")
    @Parameters({"platformParameter"})
    @Test(groups= {"SmokeTest", "Android", "iOS"},
            testName = "Mobile- Show_Password",
            description = "Test case checks if Show Password option works",
            retryAnalyzer = TcRetry.class)
    public void mobile_Show_Password(String platformParameter, ITestContext context){
        context.setAttribute("platformParameter", platformParameter);

        testCaseName = new Object(){}.getClass().getEnclosingMethod().getName();
        tcId = new Object(){}.getClass().getEnclosingMethod().getAnnotation(TcID.class).tcId();

        sip.insert_emailField(username);
        sip.clickOn_nextBtn();
        sip.insert_passwordField(password);
        sip.assert_passwordField(password, true);
        sip.clickOn_showPasswordBtn();
        sip.assert_passwordField(password, false);
    }




}
