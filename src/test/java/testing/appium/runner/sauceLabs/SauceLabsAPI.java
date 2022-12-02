package testing.appium.runner.sauceLabs;

import testing.appium.helpers.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.SessionId;
import testing.appium.runner.propertyFile.DataProvider;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.convertUnixTime;
import static java.nio.charset.StandardCharsets.US_ASCII;


public class SauceLabsAPI {

    public static String appVersionBuild;
    public static String slAppId;
    public static String entitiesVersion;

    /**
     * Sends status to SauceLabs test
     *
     * @param status "passed" / "failed"
     * @param driver Appium driver
     */
    public static void setSauceLabsTestStatus(String status, AppiumDriver<MobileElement> driver) {

        SessionId session = driver.getSessionId();
//        LoggerInformation("Appium Session Id: " + session);

        try {
            driver.executeScript("sauce:job-result=" + status);
//            LoggerInformation("SauceLabs Test Status: " + status);
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to set SauceLabs Test Status: ", ex.getMessage(), false);
        }
    }

    /**
     * Get Sauce Labs Test Run Details for Real Device
     */
    public static String getTestRunDetailsRD() {

        String entities;

        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
            HttpGet httpget = new HttpGet(DataProvider.slAuthorisationData.REAL_DEVICE_LINK + DataProvider.slAuthorisationData.REAL_DEVICE_LINK_LIMIT);
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Authorization", "Basic " + DataProvider.slAuthorisationData.SAUCELABS_TOKEN);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
//            LoggerInformation("entity: " + entity);

            InputStream inputStream = entity.getContent();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
            String entitiesFull = jsonObject.get("entities").toString();

            String statusString = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusString.contains("200")) {
                LoggerInformation("SauceLabs Run Details Status: " + statusString);
                if (entity != null) {
                    JSONArray arr = new JSONArray(entitiesFull);
                    org.json.JSONObject jObj = arr.getJSONObject(0);

                    String id = jObj.getString("id");
                    String name = jObj.getString("name");
                    String os = jObj.getString("os");
                    String os_version = jObj.getString("os_version");
                    String device_name = jObj.getString("device_name");
                    long start_time = jObj.getLong("start_time");

                    String entitiesCreation_time = convertUnixTime(start_time, "GMT+1", false);
                    String shareableLink = getShareableLink(id);

                    entities = " --- *Test Environment* ---  \\n"
                            + "Test Execution video: [" + shareableLink + "|" + shareableLink + "]" + "\\n"
                            + "Execution name: " + name + "\\n"
                            + "Device name: " + device_name + "\\n"
                            + "Platform: " + os + "\\n"
                            + "OS version: " + os_version;


                    return entities;
                } else {
                    LoggerInformation("SauceLabs Run Details Entity is Null" );
                    LoggerInformation("SauceLabs Run Details Entities: " + entitiesFull);
                }
            }else{
                LoggerInformation("SauceLabs Run Details Status: " + statusString);
                LoggerInformation("SauceLabs Run Details Entities: " + entitiesFull);
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get SauceLabs Run Details: ", ex.getMessage(), false);
        }
        return null;
    }

    /**
     * Get Sauce Labs Test Run Details for Virtual (Emulator/Simulator) Devices
     */
    public static String getTestRunDetailsVD() {

        String startTime = String.valueOf(Utils.getUnixTime() - 10000);
        String endTime = String.valueOf(Utils.getUnixTime());
        String entities = null;

        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
            HttpGet httpget = new HttpGet(DataProvider.slAuthorisationData.VIRTUAL_DEVICE_LINK + DataProvider.slAuthorisationData.USERNAME + "/jobs?from=" + startTime + "&to=" + endTime);
            LoggerInformation(DataProvider.slAuthorisationData.VIRTUAL_DEVICE_LINK + DataProvider.slAuthorisationData.USERNAME + "/jobs?from=" + startTime + "&to=" + endTime);
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Authorization", "Basic " + DataProvider.slAuthorisationData.SAUCELABS_TOKEN);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            String entitiesFull = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String statusString = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusString.contains("200")) {
                LoggerInformation("SauceLabs Run Details Status: " + statusString);
                if (entity != null) {
                    JSONArray arr = new JSONArray(entitiesFull);
                    org.json.JSONObject jObj = arr.getJSONObject(0);

//                    Get "id" form Response Body
                        String entitiesId = jObj.getString("id");
//                        LoggerInformation("entitiesId: " + entitiesId);

//                    Get "creation_time" form Response Body
                        long entitiesCreation_timeUnix = jObj.getLong("creation_time");
                        String entitiesCreation_time = convertUnixTime(entitiesCreation_timeUnix, "GMT+1", true);
//                        LoggerInformation("entitiesCreation_time: " + entitiesCreation_time);

//                    Get "name" form Response Body
                        String entitiesName = jObj.getJSONObject("base_config").getString("name");
//                        LoggerInformation("entitiesName: " + entitiesName);

//                    Get "os" form Response Body
                        String entitiesOS = jObj.getJSONObject("base_config").getString("platformName");
//                        LoggerInformation("entitiesOS: " + entitiesOS);

//                    Get "os version" form Response Body
                        String entitiesOSVersion = jObj.getJSONObject("base_config").getString("platformVersion");
//                        LoggerInformation("entitiesOSVersion: " + entitiesOSVersion);

//                    Get "deviceName" form Response Body
                        String entitiesDeviceName = jObj.getJSONObject("base_config").getString("deviceName");
//                        LoggerInformation("deviceName: " + entitiesDeviceName);

                        String shareableLink = getShareableLink(entitiesId);

                        entities = shareableLink + "\\n"
                                + "name: " + entitiesName + "\\n"
                                + "os: "  + entitiesOS + "\\n"
                                + "os version: " + entitiesOSVersion + "\\n"
                                + "device name: " + entitiesDeviceName;
//                        LoggerInformation("entities: " + entities);
                    return entities;
                } else {
                    LoggerInformation("SauceLabs Run Details Entity is Null" );
                    LoggerInformation("SauceLabs Run Details Entities: " + entitiesFull);
                }
            }else{
                LoggerInformation("SauceLabs Run Details Status: " + statusString);
                LoggerInformation("SauceLabs Run Details Entities: " + entitiesFull);
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Unable to get SauceLabs Run Details: ", ex.getMessage(), false);
        }
        return null;
    }

    public static void getAppStorageFile(String platformParameter, String testRailParameter){

        if (DataProvider.environmentData.MANUAL_SAUCELABS_DATA.equals("false")) {
            String statusString;
            int entitiesBuild;



            int arrayIndex = 0;
            String keyVersion;
            String keyBuild;

            if(testRailParameter.equals("Android")){
                keyBuild = "version_code";
                keyVersion = "version";
            }else{
                keyBuild = "version";
                keyVersion = "short_version";
                if (platformParameter.equals("remoteiOSCapsDefault")) {
                    arrayIndex = 1;
                }
            }

            try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
                HttpGet httpget = new HttpGet(DataProvider.slAuthorisationData.GET_APP_STORAGE_FILES + "?q=" + DataProvider.environmentData.APP_NAME + "&kind=" + testRailParameter);
//                LoggerInformation("SL API Get File URL:  " + GET_APP_STORAGE_FILES + "?q=" + APP_NAME + "." + ENVIRONMENT + "&kind=" + testRailParameter);

                httpget.setHeader("Accept", "application/json");
                httpget.setHeader("Authorization", "Basic " + DataProvider.slAuthorisationData.SAUCELABS_TOKEN);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                InputStream inputStream = entity.getContent();
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
                String entitiesFull = jsonObject.get("items").toString();
//                LoggerInformation("entitiesFull: , " + entitiesFull);

                int status = response.getStatusLine().getStatusCode();
                statusString = Integer.toString(status);

                if (statusString.contains("200")) {
                    LoggerInformation("App File ID, Name and Version will be taken from SauceLabs");
                    if (entity != null) {

                        JSONArray items = new JSONArray(entitiesFull);
                        org.json.JSONObject jObj = items.getJSONObject(arrayIndex);

//                        Get "id" form Response Body
                        slAppId = jObj.getString("id");
                        LoggerInformation("SauceLabs, " + DataProvider.environmentData.APP_NAME + " App File ID: " + slAppId);

                        org.json.JSONObject metadata = jObj.getJSONObject("metadata");
//                        Get "version" form Response Body
                        entitiesVersion = metadata.getString(keyVersion);
//                        LoggerInformation("entitiesVersion: " + entitiesVersion);

//                        Get "build" form Response Body
                        entitiesBuild = metadata.getInt(keyBuild);
//                        LoggerInformation("entitiesBuild: " + entitiesBuild);

                        appVersionBuild = DataProvider.environmentData.APP_NAME + " " + DataProvider.ENVIRONMENT + " - " + entitiesVersion + "-" + entitiesBuild;
                        LoggerInformation("SauceLabs, " + DataProvider.environmentData.APP_NAME + " App Name env - Version: " + appVersionBuild);
                    }
                }
            }catch (Exception ex){
                LoggerStep_Failed("Unable to get, " + DataProvider.environmentData.APP_NAME + "  App File ID: ", ex.getMessage(), true);
            }
        } else {
            LoggerInformation("App File ID, Name and Version will be taken from settings.json");
        }

    }

    /**
     * Create Sauce Labs Run Shareable Link
     *
     * @param sauceJobId - Sauce Labs Run ID
     */
    public static String getShareableLink(String sauceJobId) throws NoSuchAlgorithmException, InvalidKeyException {

        final String key = String.format("%s:%s", DataProvider.slAuthorisationData.USERNAME, DataProvider.slAuthorisationData.ACCESS_KEY);
        final String sauceTestsUrl = DataProvider.slAuthorisationData.SL_TEST_RESULT_LINK;

        SecretKeySpec sks = new SecretKeySpec(key.getBytes(US_ASCII), "HmacMD5");
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(sks);
        byte[] result = mac.doFinal(sauceJobId.getBytes(US_ASCII));
        StringBuilder hash = new StringBuilder();
        for (byte b : result) {
              String hex = Integer.toHexString(0xFF & b);
               if (hex.length() == 1) {
                    hash.append('0');
               }
               hash.append(hex);
        }
        String digest = hash.toString();
        return String.format("%s/%s?auth=%s", sauceTestsUrl, sauceJobId, digest);
        }

}