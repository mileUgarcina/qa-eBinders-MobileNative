package testing.appium.runner;


import testing.appium.runner.propertyFile.DataProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.runner.propertyFile.DataProvider.browserStackAuthorisationData.*;

public class Init_Android extends BaseInitConfig {


    /**
     * Creates a driver object for any test if this object already exists - it just
     * returns the existing one
     */
    public AppiumDriver<MobileElement> getDriverAndroid(String platformParameter, String suiteName, String testRailParameter, String browserName) {

        switch (platformParameter) {
            case "SauceLabs_Android_RealDevice_App":
                sauceLabs_Android_RealDevice_App(suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs Android Real Device - App,  OS: " + DataProvider.SlAndroidConfigData.platformVersionSlAndroid() );
                break;
            case "SauceLabs_Android_Emulator_App":
                SauceLabs_Android_Emulator_App(suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs Android Emulator - App, OS: " + DataProvider.SlAndroidConfigData.platformVersionSlAndroid());
                break;
            case "SauceLabs_Android_RealDevice_Web":
                SauceLabs_Android_RealDevice_Web(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs Android - Web, OS: " +  DataProvider.SlAndroidConfigData.platformVersionSlAndroid());
                break;
            case "BrowserStack_Android_RealDevice_App":
                browserStack_Android_RealDevice_App(suiteName, testRailParameter);
                LoggerInformation("Capabilities set for BrowserStack Android Real Device - App,  OS: " + DataProvider.SlAndroidConfigData.platformVersionSlAndroid() );
                break;
            case "BrowserStack_Android_RealDevice_Web":
                browserStack_Android_RealDevice_Web(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for BrowserStack Android - Web, OS: " +  DataProvider.SlAndroidConfigData.platformVersionSlAndroid());
                break;
            case "Local_Android_RealDevice_App":
                Local_Android_RealDevice_App();
                LoggerInformation("Capabilities set for Local Android RealDevice- Web, OS: " +  DataProvider.SlAndroidConfigData.platformVersionSlAndroid());
                break;
            case "Local_Android_Emulator_Web":
                Local_Android_Emulator_Web();
                LoggerInformation("Capabilities set for Local Android Emulator - Web, OS: " +  DataProvider.SlAndroidConfigData.platformVersionSlAndroid());
                break;
            case "":
                break;
            default:
                LoggerInformation("There are no suitable Capabilities set for " + platformParameter);
        }

        URL appiumUrl = null;
        try {
            if (useRemoteUrl) {
                if(sauceLabs) {
                    appiumUrl = new URL(DataProvider.slAuthorisationData.SAUCELABSA_URL);
                }else if(browserStack){
                    appiumUrl = new URL("https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESSKEY + BROWSERSTACK_URL);
                }
            } else {
                appiumUrl = new URL(DataProvider.environmentData.LOCAL_APPIUM_URL);
            }

        } catch (MalformedURLException ex) {
            LoggerStep_Failed("Unable to set Appium URL link: " , ex.getMessage(), true);
        }

        if (driver.get() == null) {
            driver.set(new AndroidDriver(appiumUrl, capabilities));
            System.out.println("Caps: " + capabilities);
        }
        return driver.get();
    }

    /**
     * End the test, close app, quit the driver, allow starting new, fresh session
     */
    public void end() {
//        driver.resetApp();
        driver.get().quit();
        driver = null; // prevent from calling the driver after 'quit()'

    }
}
