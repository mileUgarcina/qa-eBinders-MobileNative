package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.*;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.iOS_BUNDLE_ID;


public class SignInPage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@index=\"2\"]")
    MobileElement pageLogo;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Welcome back!\"]")
    MobileElement welcomeMsg;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Sign In\"]")
    MobileElement signInBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Next\"]")
    MobileElement nextBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Need Help?\"]")
    MobileElement needHelpBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Privacy Policy\"]")
    MobileElement privacyPolicyBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Terms and Conditions\"]")
    MobileElement termsAndConditionsBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Forgot Password?\"]")
    MobileElement forgotPasswordBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"v2022.13.1 (1)\"]")
    MobileElement appVersionTxt;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@value=\"Enter your account email\"]")
    MobileElement emailField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@value=\"Incorrect email format\"]")
    MobileElement wrongInputEmailErrorMsg;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@value=\"Enter your Florence password\"]")
    MobileElement passwordField_secure;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@value=\"Enter your Florence password\"]")
    MobileElement passwordField_visible;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@value=\"Doesn't meet all requirements\"]")
    MobileElement wrongInputPasswordErrorMsg;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label=\"Show\"]")
    MobileElement showPasswordBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@value=\"Your email or password was incorrect. Please try again.\"]")
    MobileElement wrongInputErrorMsg;


//Environment popUp
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Choose An Environment\"]")
    MobileElement environmentPopUTitle;

    @iOSXCUITFindBy(id = "Sign in to FATE")
    MobileElement fateEnvBtn;

    @iOSXCUITFindBy(id = "Sign in to UAT")
    MobileElement uatEnBtn;

    @iOSXCUITFindBy(id = "Sign in to QA")
    MobileElement qaEnBtn;

    @iOSXCUITFindBy(id = "Sign in to Production")
    MobileElement productionEnvBtn;




    public SignInPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void waitForApp() {
        try {
            LoggerWaiting("For the Application to Load");
            waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOf(welcomeMsg));
            LoggerInformation("Application is Running");
        } catch (Exception ex) {
            LoggerStep_Failed("Application is Not Running: "  , ex.getMessage(), false);
            LoggerInformation("Trying to restart Application");
            resetApp(driver, iOS_BUNDLE_ID);
            try {
                LoggerWaiting("For the Application to Load");
                waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOf(welcomeMsg));
                LoggerInformation("Application is Running");
            } catch (Exception e) {
                LoggerStep_Failed("Application is Not Running: "  , ex.getMessage(), true);
            }
        }
    }


//    public void selectEnvProcedure(String envName, boolean clickOn_signInBtn) {
//        if (clickOn_signInBtn) {clickOn_signInBtn();}
//            clickOn_pageLogo();
////            assert_environmentPopUTitle("Choose An Environment");
//            switch (envName) {
//                case "QA":
//                    clickOn_qaEnvBtn();
//                    break;
//                case "FATE":
//                    clickOn_fateEnvBtn();
//                    break;
//                case "UAT":
//                    clickOn_uatEnvBtn();
//                    break;
//                case "Production":
//                    clickOn_productionEnvBtn();
//                    break;
//            }
//            assert_envNameLabel(envName);
//    }

    public void selectEnvProcedure(String envName, boolean clickOn_signInBtn){
        if (clickOn_signInBtn) {clickOn_signInBtn();}
        clickOn_pageLogo();
        assert_environmentPopUTitle("Choose An Environment");
        clickOn_uatEnvBtn(envName);
        assert_envNameLabel(envName);
    }


    public void logInProcedure(String username, String password){
        insert_emailField(username);
        clickOn_nextBtn();
        insert_passwordField(password);
        clickOn_signInBtn();
    }


    public void assert_WelcomeMsg(String signInWelcomeMsg) {
        assertElementPresence_selector_pageSource(driver, welcomeMsg, waitInterval, "Welcome Message", signInWelcomeMsg);
    }

    public void assert_wrongInputErrorMsg(String wrongInputErrorMsgTxt) {
        assertElementPresence_selector_pageSource(driver, wrongInputErrorMsg, waitInterval, "Wrong Input Email Error Message", wrongInputErrorMsgTxt);
    }

    public void assert_envNameLabel(String envName) {
        assertElementText_contains(driver, "name", "name", "null", "null", waitInterval, envName, envName, envName + " Environment is selected");
    }

    public void clickOn_pageLogo() {
        clickOnElement_selector(driver, pageLogo, waitInterval, "Florence Logo");
    }

    public void insert_emailField(String email) {
        insertString_selector(driver, emailField, waitInterval,"Email Field", email + "\n");
    }

    public void assert_wrongInputPasswordErrorMsg(String wrongInputPasswordErrorMsgTxt) {
        assertElementPresence_selector_pageSource(driver, wrongInputPasswordErrorMsg, waitInterval, "Wrong Input Password Error Message", wrongInputPasswordErrorMsgTxt);
    }

    public void assert_wrongInputEmailErrorMsg(String wrongInputEmailErrorMsgTxt) {
        assertElementPresence_selector_pageSource(driver, wrongInputEmailErrorMsg, waitInterval, "Wrong Input Error Message", wrongInputEmailErrorMsgTxt);
    }

    public void insert_passwordField(String password){
        insertString_selector(driver, passwordField_secure, waitInterval, "Password Field", password + "\n");
    }

    public void clickOn_passwordField() {
        clickOnElement_selector(driver, passwordField_secure, waitInterval, "Password Field");
    }

    public void assert_passwordField(String password, boolean secure) {

        String passwordTxt;
        if(secure){passwordTxt = "•".repeat(password.length());
        }else{passwordTxt = password;}

        assertElementText_contains(driver, "value", "value", "null", "null", waitInterval, passwordTxt, passwordTxt, "Password is: \"" + passwordTxt + "\"");
    }

    public void clickOn_signInBtn() {
        clickOnElement_selector(driver, signInBtn, waitInterval, "Sign In Button");
    }

    public void clickOn_showPasswordBtn() {
        clickOnElement_selector(driver, showPasswordBtn, waitInterval, "Show Password Button");
    }

    public void clickOn_nextBtn() {
        clickOnElement_selector(driver, nextBtn, waitInterval, "Next Button");
    }

    public void assert_nextBtn() { assertElementPresence(driver, nextBtn, waitInterval, "Next Button");}

    public void clickOn_needHelpBtn() {
        clickOnElement_selector(driver, needHelpBtn, waitInterval, "Need Help Button");
    }

    public void clickOn_privacyPolicyBtn() {
        clickOnElement_selector(driver, privacyPolicyBtn, waitInterval, "Privacy Policy Button");
    }

    public void clickOn_termsAndConditionsBtn() {
        clickOnElement_selector(driver, termsAndConditionsBtn, waitInterval, "Terms And Conditions Button");
    }

    public void clickOn_forgotPasswordBtn() {
        clickOnElement_selector(driver, forgotPasswordBtn, waitInterval, "Forgot Password Button");
    }

    public void assert_AppVersion(String appVersion) {
        assertElementPresence_selector_pageSource(driver, welcomeMsg, waitInterval, "Welcome Message", appVersion);
    }


//    Environment PopUp
    public void assert_environmentPopUTitle(String environmentPopUTitleTxt) {
        assertElementPresence_selector_pageSource(driver, environmentPopUTitle, waitInterval, "Welcome Message", environmentPopUTitleTxt);
    }

    public void clickOn_uatEnvBtn(String envName) {
        clickOnElement_contains(driver, "name", "null", waitInterval, envName);
    }

    public void clickOn_productionEnvBtn() {
        clickOnElement_selector(driver, productionEnvBtn, waitInterval, "Production Environment Button");
    }

    public void clickOn_uatEnvBtn() {
        clickOnElement_selector(driver, uatEnBtn, waitInterval, "UAT Environment Button");
    }

    public void clickOn_qaEnvBtn() {
        clickOnElement_selector(driver, qaEnBtn, waitInterval, "QA Environment Button");
    }

    public void clickOn_fateEnvBtn() {
        clickOnElement_selector(driver, fateEnvBtn, waitInterval, "FATE Environment Button");
    }

    public void clickOn_envBtn(String envName) {
        clickOnElement_contains(driver, "name", "null", waitInterval, "Sign in to " + envName);
    }











}


