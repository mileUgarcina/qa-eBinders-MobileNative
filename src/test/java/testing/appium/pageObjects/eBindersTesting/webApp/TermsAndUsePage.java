package testing.appium.pageObjects.eBindersTesting.webApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.assertElementPresence_selector_pageSource;
import static testing.appium.helpers.Utils.clickOnElement_selector;


public class TermsAndUsePage {


        static AppiumDriver<MobileElement> driver;

        private final int waitInterval = 30;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@index==\"1\"]")
    MobileElement xBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Accept All Cookies\"]")
    MobileElement acceptAllCookiesBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Reject All Cookies\"]")
    MobileElement rejectAllCookiesBtn;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"Terms of Use\"]")
    MobileElement termsOfUsePageTitle;



    public TermsAndUsePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }





    public void assert_termsOfUsePageTitle() {
        assertElementPresence_selector_pageSource(driver, termsOfUsePageTitle, waitInterval, "Terms Of Use Page Title", "Terms of Use");
    }

    public void clickOn_acceptAllCookiesBtn() {
        clickOnElement_selector(driver, acceptAllCookiesBtn, waitInterval, "Accept All Cookies Button");
    }

    public void clickOn_rejectAllCookiesBtn() {
        clickOnElement_selector(driver, rejectAllCookiesBtn, waitInterval, "Reject All Cookies Button");
    }







}



