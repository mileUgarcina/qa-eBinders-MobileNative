package testing.appium.runner.testRailReporting;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import testing.appium.helpers.Utils;
import testing.appium.runner.propertyFile.DataProvider;
import testing.appium.helpers.TCLogger;
import testing.appium.runner.sauceLabs.SauceLabsAPI;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.ITestResult;

import static testing.appium.helpers.TCLogger.*;
import static testing.appium.helpers.Utils.getScreenshotPath;
import static testing.appium.runner.propertyFile.DataProvider.SlAndroidConfigData.nameSlAndroid;
import static testing.appium.runner.propertyFile.DataProvider.SliOSConfigData.nameSliOS;
import static testing.appium.runner.propertyFile.DataProvider.environmentData.*;
import static testing.appium.runner.propertyFile.DataProvider.slAuthorisationData.*;
import static testing.appium.runner.propertyFile.DataProvider.testRailData.*;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidDev.XRAY_RUN_ID_ANDROID_FATE;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidQa.XRAY_RUN_ID_ANDROID_QA;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataAndroidSit.XRAY_RUN_ID_ANDROID_UAT;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSSit.XRAY_RUN_ID_IOS_UAT;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSdev.XRAY_RUN_ID_IOS_FATE;
import static testing.appium.runner.propertyFile.DataProvider.testrailRunIdDataiOSqa.XRAY_RUN_ID_IOS_QA;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.getTestRunDetailsVD;


public class TestRailAPI {

  static private String xRayRunId;
  static private final String env = DataProvider.ENVIRONMENT;
  static private final String testRailAuthorizationToken = getAuthorization(USER_TR, PASS_TR);

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
    return xRayRunId;
  }

  private static String getAuthorization(String user, String password) {
    String token;
    try {
      token = new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
      return token;
    } catch (IllegalArgumentException ex) {
      TCLogger.LoggerStep_Failed("Unable to Create Authorization Token: ", ex.getMessage(), false);
    }
    return null;
  }

  public static void configTestRailReporting(AppiumDriver<MobileElement> driver, String testRailParameter, String testRailRun, String platformParameter, String suiteName) {

    switch (testRailRun) {
      case "Create":
        createTestRailRun(driver, testRailParameter, platformParameter, suiteName);
        break;
      case "Update":
        updateTestrailDescription(platformParameter, testRailParameter, suiteName);
        break;
      case "No":
        LoggerInformation("This Test Run does Not imply the Creation of a Test Rail Report");
        break;
    }
  }

  private static void createTestRailRun(AppiumDriver<MobileElement> driver, String testRailParameter, String platformParameter, String suiteName) {

    Map<String, Object> caps = driver.getSessionDetails();
    Date date = java.util.Calendar.getInstance().getTime();

    String name;
    if (testRailParameter.contains("Android")) {
      name = nameSlAndroid();
    } else {
      name = nameSliOS();
    }

    String description;
    String descriptionTRRun1 = null;
    if (platformParameter.contains("remoteAndroidCapsDefault") || platformParameter.contains("remoteiOSCapsDefault")) {
      descriptionTRRun1 = SauceLabsAPI.getTestRunDetailsRD();
    } else if (platformParameter.contains("remoteAndroidCapsEmulator") || platformParameter.contains("remoteiOSCapsSimulator")) {
      descriptionTRRun1 = getTestRunDetailsVD();
    }
    description = "***" + suiteName + "***" + "\\nSauceLabs Test Video: " + descriptionTRRun1 + "\\nPowered by: " + POWERED_BY;

    String testRunName = DEV_TEAM + " Product || Appium Test Automation || " + caps.get("platformName") + " " + caps.get("platformVersion") + " || " + name + " || " + date;
    String projectId = System.getenv("projectId") != null ? System.getenv("projectId") : PROJECT_ID_TR;
    String testSuiteId = System.getenv("suiteId") != null ? System.getenv("suiteId") : SUITE_ID_TR;
    xRayRunId = TestRailAPI.addNewTestRailRun(projectId, testSuiteId, testRunName, testRailParameter, description);
    LoggerInformation("Added new TestRail test run Name: " + testRunName);
    LoggerInformation("Added new TestRail test run Project ID: " + projectId);
    LoggerInformation("Added new TestRail test run Suite ID: " + testSuiteId);
    LoggerInformation("Added new TestRail test run ID: " + xRayRunId);
  }

  private static String addNewTestRailRun(String projectId, String suiteId, String runName, String testRailParameter, String description) {

    HttpClient httpClient = HttpClientBuilder.create().build();
    String runNumber = "";
    String addRunUrl = ADD_RUN_URL_TR;
    String case_ids = DataProvider.case_ids(testRailParameter);

    try {
      HttpPost httpPost = new HttpPost(
              addRunUrl + projectId);

      String json = "{\n" +
              "\t\"suite_id\": " + suiteId + ",\n" +
              "\t\"name\": \" " + runName + "\",\n" +
              "\t\"assignedto_id\": " + ASSIGNED_TO_ID_TR + " ,\n" +
              "\t\"description\": \" " + description + "\",\n" +
              "\t\"include_all\": " + INCLUDE_ALL_TR + " ,\n" +
              "\t\"case_ids\": " + case_ids + "\n" +
              "}";

      StringEntity requestEntity = new StringEntity(json);
      httpPost.setEntity(requestEntity);
      httpPost.setHeader("Content-type", "application/json");
      httpPost.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpClient.execute(httpPost);

      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        InputStream inputStream = responseEntity.getContent();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
        runNumber = jsonObject.get("id").toString();
        String runId = runNumber;
      }
    } catch (Exception ex) {
      TCLogger.LoggerStep_Failed("Add New Test Rail Run Field: ", ex.getMessage(), true);
    }
//    Putting a test run ID in a properties file
//    String path = "src/main/resources/" + testRailParameter + "_" + env + "_testRailRunId.properties";

    String projectPath = System.getProperty("user.dir");
    String path = projectPath + "/src/main/resources/" + testRailParameter + "_" + env + "_testRailRunId.properties";

    try {
      //Instantiating the properties file
      Properties props = new Properties();
      //Populating the properties file
      props.put("xRayRunIdData", runNumber);
      //Instantiating the FileInputStream for output file
      FileOutputStream outputStrem = new FileOutputStream(path);
      //Storing the properties file
      props.store(outputStrem, "This is " + testRailParameter + "_" + env + "_testRailRunId.properties file");
      LoggerInformation("Properties file created: " + path);
    } catch (Exception ex) {
      LoggerInformation("Properties file: " + path + " -Not created");
    }
    return runNumber;
  }

  private static String getTestRailRun(String testRailParameter) {

    String getTestRunUrl = GET_TEST_RUN_URL_TR;
    String descriptionRun1;
    String testrailRunIdData = getXRayRunId(testRailParameter);

    try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
      HttpGet httpGet;

      httpGet = new HttpGet(getTestRunUrl + testrailRunIdData);
      httpGet.setHeader("Content-Type", "application/json");
      httpGet.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpclient.execute(httpGet);
      HttpEntity entity = response.getEntity();

      int status = response.getStatusLine().getStatusCode();
      String statusString = Integer.toString(status);

      if (statusString.contains("200")) {
        LoggerInformation("Test Rail Get Run Status: " + statusString);
        if (entity != null) {
          HttpEntity responseEntity = response.getEntity();
          InputStream inputStream = responseEntity.getContent();
          JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
          descriptionRun1 = jsonObject.get("description").toString().replaceAll("\n", "\\\\n");
          return descriptionRun1;
        } else {
          LoggerInformation("Test Rail Get Run Status: " + statusString);
        }
      }
    } catch (Exception ex) {
      LoggerInformation("Unable to get Test Rail Get Run Description: " + ex.getMessage());
    }
    return null;
  }

  private static String getTestRailResults(String testResultId) {

    String resultId;

    try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
      HttpGet httpGet;
      httpGet = new HttpGet(GET_TEST_RESULTS_URL_TR + testResultId);
      httpGet.setHeader("Content-Type", "application/json");
      httpGet.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpclient.execute(httpGet);
      HttpEntity entity = response.getEntity();

      int status = response.getStatusLine().getStatusCode();
      String statusString = Integer.toString(status);

      if (statusString.equals("200")) {
        if (entity != null) {

          InputStream inputStream = entity.getContent();
          JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
          String entitiesResults = jsonObject.get("results").toString();
          JSONArray arr = new JSONArray(entitiesResults);
          org.json.JSONObject jObj = arr.getJSONObject(0);

          resultId = String.valueOf(jObj.getLong("id"));
          LoggerInformation("Added new TestRail test result Run Result ID: " + resultId);
          return resultId;
        } else {
          LoggerInformation("Test Rail Get Run Status: " + statusString);
        }
      }
    } catch (Exception ex) {
      TCLogger.LoggerStep_Failed("Unable to get Test Rail Get Run Result ID: ", ex.getMessage(), false);
    }
    return null;
  }

  private static void updateTestRailRun(String testRailParameter, String description, String sauceLabsRunDetails) {

    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
    String updateTestRunUrl = UPDATE_TEST_RUN_URL_TR;
    String descriptionFull = description + "\\n\\n" + sauceLabsRunDetails;
    String testrailRunIdData = getXRayRunId(testRailParameter);

    try {
      HttpPost httpPost;
      httpPost = new HttpPost(updateTestRunUrl + testrailRunIdData);
      String json = "{\n" +
              "\t\"description\": \"" + descriptionFull + "\"\n" +
              "}";

      StringEntity requestEntity = new StringEntity(json);
      httpPost.setEntity(requestEntity);
      httpPost.setHeader("Content-type", "application/json");
      httpPost.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpclient.execute(httpPost);
      int status = response.getStatusLine().getStatusCode();
      String statusString = Integer.toString(status);
      LoggerInformation("Test Rail Update Run Status: " + statusString);
    } catch (Exception ex) {
      TCLogger.LoggerStep_Failed("Unable to Update Test Rail Run : ", ex.getMessage(), false);
    }
  }

  /**
   * Add Test Rail Result
   * Number is numeric code for TestRail
   * 1	Passed
   * 2	Blocked
   * 3	Untested
   * 4	Retest
   * 5	Failed
   * 9   Skipped
   *
   * @param testCaseId
   * @param resultNumber
   */
  public static void addTcResult(ITestResult result, String nameTC, String testCaseId, String resultNumber, String getReport, String testRailParameter, Boolean ticketAndroid, Boolean ticketiOS, String ticketNo) {

    HttpClient httpClient = HttpClientBuilder.create().build();
    String addResultUrl = ADD_RESULT_URL_TR;
    String testrailRunIdData = getXRayRunId(testRailParameter);
    String testResultId;

    try {
      HttpPost httpPost;
      httpPost = new HttpPost(addResultUrl + testrailRunIdData + "/" + testCaseId);
      String json = configureJson(nameTC, resultNumber, getReport, testRailParameter, ticketAndroid, ticketiOS, ticketNo);
      StringEntity requestEntity = new StringEntity(json);
      httpPost.setEntity(requestEntity);
      httpPost.setHeader("Content-type", "application/json");
      httpPost.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity responseEntity = response.getEntity();

      if (responseEntity != null) {
        LoggerInformation("Added new TestRail test result Parameter: " + testRailParameter);
        LoggerInformation("Added new TestRail test Run Id: " + testrailRunIdData);
        LoggerInformation("Added new TestRail test result TestCase Id: " + testCaseId);
        LoggerInformation("Added new TestRail test result Result Number: " + resultNumber);
      }

      if (resultNumber.equals("5")) {
        InputStream inputStream = responseEntity.getContent();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
        testResultId = jsonObject.get("test_id").toString();
        LoggerInformation("Added new TestRail test result Test Result Test ID: " + testResultId);
        String result_id = getTestRailResults(testResultId);
        String screenshotPath = getScreenshotPath((AppiumDriver<MobileElement>) result.getAttribute("driver"), testCaseId, testRailParameter);
        addAttachmentToResult(result_id, screenshotPath);
        Utils.deleteFile(screenshotPath);
      }
    } catch (Exception ex) {
      TCLogger.LoggerStep_Failed("Add TestRail Test Result Fail: ", ex.getMessage(), true);
    }
  }

  public static void addTcResultManual(String testCaseId, String resultNumber, String testRailParameter, String comment) {

    String addResultUrl = ADD_RESULT_URL_TR;
    String testrailRunIdData = getXRayRunId(testRailParameter);
    HttpClient httpClient = HttpClientBuilder.create().build();

    try {
      HttpPost httpPost ;

      httpPost = new HttpPost(addResultUrl + testrailRunIdData + "/" + testCaseId);
      String json = "{\"status_id\": " + resultNumber + ",\"comment\": \"" + comment + "\"}";

      StringEntity requestEntity = new StringEntity(json);
      httpPost.setEntity(requestEntity);
      httpPost.setHeader("Content-type", "application/json");
      httpPost.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
      HttpResponse response = httpClient.execute(httpPost);

      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        InputStream inputStream = responseEntity.getContent();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
        LoggerInformation("Added new TestRail test result for TC " + testCaseId + " - Not Applicable");
      }
    } catch (Exception ex) {
      TCLogger.LoggerStep_Failed("Add Tc Result Manual Fail: ", ex.getMessage(), true);
    }
  }

  private static void updateTestrailDescription(String platformParameter, String testRailParameter, String suiteName) {

    String sauceLabsRunDetails;
    String descriptionTRRun1 = null;
    if (platformParameter.contains("remoteAndroidCapsDefault") || platformParameter.contains("remoteiOSCapsDefault")) {
      descriptionTRRun1 = SauceLabsAPI.getTestRunDetailsRD();
    } else if (platformParameter.contains("remoteAndroidCapsEmulator") || platformParameter.contains("remoteiOSCapsSimulator")) {
      descriptionTRRun1 = getTestRunDetailsVD();
    }
    sauceLabsRunDetails = "***" + suiteName + "***" + "\\nTest Execution Video: " + descriptionTRRun1 + "\\nPowered by: " + POWERED_BY;
    String descriptionTR1 = getTestRailRun(testRailParameter);
    updateTestRailRun(testRailParameter, descriptionTR1, sauceLabsRunDetails);


  }

  private static void addAttachmentToResult(String result_id, String screenshotPath) {

    try {
      URL url = new URL(ADD_ATTACHMENT_TO_RESULT_URL_TR + result_id);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.addRequestProperty("Authorization", "Basic " + testRailAuthorizationToken);
      conn.setRequestMethod("POST");

      if (screenshotPath != null) {
        String boundary = "TestRailAPIAttachmentBoundary"; //Can be any random string
        File uploadFile = new File(screenshotPath);

        conn.setDoOutput(true);
        conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream ostreamBody = conn.getOutputStream();
        BufferedWriter bodyWriter = new BufferedWriter(new OutputStreamWriter(ostreamBody));

        bodyWriter.write("\n\n--" + boundary + "\r\n");
        bodyWriter.write("Content-Disposition: form-data; name=\"attachment\"; filename=\"" + uploadFile + "\"");
        bodyWriter.write("\r\n\r\n");
        bodyWriter.flush();

        //Read file into request
        InputStream istreamFile = new FileInputStream(uploadFile);
        int bytesRead;
        byte[] dataBuffer = new byte[1024];
        while ((bytesRead = istreamFile.read(dataBuffer)) != -1) {
          ostreamBody.write(dataBuffer, 0, bytesRead);
        }

        ostreamBody.flush();

        //end of attachment, add boundary
        bodyWriter.write("\r\n--" + boundary + "--\r\n");
        bodyWriter.flush();

        BufferedReader br;
        if (conn.getResponseCode() == 200) {
          br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
          br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        while ((br.readLine()) != null) {
          LoggerInformation("Screen shot added to fail Test Case");
        }

        //Close streams
        istreamFile.close();
        ostreamBody.close();
        bodyWriter.close();
      }
    } catch (IOException ex) {
      TCLogger.LoggerStep_Failed("Add Attachment To Result Fail: ", ex.getMessage(), false);
    }
  }

  private static String configureJson(String nameTC, String resultNumber, String getReport, String testRailParameter, Boolean ticketAndroid, Boolean ticketiOS, String ticketNo) {

    String json = null;
    String testCaseLogStart = "=====\\n\\n --- Test Case - " + nameTC + " -- Start ---\\n";
    String testCaseLogEnd = "\\n\\n=====\\n\\n";
    String testCaseLogEndScreenshot = "Failed Test Step Screenshot";
    String jsonFailedDefectScreenshot = "{\"status_id\": " + resultNumber + ",\"comment\": \"" + testCaseLogStart + getReport + testCaseLogEnd + testCaseLogEndScreenshot + "\",\"defects\": \"" + ticketNo + "\"}";
    String jsonFailedScreenshot = "{\"status_id\": " + resultNumber + ",\"comment\": \"" + testCaseLogStart + getReport + testCaseLogEnd + testCaseLogEndScreenshot + "\"}";
    String jsonPassed = "{\"status_id\": " + resultNumber + ",\"comment\": \"" + testCaseLogStart + getReport + testCaseLogEnd + "\"}";

    switch (testRailParameter) {
      case "Android":
        if (resultNumber.equals("5")) {
          if (ticketAndroid) {
            json = jsonFailedDefectScreenshot;
          } else {
            json = jsonFailedScreenshot;
          }
          return json;
        } else {
          json = jsonPassed;
        }
        break;
      case "iOS":
        if (resultNumber.equals("5")) {
          if (ticketiOS) {
            json = jsonFailedDefectScreenshot;
          } else {
            json = jsonFailedScreenshot;
          }
          return json;
        } else {
          json = jsonPassed;
        }
        break;
    }
    return json;
  }

  public static void closeTestRailRun(String testRailRunCompleted, String testRailParameter) {

    if (testRailRunCompleted.equals("Yes")) {
      try {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost;
        String testrailRunIdData = getXRayRunId(testRailParameter);
        httpPost = new HttpPost(CLOSE_RUN_URL_TR + testrailRunIdData);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
          InputStream inputStream = responseEntity.getContent();
          JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
          LoggerInformation("TestRail Run " + testrailRunIdData + " Closed");
        }
      } catch (Exception ex) {
        LoggerStep_Failed("TestRail Run Close Fail: ", ex.getMessage(), true);
      }
    }
    else {
      LoggerInformation("TestRail Run Not Closed");
    }
  }

  public static void createTestRailReport(String testRailRunCompleted) {

    if(testRailRunCompleted.equals("Yes")) {
      try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
        HttpGet httpGet;
        httpGet = new HttpGet(GET_TEST_REPORT_URL_TR + TEST_RAILI_REPORT_TEMPLATE_ID);
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", "Basic " + testRailAuthorizationToken);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        InputStream inputStream = entity.getContent();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
        int status = response.getStatusLine().getStatusCode();
        String statusString = Integer.toString(status);

        if (statusString.equals("200")) {
          LoggerInformation("TestRail Create Test Report Status: " + statusString);
          if (entity != null) {
            String report_url = jsonObject.get("report_url").toString();
            LoggerInformation("TestRail Report URL: " + report_url);
            String report_html = jsonObject.get("report_html").toString();
            LoggerInformation("TestRail Report HTML: " + report_html);
            String report_pdf = jsonObject.get("report_pdf").toString();;
            LoggerInformation("TestRail Report PDF: " + report_pdf);

//            String entities = "report url: " + report_url + "\\n"
//                              + "report html: " + report_html + "\\n"
//                              + "report_pdf: " + report_pdf;
//            LoggerInformation("TestRail Report: " + entities);
          } else {
            LoggerInformation("Test Rail Test Report Status:" + statusString);
          }
        }
      } catch (Exception ex) {
        LoggerStep_Failed("Unable to create Test Rail Report: ", ex.getMessage(), false);
      }
    }
    else{
      LoggerInformation("Test Summery Report will not be created");
    }
  }

}


