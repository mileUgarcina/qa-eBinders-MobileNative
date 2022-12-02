package testing.appium.pageObjects.eBindersTesting.webApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.*;


public class PrivacyPolicyPage {


        static AppiumDriver<MobileElement> driver;

        private final int waitInterval = 30;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@index==\"1\"]")
    MobileElement xBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Accept All Cookies\"]")
    MobileElement acceptAllCookiesBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Reject All Cookies\"]")
    MobileElement rejectAllCookiesBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Privacy\"]")
    MobileElement privacyPageTitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label=\"Address\"]")
    MobileElement pageLink;



    public PrivacyPolicyPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }





    public void assert_privacyPageTitle() {
        assertElementPresence_selector_pageSource(driver, privacyPageTitle, waitInterval, "Privacy Page Title", "Privacy");
    }

    public void assert_pageLink(String url) {
        assertElementPresence_selector_pageSource(driver, pageLink, waitInterval, "Web Page Link", url);
    }

    public void clickOn_pageLink() {
        clickOnElement_selector(driver, pageLink, waitInterval, "Page Link");
    }

    public void clickOn_acceptAllCookiesBtn() {
        clickOnElement_selector(driver, acceptAllCookiesBtn, waitInterval, "Accept All Cookies Button");
    }

    public void clickOn_rejectAllCookiesBtn() {
        clickOnElement_selector(driver, rejectAllCookiesBtn, waitInterval, "Reject All Cookies Button");
    }







}



