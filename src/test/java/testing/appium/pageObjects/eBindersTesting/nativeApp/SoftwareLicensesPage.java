package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.*;


public class SoftwareLicensesPage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Close\"]")
    MobileElement closeBnt;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@value=\"Software Licenses\"]")
    MobileElement pageTitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"This app uses the following 3rd-party libraries.\"]")
    MobileElement pageTxt;



    public SoftwareLicensesPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_pageTitle() {
        assertElementPresence_selector_pageSource(driver, closeBnt, waitInterval, "Software Licenses Page Title", "Software Licenses");
    }

    public void assert_pageTxt() {
        assertElementPresence(driver, pageTxt, waitInterval,"Software Licenses Page Text");
    }


    public void clickOn_closeBnt() {
        clickOnElement_selector(driver, closeBnt, waitInterval, "Close Button");
    }



}


