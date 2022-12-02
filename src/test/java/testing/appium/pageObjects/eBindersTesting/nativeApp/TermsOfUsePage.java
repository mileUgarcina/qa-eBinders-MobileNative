package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.*;


public class TermsOfUsePage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Terms of Use\"]")
    MobileElement pageTitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"You must agree to use Florence. If you Decline, you will be signed out.\"]")
    MobileElement pageTxt;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"I Agree\"]")
    MobileElement agreeBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"I Decline\"]")
    MobileElement declineBtn;





    public TermsOfUsePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_pageTitle() {
        assertElementPresence_selector_pageSource(driver, pageTitle, waitInterval, "Terms of Use Page Title", "Terms of Use");
    }

    public void assert_pageTxt() {
        assertElementPresence_selector_pageSource(driver, pageTxt, waitInterval, "Terms of Use Page Title", "You must agree to use Florence. If you Decline, you will be signed out.");
    }


    public void clickOn_declineBtn() {
        clickOnElement_selector(driver, declineBtn, waitInterval, "Decline Button");
    }


    public boolean elementPresent() {

        boolean element;
        try {
            LoggerWaiting("for \"I Agree\" to be Visible");
            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"I Agree\"]")).isDisplayed();
            LoggerInformation("\"I Agree\" Button is Not Visible");
            element = true;
        } catch (Exception e) {
            element = false;
        }
        return element;
    }
    public void clickOn_iAgreeBtn() {

//        boolean element = false;
//
//        do {scrollDown(driver, 1, 40);
//            try {
//                LoggerWaiting("for \"I Agree\" to be Visible");
//                driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"I Agree\"]")).isDisplayed();
//                element = true;
//            } catch (Exception e) {
//                LoggerInformation("\"I Agree\" Button is Not Visible");
//                element = false;
//            }
//        }
//        while (element);


        clickOnElement_selector(driver, agreeBtn, waitInterval, "I Agree Button");
    }






}


