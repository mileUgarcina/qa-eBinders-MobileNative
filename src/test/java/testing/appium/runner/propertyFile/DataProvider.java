package testing.appium.runner.propertyFile;


import ch.clx.testing.appium.runner.Config;
import testing.appium.helpers.Utils;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.jasyptDecodingString;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.*;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.appVersionBuild;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.slAppId;


/**
 * These methods stored value as String from Resources Properties Files using the PropertyReader method
 */

public class DataProvider {


    /**
     * Data Provider for Environment variable
     */
    public static String ENVIRONMENT = System.getProperty("env");

    public static String randomString =  Utils.randomString(5);

    /**
     * Data Provider for Local Android Configuration
     */
    public static class LocalAndroidConfigData {
        public static String PLATFORM_NAME_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("platformNameLocalAndroid");
        public static String PLATFORM_VERSION_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("platformVersion");
        public static String DEVICE_NAME_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("deviceNameLocalAndroid");
        public static String BROWSER_NAME_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("browser_Name");
        public static String AUTOMATION_NAME_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("automationName");

        public static String appPackageLocalAndroid() {
            String appPackageLocalAndroid = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appPackageLocalAndroid = Config.qa.Android.PackageId;}
            else if ((ENVIRONMENT.equals(ENV_VARIABLE_FATE))) {appPackageLocalAndroid = Config.fate.Android.PackageId;}
            else if ((ENVIRONMENT.equals(ENV_VARIABLE_UAT))) {appPackageLocalAndroid = Config.uat.Android.PackageId;}
            return appPackageLocalAndroid;}
        public static String APP_ACTIVITY_LOCAL_ANDROID = PropertyReader.LocalAndroidConfigReader.get("appActivityLocalAndroid");
    }

    /**
     * Data Provider for Sauce Lab Android Configuration
     */
    public static class SlAndroidConfigData {
        public static String platformVersionSlAndroid() {
            String platformVersionSlAndroid = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {platformVersionSlAndroid = Config.qa.Android.OSVersion;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {platformVersionSlAndroid = Config.fate.Android.OSVersion;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {platformVersionSlAndroid = Config.uat.Android.OSVersion;}
            return platformVersionSlAndroid;}
        public static String nameSlAndroid() {
            String nameSlAndroid = null;
            switch (MANUAL_SAUCELABS_DATA) {
                case "true":
                    if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {
                        nameSlAndroid = Config.qa.Android.SauceLabsName;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {
                        nameSlAndroid = Config.fate.Android.SauceLabsName;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {
                        nameSlAndroid = Config.uat.Android.SauceLabsName;
                    }
                    break;
                case "false":
                    nameSlAndroid = DEV_TEAM + "-" + appVersionBuild;
                    break;
            }
            return nameSlAndroid;
        }
        public static String appSlAndroid() {
            String appSlAndroid = null;
            switch (MANUAL_SAUCELABS_DATA) {
                case "true":
                    if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appSlAndroid = "storage:" + Config.qa.Android.SauceLabsId;}
                    else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {appSlAndroid = "storage:" + Config.fate.Android.SauceLabsId;}
                    else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {appSlAndroid = "storage:" + Config.uat.Android.SauceLabsId;}
                    break;
                case "false":
                    appSlAndroid = "storage:" + slAppId;
                    break;
            }
            return appSlAndroid;
        }
        public static String appPackageSlAndroid() {
            String appPackageSlAndroid = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appPackageSlAndroid = Config.qa.Android.PackageId;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {appPackageSlAndroid = Config.fate.Android.PackageId;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {appPackageSlAndroid = Config.uat.Android.PackageId;}
            return appPackageSlAndroid;}
        public static String APPIUM_VERSION_SL_ANDROID_REAL_DEVICE = PropertyReader.SlAndroidConfigurationReader.get("appiumVersionSlAndroid_RealDevice");
        public static String APPIUM_VERSION_SL_ANDROID_EMULATOR = PropertyReader.SlAndroidConfigurationReader.get("appiumVersionSlAndroid_Emulator");
        public static String PLATFORM_NAME_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("platformNameSlAndroid");
        public static String DEVICE_NAME_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("deviceNameSlAndroid");
        public static String BROWSER_NAME_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("deviceNameSlAndroid");
        public static String DEVICE_ORIENTATION_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("deviceOrientationSlAndroid");
        public static String PHONE_ONLY_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("phoneOnlySlAndroid");
        public static String NO_RESET_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("noResetSlAndroid");
        public static String APP_ACTIVITY_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("appActivitySlAndroid");
        public static String ENABLE_TOUCH_ID_ENROLL_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("enableTouchIdEnrollSlAndroid");
        public static String DISABLE_TOUCH_ID_ENROLL_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("disableTouchIdEnrollSlAndroid");
        public static String AUTO_GRANT_PERMISSIONS = PropertyReader.SlAndroidConfigurationReader.get("autoGrantPermissions");
        public static String NETWORK_CAPTURE_SL_ANDROID = PropertyReader.SlAndroidConfigurationReader.get("networkCaptureAndroid");

    }

    /**
     * Data Provider for Local iOS Configuration
     */
    public static class LocaliOSConfigurationData {
        //        Local capabilities for iOS Simulator
        public static String PLATFORM_NAME_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("platformNameLocalSimulatoriOS");
        public static String DEVICE_NAME_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("deviceNameLocalSimulatoriOS");
        public static String AUTOMATION_NAME_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("automationName");
        public static String NO_RESET_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("noReset");
        public static String PLATFORM_VERSION_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("appPackageLocalAndroid");
        public static String USE_NEW_WDA_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("useNewWDALocalSimulatoriOS");
        public static String AUTO_ACCEPT_ALERTS_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("autoAcceptAlertsLocalSimulatoriOS");
        public static String APP_LOCAL_SIMULATOR_IOS = PropertyReader.LocaliOSConfigurationReader.get("appLocalSimulatoriOS");

        //        Local capabilities for iOS Real Device
        public static String DEVICE_NAME_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("deviceNameLocaliOS");
        public static String BROWSER_NAME_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("browserNameLocaliOS");
        public static String UDID_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("udidLocaliOS");
        public static String BUNDLE_ID_LOCA_LIOS = PropertyReader.LocaliOSConfigurationReader.get("bundleIdLocaliOS");
        public static String APP_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("appLocaliOS");
        public static String XCODE_ORG_ID_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("xcodeOrgIdLocaliOS");
        public static String XCODE_SIGNING_ID_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("xcodeSigningIdLocaliOS");
        public static String UPDATED_WDA_BUNDLE_ID_LOCAL_IOS = PropertyReader.LocaliOSConfigurationReader.get("updatedWDABundleIdLocaliOS");
    }

    /**
     * Data Provider for Sauce Lab iOS Configuration
     */
    public static class SliOSConfigData {
        public static String platformVersionSliOSRealDevice() {
            String platformVersionSliOSRealDevice = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {platformVersionSliOSRealDevice = Config.qa.iOS.OSVersionRealDevice;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {platformVersionSliOSRealDevice = Config.fate.iOS.OSVersionRealDevice;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {platformVersionSliOSRealDevice = Config.uat.iOS.OSVersionRealDevice;}
            return platformVersionSliOSRealDevice;}

        public static String platformVersionSliOSSimulator() {
            String platformVersionSliOSSimulator = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {platformVersionSliOSSimulator = Config.qa.iOS.OSVersionSimulator;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {platformVersionSliOSSimulator = Config.fate.iOS.OSVersionSimulator;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {platformVersionSliOSSimulator = Config.uat.iOS.OSVersionSimulator;}
            return platformVersionSliOSSimulator;}

        public static String appSliOS() {
            String appSliOS = null;
            switch (MANUAL_SAUCELABS_DATA) {
                case "true":
                    if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {
                        appSliOS = "storage:" + Config.qa.iOS.SauceLabsId;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {
                        appSliOS = "storage:" + Config.fate.iOS.SauceLabsId;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {
                        appSliOS = "storage:" + Config.uat.iOS.SauceLabsId;
                    }
                    break;
                case "false":
                    appSliOS = "storage:" + slAppId;
                    break;
            }
            return appSliOS;
            }

        public static String nameSliOS() {
            String nameSliOS = null;
            switch (MANUAL_SAUCELABS_DATA) {
                case "true":
                    if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {
                        nameSliOS = Config.qa.iOS.SauceLabsName;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {
                        nameSliOS = Config.fate.iOS.SauceLabsName;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {
                        nameSliOS = Config.uat.iOS.SauceLabsName;
                    }
                    break;
                case "false":
                    nameSliOS = DEV_TEAM + "-" + appVersionBuild;
                    break;
            }
                return nameSliOS;
            }

        public static String buildSliOS() {
            String buildSliOS = null;
            switch (MANUAL_SAUCELABS_DATA) {
                case "true":
                    if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {
                        buildSliOS = Config.qa.iOS.SauceLabsId;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {
                        buildSliOS = Config.fate.iOS.SauceLabsId;
                    } else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {
                        buildSliOS = Config.uat.iOS.SauceLabsId;
                    }
                    break;
                case "false":
                    buildSliOS = appVersionBuild;
                    break;
            }
            return buildSliOS;
        }

        public static String PLATFORM_NAME_SL_IOS = PropertyReader.SliOSConfigurationReader.get("platformNameSliOS");
        public static String BROWSER_NAME_SL_IOS = PropertyReader.SliOSConfigurationReader.get("browserNameSliOS");
        public static String PHONE_ONLY_SL_IOS = PropertyReader.SliOSConfigurationReader.get("phoneOnlySliOS");
        public static String NO_RESET_SL_IOS = PropertyReader.SliOSConfigurationReader.get("noResetSliOS");
        public static String AUTOMATION_NAME_SL_IOS = PropertyReader.SliOSConfigurationReader.get("automationNameSliOS");
        public static String USE_NEW_WDA_SL_IOS = PropertyReader.SliOSConfigurationReader.get("useNewWDASliOS");
//        public static String NAME_SL_IOS = PropertyReader.SliOSConfigurationReader.get("nameSliOS");
//        public static String APP_SL_IOS = PropertyReader.SliOSConfigurationReader.get("appSliOS");
        public static String AUTO_ACCEPT_ALERTS_SL_IOS = PropertyReader.SliOSConfigurationReader.get("autoAcceptAlertsSliOS");
        public static String APPIUM_VERSION_SL_IOS_REAL_DEVICE = PropertyReader.SliOSConfigurationReader.get("appiumVersionSliOS_RealDevice");
        public static String APPIUM_VERSION_SL_IOS_SIMULATOR = PropertyReader.SliOSConfigurationReader.get("appiumVersionSliOS_Simulator");
        public static String DEVICE_NAME_SL_IOS_SIMULATOR = PropertyReader.SliOSConfigurationReader.get("deviceNameSliOSSimulator");
        public static String DEVICE_NAME_SL_IOS_REAL_DEVICE = PropertyReader.SliOSConfigurationReader.get("deviceNameSliOSRealDevice");

        public static String ALLOW_TOUCH_ID_ENROLL_SL_IOS = PropertyReader.SliOSConfigurationReader.get("allowTouchIdEnrollSliOS");
        public static String NETWORK_CAPTURE_SL_iOS = PropertyReader.SlAndroidConfigurationReader.get("networkCaptureSliOS");

    }


    /**
     * Data Provider for SL Authorisation Data
     */
    public static class slAuthorisationData {
        public static String SL_TEST_RESULT_LINK = PropertyReader.SlAuthorisationDataReader.get("sLTestResultLink");
        public static String POWERED_BY = PropertyReader.SlAuthorisationDataReader.get("poweredBy");

        public static String USERNAME = PropertyReader.SlAuthorisationDataReader.get("username");
//        public static String ACCESS_KEY = PropertyReader.SlAuthorisationDataReader.get("accessKey");
        public static String ACCESS_KEY = jasyptDecodingString(DEV_TEAM, PropertyReader.SlAuthorisationDataReader.get("accessKey2"));
        public static String SAUCELABS_TOKEN = jasyptDecodingString(DEV_TEAM, PropertyReader.SlAuthorisationDataReader.get("sauceLabsToken"));
        public static String VIRTUAL_DEVICE_LINK = PropertyReader.SlAuthorisationDataReader.get("virtualDeviceLink");
        public static String REAL_DEVICE_LINK = PropertyReader.SlAuthorisationDataReader.get("realDeviceLink");
        public static String REAL_DEVICE_LINK_LIMIT = PropertyReader.SlAuthorisationDataReader.get("limit");
        public static String GET_APP_STORAGE_FILES= PropertyReader.SlAuthorisationDataReader.get("GetAppStorageFiles");
        public static String SAUCELABSA_URL= PropertyReader.SlAuthorisationDataReader.get("sauceLabsA_url");
    }

    /**
     * Data Provider for BrowserStack Authorisation Data
     */
    public static class browserStackAuthorisationData {
        public static String BROWSERSTACK_USERNAME = PropertyReader.browserStackAuthorisationDataReader.get("browserstack_username");
        public static String BROWSERSTACK_ACCESSKEY = jasyptDecodingString(DEV_TEAM, PropertyReader.browserStackAuthorisationDataReader.get("browserstack_accessKey"));
        public static String BROWSERSTACK_URL = PropertyReader.browserStackAuthorisationDataReader.get("browserStack_url");
    }


    /**
     * Data Provider for Clinical Contract - Username
     */
    public static String clinicalUsername(String platformParameter) {
        String clinicalUsernameData = null;
        if(platformParameter.equals("Android")) {
                 if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalUsernameData = Config.qa.Android.Clinical.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalUsernameData = Config.fate.Android.Clinical.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalUsernameData = Config.uat.Android.Clinical.Username;}
        } else {
                 if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalUsernameData = Config.qa.iOS.Clinical.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalUsernameData = Config.fate.iOS.Clinical.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalUsernameData = Config.uat.iOS.Clinical.Username;}
        }
        LoggerInformation("Clinical credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Username: " + clinicalUsernameData);
        return clinicalUsernameData;
    }


    /**
     * Data Provider for Clinical Contract - Password
     */
    public static String clinicalPassword(String platformParameter) {
        String ClinicalPasswordData = null;
        if(platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { ClinicalPasswordData = Config.qa.Android.Clinical.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ ClinicalPasswordData = Config.fate.Android.Clinical.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ ClinicalPasswordData = Config.uat.Android.Clinical.Password;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { ClinicalPasswordData = Config.qa.iOS.Clinical.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ ClinicalPasswordData = Config.fate.iOS.Clinical.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ ClinicalPasswordData = Config.uat.iOS.Clinical.Password;}
        }
        LoggerInformation("Clinical credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Password: " + ClinicalPasswordData);
        return ClinicalPasswordData;
    }

    /**
     * Data Provider for Clinical - First Name
     */
    public static String clinicalFirstName(String platformParameter) {
        String clinicalFirstNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalFirstNameData = Config.qa.Android.Clinical.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalFirstNameData = Config.fate.Android.Clinical.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalFirstNameData = Config.uat.Android.Clinical.FirstName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalFirstNameData = Config.qa.iOS.Clinical.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalFirstNameData = Config.fate.iOS.Clinical.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalFirstNameData = Config.uat.iOS.Clinical.FirstName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --First Name: " + clinicalFirstNameData);
        return clinicalFirstNameData;
    }

    /**
     * Data Provider for Clinical - Middle Name
     */
    public static String clinicalMiddleName(String platformParameter) {
        String clinicalMiddleNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalMiddleNameData = Config.qa.Android.Clinical.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalMiddleNameData = Config.fate.Android.Clinical.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalMiddleNameData = Config.uat.Android.Clinical.MiddleName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalMiddleNameData = Config.qa.iOS.Clinical.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalMiddleNameData = Config.fate.iOS.Clinical.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalMiddleNameData = Config.uat.iOS.Clinical.MiddleName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Middle Name: " + clinicalMiddleNameData);
        return clinicalMiddleNameData;
    }

    /**
     * Data Provider for Clinical - Last Name
     */
    public static String clinicalLastName(String platformParameter) {
        String clinicalLastNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalLastNameData = Config.qa.Android.Clinical.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalLastNameData = Config.fate.Android.Clinical.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalLastNameData = Config.uat.Android.Clinical.LastName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { clinicalLastNameData = Config.qa.iOS.Clinical.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ clinicalLastNameData = Config.fate.iOS.Clinical.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ clinicalLastNameData = Config.uat.iOS.Clinical.LastName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Last Name: " + clinicalLastNameData);
        return clinicalLastNameData;
    }

    /**
     * Data Provider for Participant Contract - Username
     */
    public static String participantUsername(String platformParameter) {

        String participantUsernameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantUsernameData = Config.qa.Android.Participant.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantUsernameData = Config.fate.Android.Participant.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantUsernameData = Config.uat.Android.Participant.Username;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantUsernameData = Config.qa.iOS.Participant.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantUsernameData = Config.fate.iOS.Participant.Username;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantUsernameData = Config.uat.iOS.Participant.Username;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Username: " + participantUsernameData);
        return participantUsernameData;
    }

    /**
     * Data Provider for Participant - Password
     */
    public static String participantPassword(String platformParameter) {
        String participantPasswordData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantPasswordData = Config.qa.Android.Participant.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantPasswordData = Config.fate.Android.Participant.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantPasswordData = Config.uat.Android.Participant.Password;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantPasswordData = Config.qa.iOS.Participant.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantPasswordData = Config.fate.iOS.Participant.Password;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantPasswordData = Config.uat.iOS.Participant.Password;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Password: " + participantPasswordData);
        return participantPasswordData;
    }

    /**
     * Data Provider for Participant - First Name
     */
    public static String participantFirstName(String platformParameter) {
        String participantFirstNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantFirstNameData = Config.qa.Android.Participant.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantFirstNameData = Config.fate.Android.Participant.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantFirstNameData = Config.uat.Android.Participant.FirstName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantFirstNameData = Config.qa.iOS.Participant.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantFirstNameData = Config.fate.iOS.Participant.FirstName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantFirstNameData = Config.uat.iOS.Participant.FirstName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --First Name: " + participantFirstNameData);
        return participantFirstNameData;
    }

    /**
     * Data Provider for Participant - Middle Name
     */
    public static String participantMiddleName(String platformParameter) {
        String participantMiddleNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantMiddleNameData = Config.qa.Android.Participant.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantMiddleNameData = Config.fate.Android.Participant.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantMiddleNameData = Config.uat.Android.Participant.MiddleName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantMiddleNameData = Config.qa.iOS.Participant.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantMiddleNameData = Config.fate.iOS.Participant.MiddleName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantMiddleNameData = Config.uat.iOS.Participant.MiddleName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Middle Name: " + participantMiddleNameData);
        return participantMiddleNameData;
    }

    /**
     * Data Provider for Participant - Last Name
     */
    public static String participantLastName(String platformParameter) {
        String participantLastNameData = null;
        if (platformParameter.equals("Android")) {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantLastNameData = Config.qa.Android.Participant.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantLastNameData = Config.fate.Android.Participant.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantLastNameData = Config.uat.Android.Participant.LastName;}
        } else {
            if(ENVIRONMENT.equals(ENV_VARIABLE_QA)) { participantLastNameData = Config.qa.iOS.Participant.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_FATE)){ participantLastNameData = Config.fate.iOS.Participant.LastName;}
            else if(ENVIRONMENT.equals(ENV_VARIABLE_UAT)){ participantLastNameData = Config.uat.iOS.Participant.LastName;}
        }
        LoggerInformation( "Participant credentials --environment: " + ENVIRONMENT + " --platform: " + platformParameter + " --Last Name: " + participantLastNameData);
        return participantLastNameData;
    }


    /**
     * Data Provider for username (Clinical/Participant)
     */
    public static String username(String user, String platformParameter) {
        String username;
        if(user.contains("Clinical")) {
            username = clinicalUsername(platformParameter);
        }else{
            username = participantUsername(platformParameter);
        }
        return username;
    }

    /**
     * Data Provider for password (Clinical/Participant)
     */
    public static String password(String user, String platformParameter) {
        String password;
        if(user.contains("Clinical")) {
            password = clinicalPassword(platformParameter);
        }else{
            password = participantPassword(platformParameter);
        }
        return password;
    }

    /**
     * Data Provider for First Name (Clinical/Participant)
     */
    public static String firstName(String user, String platformParameter) {
        String firstName;
        if(user.contains("Clinical")) {
            firstName = participantFirstName(platformParameter);
        }else{
            firstName = clinicalFirstName(platformParameter);
        }
        return firstName;
    }

    /**
     * Data Provider for Middle Name (Clinical/Participant)
     */
    public static String middleName(String user, String platformParameter) {
        String middleName;
        if(user.contains("Clinical")) {
            middleName = participantMiddleName(platformParameter);
        }else{
            middleName = clinicalMiddleName(platformParameter);
        }
        return middleName;
    }

    /**
     * Data Provider for Last Name (Clinical/Participant)
     */
    public static String lastName(String user, String platformParameter) {
        String lastName;
        if(user.contains("Clinical")) {
            lastName = participantLastName(platformParameter);
        }else{
            lastName = clinicalLastName(platformParameter);
        }
        return lastName;
    }



    /**
     * Data Provider for Test Case data and verifications
     */
    public static class tcData {

//        eConsent
//        SignIn Page

        public static String welcomeMsg = PropertyReader.TcDataReader.get("welcomeMsg");
        public static String errorMsg_wrongInput = PropertyReader.TcDataReader.get("errorMsg_wrongInput");
        public static String errorMsg_incorrectEmail = PropertyReader.TcDataReader.get("errorMsg_incorrectEmail");
        public static String errorMsg_incorrectPasswordFormat = PropertyReader.TcDataReader.get("errorMsg_incorrectPasswordFormat");


















    }

    /**
     * Data Provider for Environment Data
     */
    public static class environmentData {

        public static String DEV_TEAM = PropertyReader.EnvironmentReader.get("devTeam");
        public static String APP_NAME = PropertyReader.EnvironmentReader.get("appName");
        public static String GMAIL_URL = PropertyReader.EnvironmentReader.get("gmailUrl");
        public static String SIGN_IN_PAGE_LINK = PropertyReader.EnvironmentReader.get("signInPageLink");
        public static String PRIVACY_SETTINGS_PAGE_LINK = PropertyReader.EnvironmentReader.get("privacySettingsPageLink");
        public static String PROFILE_SETTINGS_PAGE_LINK = PropertyReader.EnvironmentReader.get("profileSettingsPageLink");



        public static String LOCAL_APPIUM_URL = PropertyReader.EnvironmentReader.get("localAppiumUrl");

        public static String appEnvironment() {
            String appEnvironment = null;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appEnvironment = Config.qa.App.Url;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_FATE)) {appEnvironment = Config.fate.App.Url;}
            else if (ENVIRONMENT.equals(ENV_VARIABLE_UAT)) {appEnvironment = Config.uat.App.Url;}
            return appEnvironment;
        }
        public static String appPackageCrealogixAndroid() {
            String appPackageCrealogixAndroid;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appPackageCrealogixAndroid =  Config.qa.Android.PackageId;}
            else {appPackageCrealogixAndroid =  Config.fate.Android.PackageId;}
            return appPackageCrealogixAndroid;}
        public static String appPackageCrealogixiOS() {
            String appPackageCrealogixiOS;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {appPackageCrealogixiOS =  Config.qa.iOS.PackageId;}
            else {appPackageCrealogixiOS =  Config.fate.iOS.PackageId;}
            return appPackageCrealogixiOS;}
        public static String androidEnvironment() {
            String androidEnvironment;
            if (ENVIRONMENT.equals(ENV_VARIABLE_QA)) {androidEnvironment = Config.qa.Android.PackageId;}
            else {androidEnvironment = Config.fate.Android.PackageId;}
            return androidEnvironment;}
        public static String ENV_VARIABLE_QA = PropertyReader.EnvironmentReader.get("envVariableQA");
        public static String ENV_VARIABLE_FATE = PropertyReader.EnvironmentReader.get("envVariableFATE");
        public static String ENV_VARIABLE_UAT = PropertyReader.EnvironmentReader.get("envVariableUAT");

        public static String MANUAL_SAUCELABS_DATA = PropertyReader.EnvironmentReader.get("manualSauceLabsData");

        public static String iOS_BUNDLE_ID = PropertyReader.EnvironmentReader.get("iOSBundleId");
        public static String ANDROID_BUNDLE_ID = PropertyReader.EnvironmentReader.get("androidBundleId");

    }

    /**
     * Data Provider for TestRail Data
     */
    public static class testRailData {
        public static String PROJECT_ID_TR = PropertyReader.testRailReader.get("projectIdTr");
        public static String SUITE_ID_TR = PropertyReader.testRailReader.get("suiteIdTr");
        public static String ASSIGNED_TO_ID_TR = PropertyReader.testRailReader.get("assignedTo_id");
        public static String REFS_TR = PropertyReader.testRailReader.get("refs");
        public static String INCLUDE_ALL_TR = PropertyReader.testRailReader.get("include_all");
        public static String ADD_RUN_URL_TR = PropertyReader.testRailReader.get("addRunUrlTr");
        public static String GET_TEST_RUN_URL_TR = PropertyReader.testRailReader.get("getTestRunUrlTr");
        public static String GET_TEST_RESULTS_URL_TR = PropertyReader.testRailReader.get("getTestResultsUrlTr");
        public static String GET_TEST_REPORT_URL_TR = PropertyReader.testRailReader.get("getTestReportUrlTr");
        public static String TEST_RAILI_REPORT_TEMPLATE_ID = PropertyReader.testRailReader.get("testRailReportTemplateId");
        public static String UPDATE_TEST_RUN_URL_TR = PropertyReader.testRailReader.get("updateTestRunUrlTr");
        public static String CLOSE_RUN_URL_TR = PropertyReader.testRailReader.get("closetRunUrlTr");
        public static String ADD_RESULT_URL_TR = PropertyReader.testRailReader.get("addResultUrlTr");
        public static String ADD_ATTACHMENT_TO_RESULT_URL_TR = PropertyReader.testRailReader.get("addAttachmentToResultUrlTr");
        public static String AUTHORIZATION_TR = PropertyReader.testRailReader.get("authorizationTr");
        public static String TOKEN_TR = PropertyReader.testRailReader.get("testRailToken");
        public static String USER_TR = PropertyReader.testRailReader.get("testRailUser");
        public static String PASS_TR = PropertyReader.testRailReader.get("testRailPass");


        public static String XRAY_USER = PropertyReader.xRayReader.get("xRay_user");
        public static String XRAY = PropertyReader.xRayReader.get("xRay");
        public static String XRAY_CLIENT_ID = jasyptDecodingString(DEV_TEAM, PropertyReader.xRayReader.get("client_id"));
        public static String XRAY_CLIENT_SECRET = jasyptDecodingString(DEV_TEAM, PropertyReader.xRayReader.get("client_secret"));
        public static String XRAY_AUTHENTICATE_URL = PropertyReader.xRayReader.get("xRay_authenticate_url");
        public static String XRAY_EXECUTION_URL = PropertyReader.xRayReader.get("xRay_execution_url");
        public static String TEST_RUN_TEST_ENVIRONMENTS_ANDROID = PropertyReader.xRayReader.get("testRun_testEnvironments_Android");
        public static String TEST_RUN_TEST_ENVIRONMENTS_IOS = PropertyReader.xRayReader.get("testRun_testEnvironments_iOS");
        public static String TEST_RUN_TEST_SUMMARY = PropertyReader.xRayReader.get("testRun_summary");
        public static String TEST_RUN_TEST_DESCRIPTION = PropertyReader.xRayReader.get("testRun_description");
        public static String TEST_PLAN_KEY = PropertyReader.xRayReader.get("testPlanKey");
        public static String TEST_KEY = PropertyReader.xRayReader.get("testKey");
    }

    public static class eConsentAPI_Data {
        public static String AUTH_SERVICE_KEY_ID = PropertyReader.eConsentAPI_Data.get("participant_auth_service_key_id");
        public static String AUTH_SERVICE_TOKEN = PropertyReader.eConsentAPI_Data.get("token");

    }

    /**
     * Data Provider for Case IDs for Android or iO
     */
    public static String case_ids(String platformParameter) {
        String case_idsData;
        if (platformParameter.equals("Android")) {
            case_idsData = PropertyReader.testRailReader.get("case_ids_Android");
        } else {
            case_idsData = PropertyReader.testRailReader.get("case_ids_iOS");
        }
        LoggerInformation("Case IDs are taken from " +platformParameter + " Test Run");
        return case_idsData;
    }

    /**
     * Data Provider for TestRail RunId data Android QA Env
     */
    public static class testrailRunIdDataAndroidQa {
        public static String XRAY_RUN_ID_ANDROID_QA = PropertyReader.xRayIdAndroidQa.get("xRayRunIdData");
    }

    /**
     * Data Provider for TestRail RunId data Android FATE Env
     */
    public static class testrailRunIdDataAndroidDev {
        public static String XRAY_RUN_ID_ANDROID_FATE = PropertyReader.xRayAndroidFate.get("xRayRunIdData");
    }

    /**
     * Data Provider for TestRail RunId data Android UAT Env
     */
    public static class testrailRunIdDataAndroidSit {
        public static String XRAY_RUN_ID_ANDROID_UAT = PropertyReader.xRayAndroidUat.get("xRayRunIdData");
    }

    /**
     * Data Provider for TestRail RunId data iOS QA Env
     */
    public static class testrailRunIdDataiOSqa {
        public static String XRAY_RUN_ID_IOS_QA = PropertyReader.xRayRunIdiOSQa.get("xRayRunIdData");
    }

    /**
     * Data Provider for TestRail RunId data iOS FATE Env
     */
    public static class testrailRunIdDataiOSdev {
        public static String XRAY_RUN_ID_IOS_FATE = PropertyReader.xRayRunIdiOSFate.get("xRayRunIdData");
    }

    /**
     * Data Provider for TestRail RunId data iOS Sit UAT
     */
    public static class testrailRunIdDataiOSSit {
        public static String XRAY_RUN_ID_IOS_UAT = PropertyReader.xRayRunIdiOSSit.get("xRayRunIdData");
    }
}
