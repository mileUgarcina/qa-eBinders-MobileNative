package testing.appium.runner;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import testing.appium.runner.propertyFile.DataProvider;

import java.net.MalformedURLException;
import java.net.URL;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.runner.propertyFile.DataProvider.browserStackAuthorisationData.*;


public class Init_iOS extends BaseInitConfig {


    /**
     * Creates a driver object for any test if this object already exists - it just
     * returns the existing one
     */
    public AppiumDriver<MobileElement> getDriveriOS(String platformParameter, String suiteName, String testRailParameter, String browserName) {

        switch (platformParameter) {
            case "SauceLabs_iOS_RealDevice_App":
                sauceLabs_iOS_RealDevice_App(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs iOS Real Device - App, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSRealDevice());
                break;
            case "SauceLabs_iOS_RealDevice_Web":
                sauceLabs_iOS_RealDevice_Web(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs iOS Real Device - App, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSRealDevice());
                break;
            case "SauceLabs_iOS_RealDevice_App_Biometric":
                sauceLabs_iOS_RealDevice_App_Biometric(suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs iOS Real Device - App - Biometric, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSRealDevice());
                break;
            case "SauceLabs_iOS_Simulator_App":
                sauceLabs_iOS_Simulator_App(suiteName, testRailParameter);
                LoggerInformation("Capabilities set for SauceLabs iOS Simulator - App, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSSimulator());
                break;
            case "BrowserStack_iOS_RealDevice_App":
                browserStack_iOS_RealDevice_App(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for BrowserStack iOS - App, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSSimulator());
                break;
            case "BrowserStack_iOS_RealDevice_Web":
                browserStack_iOS_RealDevice_Web(browserName, suiteName, testRailParameter);
                LoggerInformation("Capabilities set for BrowserStack iOS - Web, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSSimulator());
                break;
            case "localiOSimulatorSCapsWeb":
                localiOSimulatorSCapsWeb();
                LoggerInformation("Capabilities set for Local iOS - Web, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSSimulator());
                break;
            case "localiOSimulatorSCaps":
                localiOSimulatorSCaps();
                LoggerInformation("Capabilities set for Local iOS Simulator, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSSimulator());
                break;
            case "localiOSRealDeviceCaps":
                localiOSRealDeviceCaps();
                LoggerInformation("Capabilities set for Local iOS Real Devise, OS: " + DataProvider.SliOSConfigData.platformVersionSliOSRealDevice());
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
        driver.get().resetApp();
        driver.get().quit();
        driver = null; // prevent from calling the driver after 'quit()'
    }
}
