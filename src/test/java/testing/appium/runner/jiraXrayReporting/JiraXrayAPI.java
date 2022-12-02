package testing.appium.runner.jiraXrayReporting;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.ITestResult;
import testing.appium.helpers.Utils;

import static testing.appium.helpers.TCLogger.LoggerInformation;
import static testing.appium.helpers.TCLogger.LoggerStep_Failed;
import static testing.appium.helpers.Utils.*;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.*;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.ENV_VARIABLE_UAT;
import static testing.appium.runner.propertyFile.DataProvider.testRailData.*;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidDev.XRAY_RUN_ID_ANDROID_FATE;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidQa.XRAY_RUN_ID_ANDROID_QA;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidSit.XRAY_RUN_ID_ANDROID_UAT;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSSit.XRAY_RUN_ID_IOS_UAT;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSdev.XRAY_RUN_ID_IOS_FATE;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSqa.XRAY_RUN_ID_IOS_QA;

public class JiraXrayAPI {
    static private String xRayRunId;
    static private final String env = ENVIRONMENT;
    public static String getXRayRunId(String platformParameter) {
        switch (platformParameter) {
            case "Android":
                if (env.equals(ENV_VARIABLE_QA)) {
                    xRayRunId = XRAY_RUN_ID_ANDROID_QA;
                } else if (env.equals(ENV_VARIABLE_FATE)) {
                    xRayRunId = XRAY_RUN_ID_ANDROID_FATE;
                } else if (env.equals(ENV_VARIABLE_UAT)) {
                    xRayRunId = XRAY_RUN_ID_ANDROID_UAT;
                }
                break;
            case "iOS":
                if (env.equals(ENV_VARIABLE_QA)) {
                    xRayRunId = XRAY_RUN_ID_IOS_QA;
                } else if (env.equals(ENV_VARIABLE_FATE)) {
                    xRayRunId = XRAY_RUN_ID_IOS_FATE;
                } else if (env.equals(ENV_VARIABLE_UAT)) {
                    xRayRunId = XRAY_RUN_ID_IOS_UAT;
                }
                break;
        }
//        LoggerInformation("Xray Run ID: " + xRayRunId);
        return xRayRunId;
    }

    public static void xRayAPI() throws IOException {

        String evidencePath = "files/CLX_photo.jpg";
        String[] evidences = evidencePath.split(",");
        JsonArray evidencesJson = new JsonArray();

        for (String s : evidences) {
            File evidenceFile = new File(s);
            byte[] filecontent = FileUtils.readFileToByteArray(evidenceFile);
            String encoded_FileContent = Base64.getEncoder().encodeToString(filecontent);
            JsonObject evidence = new JsonObject();
            evidence.addProperty("data", encoded_FileContent);
            evidence.addProperty("filename", evidenceFile.getName());
            evidence.addProperty("contentType", "application/html");
            evidencesJson.add(evidence);
        }

        JsonObject json = new JsonObject();
        json.addProperty("testExecutionKey", "EC-4146");

        JsonArray testArray = new JsonArray();
        JsonObject test = new JsonObject();
        test.addProperty("testKey", "EC-941");
        test.addProperty("start", "2020-04-30T11:47:35+01:00");
        test.addProperty("finish", "2020-04-30T11:47:35+01:00");
        test.addProperty("comment", "This is just a trial run");
        test.addProperty("status", "PASS");
        test.add("evidences", evidencesJson);
        testArray.add(test);
        json.add("test", testArray);

        HttpsURLConnection con = null;
        InputStreamReader inputStream = null;
        try {
            URL jira_API_URL = new URL("https://florencehc.atlassian.net/rest/api/3/import/execution");
            String encodeCredentials = Base64.getEncoder().encodeToString("mobile.automation@florencehc.com:Password123*".getBytes("UTF-8"));
            con = (HttpsURLConnection) jira_API_URL.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Autherization", "Basic " + encodeCredentials);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("X-Atlassian-Token", "nocheck");
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            inputStream = new InputStreamReader(con.getInputStream(), "UTF-8");
            try (BufferedReader br = new BufferedReader(inputStream)) {
                StringBuilder response = new StringBuilder();
                String responseline = null;
                while ((responseline = br.readLine()) != null) {
                    response.append(responseline.trim());
                }
            }
            if (con.getResponseCode() == 200) {
                LoggerInformation("Response code is: " + con.getResponseCode());
            } else {
                LoggerInformation("Response code is: " + con.getResponseCode());
                Assert.fail();
            }
        } catch (Exception ex) {
            LoggerStep_Failed("API Xray: ", ex.getMessage(), true);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    LoggerStep_Failed("API Xray: ", ex.getMessage(), true);
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static String getXrayToken() {

        String authToken = null;
        final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

        try {
            OkHttpClient client = new OkHttpClient();
            String authenticationPayload = "{ \"client_id\": \"" + XRAY_CLIENT_ID + "\", \"client_secret\": \"" + XRAY_CLIENT_SECRET + "\" }";

            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, authenticationPayload);
            Request request = new Request.Builder().url(XRAY_AUTHENTICATE_URL).post(body).build();
            Response response;
            response = client.newCall(request).execute();
            String statusString = String.valueOf(response.code());

            if (statusString.equals("200")) {
                LoggerInformation("Xray Authentication Token API status: " + statusString);
                String responseBody = response.body().string();
                authToken = responseBody.replace("\"", "");
//                LoggerInformation("xrayToken: " + authToken);
                return authToken;
            } else {
                throw new IOException("failed to authenticate " + response);
            }
        } catch (Exception ex) {
            LoggerStep_Failed("Xray Authentication Token Field: ", ex.getMessage(), true);
        }
        return null;
    }

    public static void setTcRun_Xray(boolean crete_Xray_Test_Run, String platformParameter) {

        if (XRAY.equals("true") & crete_Xray_Test_Run) {
            String testRun_summary = TEST_RUN_TEST_SUMMARY;
            String testRun_description = TEST_RUN_TEST_DESCRIPTION;
            String testRun_user = XRAY_USER;
            String testRun_testPlanKey = TEST_PLAN_KEY;
            String testRun_testEnvironments;
            String testRun_testKey = TEST_KEY;
            String testRun_status ="EXECUTING";
            String testRun_key = null;
            String id;
            String self;
            testRun_testEnvironments = ENVIRONMENT;;


            String authToken = getXrayToken();

            try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost httpPost;
                httpPost = new HttpPost(XRAY_EXECUTION_URL);
//                String resultPayload = "{\"info\":{\"summary\":\"" + testRun_summary + "\",\"description\":\"" + testRun_description + "\",\"user\":\"" + testRun_user + "\",\"testPlanKey\":\"" + testRun_testPlanKey + "\",\"testEnvironments\":[\"" + testRun_testEnvironments + "\"]},\"tests\":[{\"testKey\":\"" + testRun_testKey +"\",\"status\":\"" + testRun_status +"\"}]}";
                String resultPayload = "{\"info\":{\"summary\":\"" + testRun_summary + "\",\"description\":\"" + testRun_description + "\",\"user\":\"" + testRun_user + "\",\"testPlanKey\":\"" +  testRun_testPlanKey + "\"},\"tests\":[{\"testKey\":\"" + testRun_testKey + "\",\"status\":\"EXECUTING\",\"comment\":\"" + testRun_status + "\"}]}";
                LoggerInformation("resultPayload: " + resultPayload);
                StringEntity requestEntity = new StringEntity(resultPayload);
                httpPost.setEntity(requestEntity);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + authToken);
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                String statusString = Integer.toString(status);

                HttpEntity responseEntity = response.getEntity();
                if (statusString.equals("200")) {
                    LoggerInformation("Xray Create Test Run API status: " + statusString);
                    InputStream inputStream = responseEntity.getContent();
                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    id = jsonObject.get("id").toString();
                    testRun_key = jsonObject.get("key").toString();
                    self = jsonObject.get("self").toString();
    //                LoggerInformation("Test Case Result ID: " + id);
                    LoggerInformation("Test Case Result key: " + testRun_key);
    //                LoggerInformation("Test Case Result self: " + self);
                } else {
                    LoggerInformation("XRay Create Test Run  status: " + statusString);
                }
            } catch (Exception ex) {
                LoggerStep_Failed("Create Test Run Field: ", ex.getMessage(), true);
            }
            if(testRun_key != null) {
                String projectPath = System.getProperty("user.dir");
                String path = projectPath + "/src/main/resources/" + platformParameter + "_" + env + "_xRayRunId.properties";
                try {
                    //Instantiating the properties file
                    Properties props = new Properties();
                    //Populating the properties file
                    props.put("xRayRunIdData", testRun_key);
                    //Instantiating the FileInputStream for output file
                    FileOutputStream outputStrem = new FileOutputStream(path);
                    //Storing the properties file
                    props.store(outputStrem, "This is " + platformParameter + "_" + env + "_xRayRunId.properties file");
                    LoggerInformation("Properties file created: " + path);
                } catch (Exception ex) {
                    LoggerInformation("Properties file: " + path + " -Not created");
                }
            }else{
                LoggerInformation("Test Key is Not Created");
            }
        } else {
            LoggerInformation("This Test Run does Not imply the Creation of a Xray Test Report");
        }
    }

    public static void setTcResult_Xray(ITestResult result, String platformParameter, String nameTC, String testRunId, String testKey, String testStatus, String getReport, boolean ticketAndroid, boolean ticketiOS, String bugTicketNo) {

        if (XRAY.equals("true")) {
            String testStatus_data;
            if(testStatus.equals("FAILED") && (ticketAndroid || ticketiOS)){
                testStatus_data = "KNOWN_ISSUE";
            }else{
                testStatus_data = testStatus;

            }

            String screenshotPath = null;
            String encoded_FileContent = null;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long ts = timestamp.getTime();
            String fileName = "screenshot_" + ts + "_" + testKey + ".jpg";
            if(testStatus.equals("FAILED") || testStatus.equals("KNOWN_ISSUE") ){
                screenshotPath = getScreenshotPath((AppiumDriver<MobileElement>) result.getAttribute("driver"), testKey, platformParameter);
                encoded_FileContent = encodeFile(screenshotPath);
            }

            String authToken = getXrayToken();

            try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost httpPost;
                httpPost = new HttpPost(XRAY_EXECUTION_URL);
                String resultPayload = configureJson(nameTC, testStatus_data, testRunId, testKey, getReport, platformParameter, ticketAndroid, ticketiOS , bugTicketNo, encoded_FileContent, fileName);
//                LoggerInformation("resultPayload: " + resultPayload);
                StringEntity requestEntity = new StringEntity(resultPayload);
                httpPost.setEntity(requestEntity);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + authToken);
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                String statusString = Integer.toString(status);

                HttpEntity responseEntity = response.getEntity();
                InputStream inputStream = responseEntity.getContent();
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                if (statusString.equals("200")) {
                    LoggerInformation("Xray Set Test Result API status: " + statusString);

                    String id = jsonObject.get("id").toString();
                    String key = jsonObject.get("key").toString();
                    String self = jsonObject.get("self").toString();
//                    LoggerInformation("Test Case Result ID: " + id);
    //                LoggerInformation("Test Case Result key: " + key);
    //                LoggerInformation("Test Case Result self: " + self);
                    if (testStatus.equals("FAILED") ||testStatus.equals("KNOWN_ISSUE")) {
                        Utils.deleteFile(screenshotPath);
                    }
                } else {
                    LoggerInformation("XRay set Test Result status: " + statusString);
                    LoggerInformation("eConsent POST API Response Body: " + jsonObject);
                }
            } catch (Exception ex) {
                LoggerStep_Failed("Add New Test Result Field: ", ex.getMessage(), true);
            }
        }
    }

    private static String configureJson(String nameTC, String testStatus, String testRunId,  String testKey, String comment, String platformParameter, Boolean ticketAndroid, Boolean ticketiOS, String ticketNo, String encodedFile, String fileName) {

//        String time = cutString(String.valueOf(java.time.LocalDateTime.now()), "\\.", 1) + "+1:00";
        String time = "";
        String json = null;
        String testCaseLogStart = "*Test Case Execution Log*\\n=====\\n\\n --- Test Case - " + nameTC + " -- *Start* ---\\n";
        String testCaseLogEnd = "\\n\\n=====\\n\\n";
        String testCaseLogEndScreenshot = "Failed Test Step Screenshot";

        String json_Passed_No_BugID = "{\"testExecutionKey\":\"" + testRunId + "\",\"tests\":[{\"testKey\":\"" + testKey + "\",\"status\":\"" + testStatus + "\",\"comment\":\"" + testCaseLogStart + comment + testCaseLogEnd + "\"}]}";
        String jsonFailed_No_BugID_screenshot = "{\"testExecutionKey\":\"" + testRunId + "\",\"tests\":[{\"testKey\":\"" + testKey + "\",\"status\":\"" + testStatus + "\",\"comment\":\"" + testCaseLogStart + comment + testCaseLogEnd + "\",\"evidence\":[{\"data\":\"" + encodedFile + "\",\"filename\":\"" + fileName + "\",\"contentType\":\"image/jpeg\"}]}]}";
        String jsonFailed_BugID_screenshot = "{\"testExecutionKey\":\"" + testRunId + "\",\"tests\":[{\"testKey\":\"" + testKey + "\",\"status\":\"" + testStatus+ "\",\"comment\":\"" + testCaseLogStart+ comment + testCaseLogEnd + "\",\"defects\":[\"" + ticketNo + "\"],\"evidence\":[{\"data\":\"" + encodedFile + "\",\"filename\":\"" + fileName + "\",\"contentType\":\"image/jpeg\"}]}]}";
        String jsonExecuting = "{\"testExecutionKey\":\"" + testRunId + "\",\"tests\":[{\"testKey\":\"" + testKey + "\",\"start\":\"" + time + "\",\"status\":\"" + testStatus + "\",\"comment\":\"The test is currently running\"}]}";

        switch (platformParameter) {
            case "Android":
                if (testStatus.equals("FAILED") || testStatus.equals("KNOWN_ISSUE")){
                    if (ticketAndroid) {
                        json = jsonFailed_BugID_screenshot;
                    } else{
                        json = jsonFailed_No_BugID_screenshot;
                    }
                }else if(testStatus.equals("EXECUTING")){
                    json = jsonExecuting;
                }else{
                    json = json_Passed_No_BugID;
                }
                break;
            case "iOS":
                if (testStatus.equals("FAILED") || testStatus.equals("KNOWN_ISSUE")) {
                    if (ticketiOS) {
                        json = jsonFailed_BugID_screenshot;
                    } else {
                        json = jsonFailed_No_BugID_screenshot;
                    }
                }else if(testStatus.equals("EXECUTING")){
                    json = jsonExecuting;
                }else{
                    json = json_Passed_No_BugID;
                }
                break;
        }
        return json;
    }


}

