package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.*;


public class SettingsPage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Settings\"]")
    MobileElement pageTitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"mobile.automation@florencehc.com\"]")
    MobileElement account;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Contact Support\"]")
    MobileElement supportBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Privacy Policy\"]")
    MobileElement privacyPolicyBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Software Licenses\"]")
    MobileElement softwareLicensesBtn;
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Sign out\"]")
    MobileElement signOutBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@index=\"0\"]")
    MobileElement logo;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"UAT\"]")
    MobileElement envLabel;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"v2022.13.1 (1)\"]")
    MobileElement appVersion;


    public SettingsPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_pageTitle() {
        assertElementPresence_selector_pageSource(driver, pageTitle, waitInterval, "Welcome Message", "Settings");
    }

    public void assert_account(String username) {
        assertElementText_contains(driver, "name", "name", "null", "null", waitInterval, username, username, username + " is Present");
    }

    public void clickOn_supportBtn() {
        clickOnElement_selector(driver, supportBtn, waitInterval, "Contact Support Button");
    }

    public void clickOn_privacyPolicyBtn() {
        clickOnElement_selector(driver, privacyPolicyBtn, waitInterval, "Privacy Policy Button");
    }

    public void clickOn_softwareLicensesBtn() {
        clickOnElement_selector(driver, softwareLicensesBtn, waitInterval, "Software Licenses Button");
    }

    public void clickOn_signOutBtn() {
        clickOnElement_selector(driver, signOutBtn, waitInterval, "Sign Out Button");
    }

    public void assert_AppVersion(String appVersion) {
        assertElementPresence_selector_pageSource(driver, pageTitle, waitInterval, "Welcome Message", appVersion);
    }

    public void assert_logo() { assertElementPresence(driver, logo, waitInterval, "Florence Logo");}


    public void clickOn_envBtn(String envName) {

        MobileElement element = driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"" + envName + "\"]"));
        try {
            LoggerWaiting("for \"" + envName + "\" to be Visible");
            waitForElement(driver, waitInterval).until(ExpectedConditions.visibilityOf(element));
            LoggerAssert_Passed("\"" + envName + "\" is Present");
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + envName + "\" not Present: ", ex.getMessage(), true);
        }
            element.click();
        LoggerAction("Click on \"" + envName + "\" Element");
    }


    public void assert_envNameLabel(String envName) {

        MobileElement element = driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"" + envName + "\"]"));
        try {
            LoggerWaiting("for \"" + envName + "\" to be Visible");
            waitForElement(driver, waitInterval).until(ExpectedConditions.visibilityOf(element));
            LoggerAssert_Passed("\"" + envName + "\" is Present");
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + envName + "\" not Present: ", ex.getMessage(), true);
        }
    }




}


