package testing.appium.pageObjects.eBindersTesting.webApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.font.ShapeGraphicAttribute;

import static testing.appium.helpers.Utils.assertElementPresence_selector_pageSource;
import static testing.appium.helpers.Utils.clickOnElement_selector;


public class ResetPasswordPage {

    static AppiumDriver<MobileElement> driver;

    private final int waitInterval = 15;



    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"If this user exists, an email with instructions for resetting the password will arrive shortly.\"]")
    MobileElement toastMessage;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Reset Your Password\"]")
    MobileElement welcomeMsg;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Request Password Reset\"]")
    MobileElement requestPasswordResetBtn;




    public ResetPasswordPage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void assert_toastMessage() {
        assertElementPresence_selector_pageSource(driver, toastMessage, waitInterval, "Welcome Message", "If this user exists, an email with instructions for resetting the password will arrive shortly.");
    }

    public void assert_WelcomeMsg() {
        assertElementPresence_selector_pageSource(driver, welcomeMsg, waitInterval, "Welcome Message", "Reset Your Password");
    }

    public void clickOn_requestPasswordResetBtn() {
        clickOnElement_selector(driver, requestPasswordResetBtn, waitInterval, "Request Password Reset Button");
    }

    public void clickOn_homeBtn() {
        clickOnElement_selector(driver, requestPasswordResetBtn, waitInterval, "Home Button");
    }



//
//    public void assert_environmentPopUTitle(String environmentPopUTitleTxt) {
//        assertElementPresence_selector_pageSource(driver, environmentPopUTitle, waitInterval, "Welcome Message", environmentPopUTitleTxt);
//    }



}


