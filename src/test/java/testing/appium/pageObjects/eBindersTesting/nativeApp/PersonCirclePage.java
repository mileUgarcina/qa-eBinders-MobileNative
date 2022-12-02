package testing.appium.pageObjects.eBindersTesting.nativeApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.assertElementPresence_selector_pageSource;
import static testing.appium.helpers.Utils.clickOnElement_selector;


public class PersonCirclePage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Switch Teams\"]")
    MobileElement switchTeamsBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Settings\"]")
    MobileElement settingsBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Binders\"]")
    MobileElement bindersBtn;



    public PersonCirclePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_WelcomeMsg(String firstName) {
        assertElementPresence_selector_pageSource(driver, settingsBtn, waitInterval, "Welcome Message", firstName);
    }

    public void clickOn_settingsBtn() {
        clickOnElement_selector(driver, settingsBtn, waitInterval, "Settings Button");
    }

    public void clickOn_homeBtn() {
        clickOnElement_selector(driver, settingsBtn, waitInterval, "Home Button");
    }

    public void clickOn_bindersBtn() {
        clickOnElement_selector(driver, bindersBtn, waitInterval, "Binders Button");
    }

//
//    public void assert_environmentPopUTitle(String environmentPopUTitleTxt) {
//        assertElementPresence_selector_pageSource(driver, environmentPopUTitle, waitInterval, "Welcome Message", environmentPopUTitleTxt);
//    }



}


