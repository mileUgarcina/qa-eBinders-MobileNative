package testing.appium.pageObjects.eBindersTesting.ui.iOS;

import testing.appium.helpers.TCLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static testing.appium.helpers.Utils.*;

public class AddFilePopUp_iOS {

    AppiumDriver<MobileElement> driver;

    private final int waitInterval = 30;

//Video and Photos
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Cancel\"]")
    MobileElement cancelBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Videos\"]")
    MobileElement videosBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Photos\"]")
    MobileElement photosBtn;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Albums\"]")
    MobileElement albumsBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSearchField[@name=\"Photos, People, Places…\"]")
    MobileElement searchField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Video, four seconds, 28 February, 06:55\"]")
    MobileElement firstVideoBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"Screenshot, 18 October 2021, 15:42\"]")
    MobileElement firsPhotoBtn;

//    Documents
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Resents\"]")
    MobileElement documentsMsgTitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSearchField[@name=\"Search\"]")
    MobileElement searchFieldDocument;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[@name=\"receipt, pdf\"]")
    MobileElement firsDocumentBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Resents\"]")
    MobileElement resentsBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Browse\"]")
    MobileElement browseBtn;


    public AddFilePopUp_iOS(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void assertAddFilePopUp(String addFilePopUpSearchField) {
        assertElementPresence_selector_pageSource(driver, searchField, waitInterval, "Search Field", addFilePopUpSearchField);
    }

    public void assertPhotoPage() {
        assertElementPresence_selector_pageSource(driver, photosBtn, waitInterval, "Photos Button","Photos");
    }

    public void assertVideosPage() {
        assertElementPresence_selector_pageSource(driver, videosBtn, waitInterval, "Videos Button","Videos");
    }

    public void cancelBtnClick() {
        clickOnElement_selector(driver, cancelBtn, waitInterval, "Cancel Button");
    }

    public void videosBtnClick() {
        clickOnElement_selector(driver, videosBtn, waitInterval, "Videos Button");
    }

    public void photosBtnClick() {
        clickOnElement_selector(driver, photosBtn, waitInterval, "Photos Button");
    }

    public void albumsBtnClick() {
        clickOnElement_selector(driver, albumsBtn, waitInterval, "Albums Button");
    }

    public void firstVideoBtnClick() {
        clickOnElement_selector(driver, firstVideoBtn, waitInterval, "First Video Button");
    }

    public void firstPhotoBtnClick() {
        clickOnElement_selector(driver, firsPhotoBtn, waitInterval, "First Photo Button");
    }

    public void fillSearchField(String searchFile){
        insertString_selector(driver, searchField, waitInterval, "Search Field", searchFile);
    }



//    Documents

    public void assertDocumentsMsg(String documentsMsg) {
        assertElementPresence_selector_pageSource(driver, documentsMsgTitle, waitInterval, "Documents Message Title", "documentsMsg");
    }

    public void fillSearchFieldDocument(String searchFile){
        insertString_selector(driver, searchFieldDocument, waitInterval, "Search Field", searchFile);
    }

    public void firsDocumentBtnClick() {
        clickOnElement_selector(driver, firsDocumentBtn, waitInterval, "First Document Button");
    }

    public void resentsBtnClick() {
        clickOnElement_selector(driver, resentsBtn, waitInterval, "Resents Button");
    }

    public void browseBtnClick() {
        clickOnElement_selector(driver, browseBtn, waitInterval, "Browse Button");
    }

    public void mediaBtnClick(String fileName) {

        String dateFull = String.valueOf(getDate());
        String[] partTimeFull = dateFull.split(" ");
        String month = partTimeFull[1];
        String dayFull = partTimeFull[2];
        String day;

//        First nine days in month without "0"
//        if ((dayFull.substring(0, 1).equals("0"))) {
//            day = dayFull.substring(1, 2);
//        } else {
//            day = dayFull;
//        }
//        String monthDay = month + " " + day;

        String monthDay = dayFull + ",";

        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            TCLogger.LoggerWaiting("for " + fileName + " File Button to be Visible");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@name, '" + monthDay + "')]")));

//            iOS 14
//            driver.findElement(By.xpath("//*[contains(@name, '" + monthDay + "')]")).click();
//            TCLogger.LoggerAction("Click on " + fileName + " File Button");

//            iOS 15
            List<MobileElement> elementList = driver.findElementsByXPath("//*[contains(@name, '" + monthDay + "')]");
            elementList.get(1).click();

        } catch (Exception ex) {
            TCLogger.LoggerStep_Failed(fileName + " Video Button not Present: ", ex.getMessage(), true);
        }
    }



}

