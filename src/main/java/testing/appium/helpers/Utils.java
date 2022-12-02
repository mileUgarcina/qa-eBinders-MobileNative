package testing.appium.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.io.FileUtils;
import org.jasypt.util.text.StrongTextEncryptor;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import com.asprise.ocr.Ocr;

import javax.imageio.ImageIO;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static testing.appium.helpers.TCLogger.*;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static org.testng.Assert.assertEquals;
import static testing.appium.helpers.TCLogger.LoggerStep_Failed;

public class Utils{

    /**
     * Set WebDriverWait object
     *
     * @param driver - Appium Driver
     * @param waitInterval - Time interval how long to wait for an element
     */
    public static WebDriverWait waitForElement(AppiumDriver<MobileElement> driver, int waitInterval ) {
        return new WebDriverWait(driver, waitInterval);
    }

    /**   Web elements methods **/
    public static void waitVisibility_ofWebElement(AppiumDriver<MobileElement> driver, By elementBy, String elementName, int waitInterval) {
     try {
        LoggerWaiting("To be Visible element: \"" + elementName + "\"");
        waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
        } catch (Exception ex) {
        LoggerStep_Failed("Not Present: " + elementName , ex.getMessage(), true);
     }
    }

    public static WebElement waitToBeClickable(AppiumDriver<MobileElement> driver, By elementBy, String elementName, int waitInterval) {
        try {
            LoggerWaiting("To be Clickable element: \"" + elementName + "\"");
            return waitForElement(driver,waitInterval).until(ExpectedConditions.elementToBeClickable(elementBy));
        } catch (Exception ex) {
            LoggerStep_Failed(" Not Present: \"" + elementName + "\"; ", ex.getMessage(), true);
        }
        return null;
    }

    public static void click_onWebElement_waitClickable(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {
        try {
            Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval)).click();
            LoggerAction("Click on element: \"" + elementName + "\"");
        } catch (Exception ex) {
            LoggerStep_Failed(" Not Present: \"" + elementName + "\"; ", ex.getMessage(), true);
        }
    }

    public static void click_onWebElement_waitVisibility(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {
        try {
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            driver.findElement(elementBy).click();
            LoggerAction("Click on element: \"" + elementName + "\"");
        } catch (Exception ex) {
            LoggerStep_Failed(" Not Present: \"" + elementName + "\"; ", ex.getMessage(), true);
        }
    }

    public static String getElementText_onWebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {
        String elementText = null;
        try {
            elementText = Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval)).getText();
            LoggerAction("Element Text on element: \"" + elementName + "\" is: " + elementText);
        } catch (Exception ex) {
            LoggerStep_Failed("Not Present: \"" + elementName + "\"; ", ex.getMessage(), true);
        }
        return elementText;
    }

    public static void sendKey_toWebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName, String text, boolean clickEnter) {
        String txt;
        if(clickEnter){txt = text + "\n";}else{txt = text;}
        Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval)).sendKeys(txt);
        LoggerAction("Send Text:  \"" + text + "\"  on element: \"" + elementName + "\"");
        if(clickEnter){LoggerAction("Press Enter Key");}
    }

    public static void clearInputField_toWebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {
        try {
            Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval)).clear();
            LoggerAction("Element: \"" + elementName + "\"" + " is cleared");
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static void clearInputField_doubleClick_toWebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {
        try {
            WebElement element = Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval));
            if (!isIOS(driver)) {
            Actions act = new Actions(driver);
            act.doubleClick(element).perform();
            element.sendKeys("\b");
            }else {
                String elementText;
                do {
                    element.sendKeys("\b");
                    elementText = element.getAttribute("value");
                } while (!elementText.equals(""));
            }
            LoggerAction("Element: \"" + elementName + "\"" + " is cleared");
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static void assert_getText_attribute_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String attributeType, String elementName, String expectedTxt) {

        WebElement element = Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval));
        String actualTxt = element.getAttribute(attributeType);

        if(actualTxt.contains(expectedTxt)){
            LoggerAssert_Passed("Verification successful. expected: " + expectedTxt + " == actual: " + actualTxt);
        }
        else{
            LoggerAssert_Failed("Verification unsuccessful! expected: " + expectedTxt + " != actual: " + actualTxt);
            Assert.fail();
        }
    }


    public static void assert_getText_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName, String expectedTxt) {

        waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
        String actualTxt = driver.findElement(elementBy).getText().replace('\n', ' ');

        if(actualTxt.contains(expectedTxt)){
            LoggerAssert_Passed("Verification successful. expected: " + expectedTxt + " == actual: " + actualTxt);
        }
        else{
            LoggerAssert_Failed("Verification unsuccessful! expected: " + expectedTxt + " != actual: " + actualTxt);
            Assert.fail();
        }
    }

    public static void assertElementPresence_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {

        try {
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Present");
        } catch (Exception ex) {
            LoggerStep_Failed("Verification unsuccessful! - element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static void assertElementNotPresent_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {

        boolean present;
        try {
            waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
            present = true;
        } catch (Exception ex) {present = false;}
        
        if(present){
            LoggerAssert_Failed("Verification unsuccessful - element: \"" + elementName + "\" is Present");
            Assert.fail();
        }else{
            LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Not Present");
        }
    }

    public static boolean assertElementType_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName, String elementType, String expectedType) {

        waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
        String type = driver.findElement(elementBy).getAttribute(elementType);
        if(type.equals(expectedType)){
            LoggerAssert_Passed("Verification successful. expected: " + expectedType + " == actual: " + type);
            return true;
        }
        else{
            LoggerAssert_Failed("Verification unsuccessful! expected: " + expectedType + " != actual: " + type);
            return false;
        }
    }

    public static void assertElementPresence_pageSource_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName, String assertText) {

        waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
        if(!driver.getPageSource().contains(assertText)){
            LoggerAssert_Failed("Verification unsuccessful - \"" +  assertText + "\" not Present");
            Assert.fail();
        }else{
            LoggerAssert_Passed("Verification successful - \"" +  assertText + "\" is Present");
        }
    }

    public static String buffer_getText_attribute_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String attributeType, String elementName) {

        WebElement element = Objects.requireNonNull(waitToBeClickable(driver, elementBy, elementName, waitInterval));
        String buffer = element.getAttribute(attributeType);
        LoggerInformation("Buffered value is: " + buffer);
        return buffer;
    }

    public static void click_onWebElement_contains(AppiumDriver<MobileElement> driver, int waitInterval, String elementText, String elementName) {

        MobileElement element = driver.findElement(By.xpath(" //span[contains(text(),'" + elementText + "')]"));
        try {
            LoggerWaiting("To be Clickable element: \"" + elementName + "\"");
            waitForElement(driver,waitInterval).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception ex) {
            LoggerStep_Failed(" Not Present: \"" + elementName + "\"; ", ex.getMessage(), true);
        }
        element.click();
        LoggerAction("Click on element: \"" + elementName + "\"");
    }

    public static void assertElementBorderColor_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName, String RGBColor, String color) {
        try {
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            String borderBottomColor = driver.findElement(elementBy).getCssValue("border-bottom-color");
            if(borderBottomColor.equals(RGBColor)) {
                LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" color is: " + borderBottomColor + " = "+ color);
            }else{
                LoggerAssert_Failed("Verification unsuccessful - element: \"" + elementName + "\" color is not: " + borderBottomColor + " = "+ color);
                Assert.fail();
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static void assertElementIsDisable_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {

        try {
            waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            if(driver.findElement(elementBy).isEnabled()){
                LoggerAssert_Failed("Verification unsuccessful - element: \"" + elementName + "\" is Enabled");
                Assert.fail();
            }else{
                LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Disabled");
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static void assertElementIsEnabled_WebElement(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName) {

        try {
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            if(driver.findElement(elementBy).isEnabled()){
                LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Enabled");
            }else{
                LoggerAssert_Failed("Verification unsuccessful - element: \"" + elementName + "\" is Disabled");
                Assert.fail();
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }

    public static String assert_oneWindowTabIsPresent(AppiumDriver<MobileElement> driver) {

        String originalWindow = driver.getWindowHandle();
        if(driver.getWindowHandles().size() == 1){
            LoggerInformation("One Tab is Present: " + originalWindow);
        }else{
            LoggerInformation("More the one Tab is present: " + originalWindow);
            Assert.fail();
        }
        return originalWindow;
    }

    public static void assert_twoStrings(String expected, String actual) {

        if (expected.equals(actual)) {
            LoggerAssert_Passed("Verification successful. expected: " + expected + " == actual: " + actual);
        } else {
            LoggerAssert_Failed("Verification unsuccessful! expected: " + expected + " != actual: " + actual);
            Assert.fail();
        }
    }

    public static void switchingTabs(AppiumDriver<MobileElement> driver, int waitInterval, String originalWindow) {

        try {
            LoggerWaiting("For New Tab to be Visible element");
            waitForElement(driver,waitInterval).until(numberOfWindowsToBe(2));

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    LoggerAction("Switching Tabs: " + windowHandle);
                    break;
                }
            }
        } catch (Exception ex) {
            LoggerStep_Failed("No New Tab", ex.getMessage(), true);
        }

    }

    public static void wait_foreElementToBeDisabled(AppiumDriver<MobileElement> driver, By elementBy, int waitInterval, String elementName){

        int attempts = 0;
        int maxAttempts = 10;

        try {
            waitVisibility_ofWebElement(driver, elementBy, elementName, waitInterval);
            while(attempts++ <= maxAttempts) {
                LoggerWaiting("To be Disabled element: \"" + elementName + "\"");
                if (!driver.findElement(elementBy).isEnabled()) {
                    LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Disabled");
                    break;
                } else {
                    Thread.sleep(500);
                }
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Element: \"" + elementName + "\" is not Present", ex.getMessage(), true);
        }
    }



    public static void switchingToOriginalTab(AppiumDriver<MobileElement> driver, String originalWindow) {

        driver.close();
        LoggerAction("Closing Current Tab");
        driver.switchTo().window(originalWindow);
        LoggerAction("Switch to Original Tab: " + originalWindow);
    }

    public static void navigateBack_WebElement(AppiumDriver<MobileElement> driver) {

        try {
            driver.navigate().back();
            LoggerAction("Click on Back Button");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to click on Back Button: " , ex.getMessage(), true);
        }
    }

    public static void refreshPage_WebElement(AppiumDriver<MobileElement> driver) {

        try {
            driver.navigate().refresh();
            LoggerAction("Refresh Web Page");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Refresh Web Page: " , ex.getMessage(), true);
        }
    }

    public static void deleteAllCookies_WebElement(AppiumDriver<MobileElement> driver) {

        try {
            driver.manage().deleteAllCookies();
            LoggerAction("Delete Cookies");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Delete Cookies: " , ex.getMessage(), true);
        }
    }

    public static void openWebPage_WebElement(AppiumDriver<MobileElement> driver, String appUrl) {

        try {
            driver.get(appUrl);
            LoggerAction("Enter URL: " + appUrl);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Enter URL: " , ex.getMessage(), true);
        }
    }

    public static void switchToAlertMessage_WebElement(AppiumDriver<MobileElement> driver) {

        try {
            driver.switchTo().alert();
            LoggerAction("Switch to Alert Message");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable Switch to Alert Message: " , ex.getMessage(), true);
        }
    }

    public static void acceptAlertMessage_WebElement(AppiumDriver<MobileElement> driver, int waitInterval) {

        try {
            LoggerWaiting("To be Visible element: \"" + "Alert Message" + "\"");
            waitForElement(driver,waitInterval).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            LoggerAction("Click on OK Button");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Clock on OK Button: " , ex.getMessage(), true);
        }
    }

    public static void dismissAlertMessage_WebElement(AppiumDriver<MobileElement> driver, int waitInterval) {

        try {
            LoggerWaiting("To be Visible element: \"" + "Alert Message" + "\"");
            waitForElement(driver,waitInterval).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
            LoggerAction("Click on Cancel Button");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Clock on Cancel Button: " , ex.getMessage(), true);
        }
    }



    public static void assertAlertMessage_WebElement(AppiumDriver<MobileElement> driver, int waitInterval, String text) {

        try {
            LoggerWaiting("To be Visible element: \"" + "Alert Message" + "\"");
            waitForElement(driver,waitInterval).until(ExpectedConditions.alertIsPresent());
            String alertMessageTxt = driver.switchTo().alert().getText();

            if(alertMessageTxt.equals(text)){
                LoggerAssert_Passed("Verification successful. expected: " +  text + " == actual: " + alertMessageTxt);
            }
            else{
                LoggerAssert_Failed("Verification unsuccessful! expected: " + text + " != actual: " + alertMessageTxt);
                Assert.fail();

            }
        } catch (Exception ex) {
            LoggerStep_Failed("Alert Message Not Present! ", ex.getMessage(), true);
        }
    }





    /**  Mobile elements methods **/



    /**
     * Waiting for an element to be visible using Selector method
     *
     * @param driver - Appium Driver
     * @param selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     *
     */
    public static MobileElement waitToBeVisible_selector(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName) {

        MobileElement element = null;

        try {
            LoggerWaiting("for \"" + elementName + "\" to be Visible");
            element = (MobileElement) waitForElement(driver, waitInterval).until(ExpectedConditions.visibilityOf(selector));
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + elementName + "\" not Present: ", ex.getMessage(), true);
        }
        return element;
    }

    /**
     * Waiting for an element to be clickable using Selector method
     *
     * @param driver - Appium Driver
     * @param selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     *
     */
    public static MobileElement waitToBeClickable_selector(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName) {

        MobileElement element = null;

        try {
            LoggerWaiting("for \"" + elementName + "\" to be Clickable");
            element = (MobileElement) waitForElement(driver, waitInterval).until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + elementName + "\" not Present: ", ex.getMessage(), true);
        }
        return element;
    }

    /**
     * Waiting for an element to be clickable using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     *
     */
    public static MobileElement waitToBeClickable_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue) {

        String locator;
        MobileElement element = null;

        try {
            if (isIOS(driver)) {
                locator = locator_iOS;
            } else {
                locator = locator_Android;
            }

            LoggerWaiting("for \"" + elementValue + "\" element to be Clickable");
            element = (MobileElement) waitForElement(driver, waitInterval).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]")));
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + elementValue + "\" element not Present: ", ex.getMessage(), true);
        }
        return element;
    }

    /**
     * Waiting for an element to be visible using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     *
     */
    public static MobileElement waitToBeVisible_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue) {

        String locator;
        MobileElement element = null;

        try {
            if (isIOS(driver)) {
                locator = locator_iOS;
            } else {
                locator = locator_Android;
            }

            LoggerWaiting("for \"" + elementValue + "\" element to be Clickable");
            element = (MobileElement) waitForElement(driver, waitInterval).until(ExpectedConditions.visibilityOf((WebElement) By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]")));
        } catch (Exception ex) {
            LoggerStep_Failed("\"" + elementValue + "\" element not Present: ", ex.getMessage(), true);
        }
        return element;
    }


    /**
     * Click an element using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     *
     */
    public static void clickOnElement_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue) {

            waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue)
                    .click();
            LoggerAction("Click on \"" + elementValue + "\" element");
    }

    /**
     * Clear an element using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     * @param repeat - Number of repetitions of the action
     * @param logger - Value for logging the element
     *
     */
    public static void clearElement_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue, int repeat, String logger) {

            for (int i=0; i<repeat;i++) {
                waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue).clear();
            }
            LoggerAction("Clear input filed contains: " + logger);
    }

    /**
     * Assert element text using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     * @param assertTxt - Expected value of the text for the assertion
     * @param logger - Value for logging the element
     *
     */
    public static void assertElementText_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String attribute_iOS, String locator_Android, String attribute_Android, int waitInterval, String elementValue, String assertTxt, String logger) {

        String locator;
        String attribute;
        try {
            if(isIOS(driver)){
                locator = locator_iOS;
                attribute = attribute_iOS;
            }else{
                locator = locator_Android;
                attribute = attribute_Android;
            }
            waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);
            try {
                driver.findElement(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]"))
                        .getAttribute(attribute)
                        .contains(assertTxt);
                LoggerAssert_Passed("Verification successful: " + logger);
            } catch (Exception ex) {
                LoggerStep_Failed("Verification unsuccessful: " + logger + " " , ex.getMessage(), true);
            }
        } catch (Exception ex) {
            LoggerStep_Failed("\" " + logger + "\" Element Not Present: " , ex.getMessage(), true);
        }
    }

    /**
     * Assert element presence using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     * @param logger - Value for logging the element
     *
     */
    public static void assertElementPresence_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue, String logger) {

        waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);
        LoggerAssert_Passed("Verification successful - element: \"" + logger + "\" is Present");
    }

    /**
     * Assert element presence
     *
     * @param driver - Appium Driver
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - Value for logging the element
     *
     */
    public static void assertElementPresence(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName ) {
        waitToBeVisible_selector (driver, selector, waitInterval, elementName );
        LoggerAssert_Passed("Verification successful - element: \"" + elementName + "\" is Present");
    }


    /**
     * Assert element text
     *
     * @param driver - Appium Driver
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - Value for logging the element
     * @param assertTxt - text for assertion
     *
     */
    public static void assertElementText(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName, String assertTxt) {
        String elementTxt = waitToBeVisible_selector (driver, selector, waitInterval, elementName).getText();
        if(elementTxt.equals(assertTxt)){
            LoggerAssert_Passed("Verification successful. expected: " + elementTxt + " == actual: " + assertTxt);
        }
        else{
            LoggerAssert_Failed("Verification unsuccessful! expected: " + elementTxt + " != actual: " + assertTxt);
            Assert.fail();

        }

    }

    /**
     * Long click on element using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     * @param longClickDuration - How long will the Long click be applied
     *
     */
    public static void longClickElement_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue, int longClickDuration) {

        String locator;

        if (isIOS(driver)) {
            locator = locator_iOS;
        } else {
            locator = locator_Android;
        }

        waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);

        try {
            TouchAction longPress = new TouchAction(driver);
            if (Utils.isIOS(driver)) {
                longPress.longPress(longPressOptions()
                                .withElement(element(driver.findElement(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]"))))
                                .withDuration(ofSeconds(longClickDuration)))
                        .moveTo(element(driver.findElement(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]"))))
                        .release();
                longPress.perform();
            } else {
                longPress.longPress(element(driver.findElement(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]"))))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(longClickDuration)))
                        .release()
                        .perform();
            }
            LoggerAction("Long Click on \"" + elementValue + "\" Text Message");
        } catch (Exception ex) {
            LoggerStep_Failed("Message \"" + elementValue + "\" not Present: ", ex.getMessage(), true);
        }
    }

    /**
     * Double-click on element using Contains method
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementValue - The value of the pending element
     * @param logger - Value for logging the element
     *
     */
    public static void doubleClickElement_contains(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, int waitInterval, String elementValue, String logger){

        String locator;

        try {
            if(isIOS(driver)){
                locator = locator_iOS;
            }else{
                locator = locator_Android;
            }
            waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);
            TouchActions action = new TouchActions(driver);
            action.moveToElement((WebElement) element(driver.findElement(By.xpath("//*[contains(@" + locator + ", '" + elementValue + "')]"))));
            action.doubleClick();
            action.perform();
            LoggerAction("Double Click on " + logger + " Button");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Double Click on: " + logger , ex.getMessage(), true);
        }
    }

    /**
     * Click an element using selector method
     *
     * @param driver - Appium Driver
     * @param selector - @selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     *
     */
    public static void clickOnElement_selector(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName) {

        waitToBeVisible_selector(driver, selector, waitInterval, elementName)
                .click();
        LoggerAction("Click on " + elementName);

    }

    /**
     * Insert String an element using selector method
     *
     * @param driver - Appium Driver
     * @param selector - @selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     *
     */
    public static void insertString_selector(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName, String insertString) {

        clickOnElement_selector(driver, selector, waitInterval, elementName);
        try {
            selector.sendKeys(insertString);
            LoggerAction("Input " + elementName + ": " + insertString);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to insert text in the field" + elementName, ex.getMessage(), true);
        }
    }

    /**
     * Assert element presence using Selector method wit pageSource option
     *
     * @param driver - Appium Driver
     * @param selector - @selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     * @param assertText - Expected value of the text for the assertion
     *
     */
    public static void assertElementPresence_selector_pageSource(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName, String assertText) {

        waitToBeVisible_selector(driver, selector, waitInterval, elementName);
        if(!driver.getPageSource().contains(assertText)){
            LoggerAssert_Failed("Verification unsuccessful - \"" +  assertText + "\" not Present");
            Assert.fail();
        }else{
            LoggerAssert_Passed("Verification successful - \"" +  assertText + "\" is Present");
        }
    }

    /**
     * Assert element absence using Selector method wit pageSource option
     *
     * @param driver - Appium Driver
     * @param selector - @selector - MobileElement
     * @param waitInterval - Time interval how long to wait for an element
     * @param elementName - The name of the pending element
     * @param assertText - Expected value of the text for the assertion
     *
     */
    public static void assertElementAbsence_selector_pageSource(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval, String elementName, String assertText) {

        waitToBeVisible_selector(driver, selector, waitInterval, elementName);
        if(driver.getPageSource().contains(assertText)){
            LoggerAssert_Failed("Verification unsuccessful - \"" +  assertText + "\" is present");
            Assert.fail();
        }else{
            LoggerAssert_Passed("Verification successful - \"" + assertText + "\" not present");
        }
    }

    /**
     * Assert element presence using Contains method wit pageSource option
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param elementValue - The name of the pending element
     * @param waitInterval - Time interval how long to wait for an element
     * @param assertText - Expected value of the text for the assertion
     *
     */
    public static void assertElementPresence_contains_pageSource(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, String elementValue, int waitInterval, String assertText) {

        waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);
        if(!driver.getPageSource().contains(assertText)){
           LoggerAssert_Failed("Verification unsuccessful - element: \"" +  assertText + "\" not Present");
            Assert.fail();
        }else{
            LoggerAssert_Passed("Verification successful - element: \"" +  assertText + "\" is Present");
        }
    }

    /**
     * Assert element absence using Contains method wit pageSource option
     *
     * @param driver - Appium Driver
     * @param locator_iOS - iOS element locator ("name", "value", "index" ...)
     * @param locator_Android - Android element locator ("content-desc", "value", "index" ...)
     * @param elementValue - The name of the pending element
     * @param waitInterval - Time interval how long to wait for an element
     * @param assertText - Expected value of the text for the assertion
     *
     */
    public static void assertElementAbsence_contains_pageSource(AppiumDriver<MobileElement> driver, String locator_iOS, String locator_Android, String elementValue, int waitInterval, String assertText) {

        waitToBeClickable_contains(driver, locator_iOS, locator_Android, waitInterval, elementValue);
          if (driver.getPageSource().contains(assertText)) {
              LoggerAssert_Failed("Verification unsuccessful - element: \"" + assertText + "\" is present");
              Assert.fail();
          } else {
               LoggerAssert_Passed("Verification successful - element: \"" + assertText + "\" not present");
          }

    }

    /**
     * Hide Keyboard using keyboard Done Key for iOS and Method hideKeyboard for Android
     *
     * @param driver - Appium Driver
     * @param waitInterval - Time interval how long to wait for an element
     *
     */
    public static void hideKeyboard(AppiumDriver<MobileElement> driver, MobileElement selector, int waitInterval) {
        try {
            LoggerWaiting("for Keyboard to be Visible");
            if (isIOS(driver)) {
                waitForElement(driver,waitInterval).until(ExpectedConditions.visibilityOf(selector));
                selector.click();
            } else {
                driver.hideKeyboard();
            }
            LoggerAction("Keyboard Hidden");
        } catch (Exception ex) {
            LoggerInformation("Keyboard Not Present");
        }
    }

    /**
     * Set ScreenOrientation to Landscape
     *
     * @param driver - Appium Driver
     */
    public void setScreenOrientation_Landscape(AppiumDriver<MobileElement> driver) {
        try {
            Thread.sleep(3000);
            assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
            LoggerInformation("Current Screen Orientation " + driver.getOrientation());
            driver.rotate(ScreenOrientation.LANDSCAPE);
            LoggerAction("Switch Screen Orientation to Landscape");
            assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
            LoggerAssert_Passed("Current Screen Orientation " + driver.getOrientation());
        } catch (Exception ex) {
            LoggerStep_Failed("Failed Screen Orientation Landscape: " , ex.getMessage(), true);
        }
    }

    /**
     * Set ScreenOrientation to Portrait
     *
     * @param driver - Appium Driver
     */
    public void setScreenOrientation_Portrait(AppiumDriver<MobileElement> driver) {
        try {
            assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
            LoggerInformation("Current Screen Orientation " + driver.getOrientation());
            driver.rotate(ScreenOrientation.PORTRAIT);
            LoggerAction("Switch Screen Orientation to Portrait");
            assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
            LoggerAssert_Passed("Current Screen Orientation " + driver.getOrientation());
        } catch (Exception ex) {
            LoggerStep_Failed("Failed Screen Orientation: Portrait: " , ex.getMessage(), true);
        }
    }

    /**
     * Start App from appPackage
     *
     * @param driver - Appium Driver
     * @param appPackage - unique identifier for app on the device
     */
    public void startActivity(AppiumDriver<MobileElement> driver, String appPackage) {
        try {
            driver.activateApp(appPackage);
            LoggerInformation("Starting Application: " + appPackage);
        } catch (Exception ex) {
            LoggerStep_Failed("Application Not Starting: " + appPackage + " / " , ex.getMessage(), true);
        }
    }

    /**
     * Tap to the center of the screen
     * @param tapPositionX - Where the action will take place on the screen along the X axis-> 1-bottom of the screen; 2- center of the screen; 5 - top of the screen
     * @param tapPositionY - Where the action will take place on the screen along the Y axis -> 1-bottom of the screen; 2- center of the screen; 5 - top of the screen

     */
    public static void tapOnScreen(AppiumDriver<MobileElement> driver, int tapPositionX, int tapPositionY) {
        String position;
        try {
            TouchAction action = new TouchAction(driver);
            Dimension dim = driver.manage().window().getSize();
            int xPoint = dim.getWidth() / tapPositionX;
            int yPoint = dim.getHeight() / tapPositionY;
            action.tap(PointOption.point(xPoint, yPoint)).perform();
            switch (tapPositionX) {
                case 1:
                    position = "the bottom";
                    break;
                case 3:
                    position = "the center";
                    break;
                case 5:
                    position = "the top";
                    break;
                default:
                    position = "custom position";
            }
            LoggerAction("Tap on " + position + " of the screen");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Tap on the screen: " , ex.getMessage(), true);
        }
    }

    /**
     * Scroll Down the screen "count" number of times
     *
     * @param driver - Appium Driver
     * @param count - Number of scroll repetitions
     */
    public static void scrollDown(AppiumDriver<MobileElement> driver, int count) {

        TouchAction action = new TouchAction(driver);

        Dimension dim = driver.manage().window().getSize();

        int x = dim.getWidth() / 2;
        int startY = (int) (dim.getHeight() * 0.4);
        int endY = (int) (dim.getHeight() * 0.2);

        for (int i = 0; i < count; i++) {
            try {
                action.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(x, endY)).release().perform();
                LoggerAction("Scrolling down " + count + " times" + " -Start:" + startY + "px" + " -End:" + endY + "px");
            } catch (Exception ex) {
                LoggerStep_Failed("Unable to Scroll Down: " , ex.getMessage(), true);
            }

        }
    }

    /**
     * Scroll Up the screen "count" number of times
     *
     * @param driver - Appium Driver
     * @param count - Number of scroll repetitions
     */
    public static void scrollUp(AppiumDriver<MobileElement> driver, int count) {

        TouchAction action = new TouchAction(driver);
        Dimension dim = driver.manage().window().getSize();

        int x = dim.getWidth() / 2;
        int endY = (int) (dim.getHeight() * 0.6);
        int startY = (int) (dim.getHeight() * 0.4);

        for (int i = 0; i < count - 1; i++) {
            try {
                action.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                        .moveTo(PointOption.point(x, endY))
                        .release()
                        .perform();
                LoggerAction("Scrolling up " + count + " times " + "/Start: " + startY + "/ End" + endY);
            } catch (Exception ex) {
                LoggerStep_Failed("Unable to Scroll Up: " , ex.getMessage(), true);
            }
        }
    }

    /**
     * Presence of iOS driver in execution
     *
     * @param driver - Appium Driver
     *
     */
    public static boolean isIOS(AppiumDriver<MobileElement> driver) {
        return driver.getCapabilities().getPlatform().compareTo(Platform.IOS) == 0 || driver.getCapabilities().getPlatform().compareTo(Platform.MAC) == 0;
    }

    /**
     * Android only - click on BACK button
     *
     * @param driver - Appium Driver
     */
    public static void navigateBack(AppiumDriver<MobileElement> driver){

        try {
            driver.navigate().back();
            LoggerAction("Click on Android Native Back Button");
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Click Android Native Back Button: " , ex.getMessage(), true);
        }
    }

    /**
     * Takes screenshot and saves to folder with testID+testRailParameter+timestamp.jpg
     *
     * @param driver - Appium Driver
     * @param testID - Test ID from the test class name
     * @param platformParameter - Data parameter from testNG xml file
     */
    public static void takesScreenshot(AppiumDriver<MobileElement> driver, String testID, String platformParameter) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long ts = timestamp.getTime();
        String projectPath = System.getProperty("user.dir");
        String buildFolderPathName = projectPath + File.separator + "screenshots" + File.separator + "C" + testID + "_" + platformParameter + "_" + ts + ".jpg";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File(buildFolderPathName));
            LoggerInformation("Screenshot Taken");
            LoggerInformation("Screenshot File Path: " + buildFolderPathName);
        } catch (IOException ex) {
            LoggerStep_Failed("Field to Take Screenshot: " , ex.getMessage(), false);
        }
    }

    /**
     * Takes screenshot and return path
     *
     * @param driver - Appium Driver
     * @param testID - Test ID from the test class name
     * @param testRailParameter - Data parameter from testNG xml file
     */
    public static String getScreenshotPath(AppiumDriver<MobileElement> driver, String testID, String testRailParameter) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long ts = timestamp.getTime();
        String projectPath = System.getProperty("user.dir");
        String buildFolderPathName = projectPath + File.separator + "screenshots" + File.separator + testID + "_" + testRailParameter + "_" + ts + ".jpg";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(buildFolderPathName));
            LoggerPrintScreen("Screenshot Taken");
            LoggerPrintScreen("Screenshot File Path: " + buildFolderPathName);
            return buildFolderPathName;
        } catch (IOException ex) {
            LoggerStep_Failed("Field to Take Screenshot: ", ex.getMessage(), false);
        }
        return null;
    }

    /**
     * Delete screenshot
     *
     * @param filePath The path to the file
     */
    public static void deleteFile(String filePath) {

        try {
            File f = new File(filePath);
            f.delete();
            LoggerInformation("File Deleted: " + filePath);
        } catch (Exception e) {
            LoggerInformation("Failed to Delete File: " + filePath);
        }
    }

    /**
     * Reset Application - close app, start App again
     *
     * @param iOSBundleId iOS Application Bundle identifier. No need for Android BundleId.
     */
    public static void resetApp(AppiumDriver<MobileElement> driver, String iOSBundleId) {

        if(!isIOS(driver)){
            LoggerInformation("Terminate Android App");
            driver.resetApp();
            LoggerInformation("Activate Android App");
        }
        else {

            driver.terminateApp(iOSBundleId);
            LoggerInformation("Terminate iOS App: " + iOSBundleId );
            driver.activateApp(iOSBundleId);
            LoggerInformation("Activate iOS App: " + iOSBundleId);
        }
    }

    /**
     * Change Context page
     *  0 for NATIVE
     *  1 for WEBVIEW
     *
     * @param contextNumber - Number 0 or 1
     * @param driver - Appium driver
     *
     */
    public static void changeContext(AppiumDriver<MobileElement> driver, int contextNumber) {

        try {
            Set<String> contextNames = driver.getContextHandles();
            for (String contextName : contextNames) {
                LoggerInformation("Available Context: " + contextName);
            }
            String[] contextsArray = contextNames.toArray(new String[0]);
            LoggerAction("Switch Context to: " + contextsArray[contextNumber]);
            driver.context(contextsArray[contextNumber]);
        } catch (Exception ex) {
            LoggerStep_Failed("Change Context Failed: " , ex.getMessage(), true);
        }
    }

    /**
     * Add File to Android Device
     *
     * @param driver - Android Driver
     * @param remotePath - Path to local file
     * @param pathName - Path to device
     */
    public static void addFileToAndroid(AndroidDriver<MobileElement> driver, String remotePath, String pathName){

        String[] parts = pathName.split("/");
        String fileName = parts[1];

        try {
            driver.pushFile(remotePath + fileName, new File(pathName));
            LoggerInformation("File Push to Android Device: " + remotePath + fileName);
        } catch (IOException ex) {
            LoggerStep_Failed("Unable to add File to Android Device: " , ex.getMessage(), true);
        }
    }

    /**
     * Add File to iOS Device
     *
     * @param driver - iOS Driver
     * @param remotePath - Path to local file
     * @param pathName - Path to device
     */
    public static void addFileToiOS(IOSDriver<MobileElement> driver, String remotePath, String pathName) {

        String[] parts = pathName.split("/");
        String fileName = parts[1];

        try {
            driver.pushFile(fileName, new File(pathName));
            LoggerInformation("File Push to iOS Device: " + remotePath + fileName);
        } catch (IOException ex) {
            LoggerStep_Failed("Unable to add File to iOS Device: " , ex.getMessage(), true);
        }
    }

    /**
     * Get testNG Test Report Log
     * @param result - ITestResult, This class describes the result of a test
     */
    public static String testReport(ITestResult result) {
        try {
            Date d=new Date();
            int year=d.getYear();
            String currentYear= String.valueOf(year+1900);

            return String.valueOf(Reporter.getOutput(result))
//                    .replace("[", "")
//                    .replace("]", "")
                    .replace("\n", "")
                    .replace("\"", "\\\"")
                    .replaceAll(" --- Test Case ", "\\\\n\\\\n--- Test Case")
                    .replaceAll("" + currentYear + "-", "\\\\n"  + currentYear + "-");
        } catch (Exception ex) {
            LoggerStep_Failed("There is no Test Report: " , ex.getMessage(), false);
        }
        return "There is No Test Report";
    }

    /**
     * Get Unix timestamp
     */
    public static int getUnixTime() {

        int unixTime = 0;
        try {
            unixTime = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000L));
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get Unix Time: " , ex.getMessage(), false);
        }
        return unixTime;
    }

    /**
     * Convert Unix timestamps to String Simple Date Format
     * @param unixTime - long variable to convert
     * @param zoneGMT - time zone, e.g. GMT+1
     * @param milliseconds - convert to milliseconds if provided timestamps is raw
     */
    public static String convertUnixTime(long unixTime, String zoneGMT, boolean milliseconds) {

        final String gmtTime;
        final long unixTimeMlSec;

        if (milliseconds) unixTimeMlSec = unixTime * 1000L;
        else unixTimeMlSec = unixTime;

        try {
            Date date = new Date(unixTimeMlSec);
            SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            jdf.setTimeZone(TimeZone.getTimeZone(zoneGMT));
            gmtTime = jdf.format(date);
            return gmtTime;
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to convert Unix Time: " , ex.getMessage(), true);
        }
        return null;
    }

    /**
     * Generate random String
     * @param len string length
     */
    public static String randomString(int len){
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }


    /**
     * Get Current Date and Time (Fri Dec 17 16:07:15 GMT 2021)
     */
    public static Date getDate() {
        java.util.Date date = new java.util.Date();
        LoggerInformation("Current Date: " + date);
        return date;

        }

    /**
     * Copy File
     * @param filePath path of the existing file
     * @param newFileName name of the new file
     * @param fileExtension file type (without the dot ".")
     * @return new file path
     */
    public static String copyFile(String filePath, String newFilePath, String newFileName, String fileExtension){

        Path pathIn = Paths.get(filePath);
        Path pathOut = Paths.get( newFilePath, newFileName + "." + fileExtension);

            try {
                Files.copy(pathIn, pathOut, StandardCopyOption.REPLACE_EXISTING);
                LoggerInformation("File Copy-Rename from: " + filePath +" to: " + pathOut);
                return String.valueOf(pathOut);
        }
            catch (IOException ex) {
                LoggerStep_Failed("Unable to Copy File: " , ex.getMessage(), true);
        }
        return null;
    }

    /**
     * Cut string by space between values
     * @param stringForCut the string we want to shorten
     * @param regex by which character the string will be divided
     * @param part the part of the string we want to take  (eg 0- first part; 1 - second part;...)
     * @return cut string
     */
    public static String cutString(String stringForCut, String regex, int part){

        String partOfString = null;
        try {
            String[] forCutFull = stringForCut.split(regex);
            partOfString = forCutFull[part];
            LoggerInformation("Cut String value is: " + partOfString);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Cut String: " , ex.getMessage(), true);
        }
        return partOfString;
    }

    /**
     * Get Text from Image
     * @param pathname path to the image
     * @return imageTxt string
     */
    public static String getTxtFromImage(String pathname) {
        String imageTxt = null;
        try {
            Ocr.setUp();
            Ocr ocr = new Ocr();
            ocr.startEngine("eng", Ocr.SPEED_FASTEST);
            imageTxt = ocr.recognize(new File[] { new File(pathname) },
            Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
//            LoggerInformation("Text from the Image: " + imageTxt);
            ocr.stopEngine();
            return imageTxt;
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get text from image: " , ex.getMessage(), true);
        }
        return imageTxt;
    }


    /**
     * Compare Two Image
     *
     * @param pathFirstImage  path to the image
     * @param pathSecondImage path to the image
     * @return imageTxt string
     */
    public static double compareTwoImage(String pathFirstImage, String pathSecondImage) {

        double percentage = 0;
        try {
            BufferedImage img1 = ImageIO.read(new File(pathFirstImage));
            BufferedImage img2 = ImageIO.read(new File(pathSecondImage));
            int w1 = img1.getWidth();
            int w2 = img2.getWidth();
            int h1 = img1.getHeight();
            int h2 = img2.getHeight();
            percentage = 0;
            if ((w1 != w2) || (h1 != h2)) {
                LoggerInformation("Both images should have same dimensions");
            } else {
                long diff = 0;
                for (int j = 0; j < h1; j++) {
                    for (int i = 0; i < w1; i++) {
                        //Getting the RGB values of a pixel
                        int pixel1 = img1.getRGB(i, j);
                        Color color1 = new Color(pixel1, true);
                        int r1 = color1.getRed();
                        int g1 = color1.getGreen();
                        int b1 = color1.getBlue();
                        int pixel2 = img2.getRGB(i, j);
                        Color color2 = new Color(pixel2, true);
                        int r2 = color2.getRed();
                        int g2 = color2.getGreen();
                        int b2 = color2.getBlue();
                        //sum of differences of RGB values of the two images
                        long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                        diff = diff + data;
                    }
                }
                double avg = diff / (w1 * h1 * 3);
                percentage = (avg / 255) * 100;
                LoggerInformation("Difference in two images: " + percentage + " %");
            }
        } catch (IOException ex) {
            LoggerStep_Failed("Unable to compare two image: ", ex.getMessage(), true);
        }
        return percentage;
    }


    /**
     * Assert Two Image Difference
     * @param pathFirstImage First Image for Verification
     * @param pathSecondImage Second Image for Verification
     * @param compareTwoImage Difference between the two images in percentage

     */
    public static void assertTwoImageDifference(String pathFirstImage, String pathSecondImage, double compareTwoImage) {

        try {
            if (compareTwoImage > 20) {
                LoggerAssert_Passed("Verification successful, images are Different");
                deleteFile(pathFirstImage);
                deleteFile(pathSecondImage);
            } else {
                LoggerAssert_Failed("Verification Field, images are Not Different");
                deleteFile(pathFirstImage);
                deleteFile(pathSecondImage);
                Assert.fail();
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Assert two image Different: ", ex.getMessage(), true);
        }

    }




    /**
     * Get First Name from email
     * @param email path to the image
     * @return imageTxt string
     */
    public static String firstName(String email) {
        String name;
        String regex;
        if (email.contains("@")) {
            regex = "\\.";
        } else {
            regex = " ";
        }
        String[] part = email.split(regex);
        name = part[0];

        return name;
    }

    /**
     * Get Current Methode Name
     */
    public static String getMethodeName() {
        String methodeName= null;
        try {
            methodeName = new Object(){}.getClass().getEnclosingMethod().getName();
            LoggerInformation("Current Methode Name is: " + methodeName);
            return methodeName;
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get Methode Name : " , ex.getMessage(), false);
            return methodeName;
        }
    }

    /**
     * Get Current Class Name
     */
    public String getClassName() {
        String className= null;
        try {
            className = this.getClass().getName();
            LoggerInformation("Current Class Name is: " + className);
            return className;
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get Class Name : " , ex.getMessage(), false);
            return className;
        }
    }




    /**
     * Verification txt from Image
     * @param assertText Text to be verified
     * @param loggerText Text to be written in the Logger
     * @param driver Appium Driver
     * @param testID Test Case ID
     * @param platformParameter TestNG parameter (Android or iOS)
     */
    public static void assertTextFromImage(AppiumDriver<MobileElement> driver, String assertText, String loggerText, String testID, String platformParameter){

        try {
            String screenShotPath = Utils.getScreenshotPath(driver, testID, platformParameter);
            String imageTxt = Utils.getTxtFromImage(screenShotPath);

            if(imageTxt.contains(assertText)){
                LoggerAssert_Passed("Verification successful: " + loggerText);
                deleteFile(screenShotPath);
            }else{
                LoggerAssert_Passed("Verification Field: " + loggerText);
                deleteFile(screenShotPath);
                Assert.fail();
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to Assert text from image: " , ex.getMessage(), true);
        }
    }

    /**
     * Base64 Encoder
     * @param filePath path to the file to encode
     */
    public static String encodeFile(String filePath) {
        try {
            File fileForEncode = new File(filePath);
            byte[] fileContent = FileUtils.readFileToByteArray(fileForEncode);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException ex) {
            LoggerStep_Failed("Unable to convert file to Base64", ex.getMessage(), false);
        }
        return null;
    }

    /**
     * HMAC - cryptographic method
     * @param algorithm for encoding
     * @param data path string to encode
     * @param key for encoding
     */
    public static String hmacWithApacheCommons(String algorithm, String data, String key) {
        return new HmacUtils(algorithm, key).hmacHex(data);
    }

    /**
     * jasypt for Encoding Data
     * jasypt - cryptographic method
     * @param dataForEncoding path string to encode
     * @param setPassword for encoding
     */
    public static String jasyptEncodingString(String setPassword, String dataForEncoding) {
        String encodedData = null;
        try {
            StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
            textEncryptor.setPassword(setPassword);
            encodedData = textEncryptor.encrypt(dataForEncoding);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to encode data: " + dataForEncoding, String.valueOf(ex.getCause()), false);
        }
        return encodedData;
    }

    /**
     * jasypt for Decoding Data
     * jasypt - cryptographic method
     * @param dataForDecoding path string to encode
     * @param setPassword for encoding
     */
    public static String jasyptDecodingString(String setPassword, String dataForDecoding) {
        String decodedData = null;
        try {
            StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
            textEncryptor.setPassword(setPassword);
            decodedData = textEncryptor.decrypt(dataForDecoding);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to decode data: " + dataForDecoding, String.valueOf(ex.getCause()), false);
        }
        return decodedData;
    }


    public static void printingTheRemainingMinutes(int waitTime, int numberOfMinutes) throws InterruptedException {

         LoggerInformation("The " + numberOfMinutes + "-minute countdown begins");
         while (numberOfMinutes !=0){
             LoggerInformation(numberOfMinutes + " minutes left to go.");
             Thread.sleep(waitTime);
             numberOfMinutes--;
             }
        }



}
