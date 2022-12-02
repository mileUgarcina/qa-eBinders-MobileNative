package testing.appium.pageObjects.eBindersTesting.webApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.*;



public class NeedHelpPage {


        static AppiumDriver<MobileElement> driver;

        private final int waitInterval = 30;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@index==\"1\"]")
    MobileElement xBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Accept All Cookies\"]")
    MobileElement acceptAllCookiesBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Reject All Cookies\"]")
    MobileElement rejectAllCookiesBtn;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"Florence is here to help.\"]")
    MobileElement needHelpPageTitle;



    public NeedHelpPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_needHelpPageTitle() {
        assertElementPresence_selector_pageSource(driver, needHelpPageTitle, waitInterval, "Need Help Page Title", "Florence is here to help.");
    }

    public void clickOn_acceptAllCookiesBtn() {
        clickOnElement_selector(driver, acceptAllCookiesBtn, waitInterval, "Accept All Cookies Button");
    }

    public void clickOn_rejectAllCookiesBtn() {
        clickOnElement_selector(driver, rejectAllCookiesBtn, waitInterval, "Reject All Cookies Button");
    }







}



