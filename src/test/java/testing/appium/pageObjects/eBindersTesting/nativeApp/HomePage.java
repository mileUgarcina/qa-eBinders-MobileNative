package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.*;


public class HomePage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Signature\"]")
    MobileElement signatureBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Home\"]")
    MobileElement homeBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Binders\"]")
    MobileElement bindersBtn;



    public HomePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_WelcomeMsg(String firstName) {
        assertElementPresence_contains(driver,"value", "null", waitInterval, "Hi, " + firstName, "Home Page Welcome Message - Hi, " + firstName);
//        assertElementPresence_selector_pageSource(driver, homeBtn, waitInterval, "Welcome Message", firstName);
    }

    public void clickOn_signatureBtn() {
        clickOnElement_selector(driver, signatureBtn, waitInterval, "Signature Button");
    }

    public void clickOn_homeBtn() {
        clickOnElement_selector(driver, homeBtn, waitInterval, "Home Button");
    }

    public void clickOn_bindersBtn() {
        clickOnElement_selector(driver, bindersBtn, waitInterval, "Binders Button");
    }

//
//    public void assert_environmentPopUTitle(String environmentPopUTitleTxt) {
//        assertElementPresence_selector_pageSource(driver, environmentPopUTitle, waitInterval, "Welcome Message", environmentPopUTitleTxt);
//    }



}


