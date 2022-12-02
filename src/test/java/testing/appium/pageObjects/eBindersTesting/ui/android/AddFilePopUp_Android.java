package testing.appium.pageObjects.eBindersTesting.ui.android;

import testing.appium.helpers.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import static testing.appium.helpers.Utils.*;

public class AddFilePopUp_Android {

    AppiumDriver<MobileElement> driver;

    private final int waitInterval = 30;

//    Video
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Show roots\"]")
    MobileElement rootBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Recent\"]")
    MobileElement pageTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"Search\"]")
    MobileElement searchBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"More options\"]")
    MobileElement optionsBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Photos\"]")
    MobileElement photosBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"CLX_Vide.mp4\"]")
    MobileElement firsVideoBtn;

//    Photo
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@text=\"Navigate up\"]")
    MobileElement backBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Select a photo\"]")
    MobileElement pageTitlePhoto;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Camera\"]")
    MobileElement cameraBtn;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@index=\"1\"]")
    MobileElement photoBtn;

//    Document
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"CLX_doc.pdf\"]")
    MobileElement documentBtn;


    public AddFilePopUp_Android(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

//    Video
    public void assertAddFilePage(String addFilePopUpPageTitleAndroid) {
        assertElementPresence_selector_pageSource(driver, pageTitle, waitInterval, "Page Title", addFilePopUpPageTitleAndroid);
    }

    public void rootBtnClick() {
        clickOnElement_selector(driver, rootBtn, waitInterval, "Root Button");
    }

    public void searchBtnClick() {
        clickOnElement_selector(driver, searchBtn, waitInterval, "Search Button");
    }

    public void photosBtnClick() {
        clickOnElement_selector(driver, photosBtn, waitInterval, "Photos Button");
    }

    public void firstFileBtnClick(String pathName) {
        String[] pathNameFull = pathName.split("/");
        String fileName = pathNameFull[1];

        clickOnElement_contains(driver, "name", "text", waitInterval, fileName);
    }

//    Photo
    public void backBtnClick() {
        clickOnElement_selector(driver, backBtn, waitInterval, "Back Button");
    }

    public void assertAddFilePhotoPage(String addFilePopUpPagePhotoTitleAndroid) {
        assertElementPresence_selector_pageSource(driver, pageTitlePhoto, waitInterval, "Photo Page Title", addFilePopUpPagePhotoTitleAndroid);
    }

    public void cameraBtnClick() {
        clickOnElement_selector(driver, cameraBtn, waitInterval, "Camera Button");
    }

    public void mediaBtnClick(String fileName) {

        String dateFull = String.valueOf(Utils.getDate());
        String[] partTimeFull = dateFull.split(" ");
        String month = partTimeFull[1];
        String dayFull = partTimeFull[2];
        String day;

        if ((dayFull.charAt(0) == '0')) {
             day = dayFull.substring(1, 2);
        } else {
            day = dayFull;
        }

        String monthDay = month + " " + day;

        clickOnElement_contains(driver, "name", "content-desc", waitInterval, monthDay);
     }

}




