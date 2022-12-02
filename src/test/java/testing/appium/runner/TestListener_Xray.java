package testing.appium.runner;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import testing.appium.helpers.TCLogger;

import static testing.appium.helpers.Utils.testReport;
import static testing.appium.helpers.jiraTicket.JiraTicket.*;
import static testing.appium.helpers.testCaseId.TestCaseId.getTestCaseId;
import static testing.appium.runner.jiraXrayReporting.JiraXrayAPI.getXRayRunId;
import static testing.appium.runner.jiraXrayReporting.JiraXrayAPI.setTcResult_Xray;
import static testing.appium.runner.propertyFile.DataProvider.ENVIRONMENT;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.entitiesVersion;
import static testing.appium.runner.sauceLabs.SauceLabsAPI.setSauceLabsTestStatus;


public class TestListener_Xray implements ITestListener {

    /**
     * Change the color of the log printout
     * Note - Test Rail cannot receive these codes when entering comments in Test Cases
     */
    private static final String reset = TCLogger.ANSI_RESET;
    private static final String red = TCLogger.ANSI_RED;
    private static final String green = TCLogger.ANSI_GREEN;
    private static final String yellow = TCLogger.ANSI_YELLOW;

    /**
     * Invoked each time a test succeeds.
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     * parameter "testStatus" - PASSED
     */
    @Override
    public void onTestSuccess(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> {color:#36b37e}*PASSED*{color} ---\\n", true);
        if(((String) result.getAttribute("deviceParameter")).contains("SauceLabs")) {
            setSauceLabsTestStatus("passed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
            String SauceLabsVideo = (String) result.getAttribute("sauceLabsTestRunDetails");
            Reporter.log("\\n" +  SauceLabsVideo + "\\n" + "App Name: " + "Florence - eBinders" + "\\n" + "Environment: " + ENVIRONMENT + "\\n" + "App Version: " + entitiesVersion, true);
        }
        String platformParameter = (String) result.getAttribute("platformParameter");
        boolean ticketAndroid = getAndroidTicket(result, platformParameter, true);
        boolean ticketiOS = getiOSTicket(result, platformParameter, true);
        String bugTicketNo = getTicketNumberValue(result, platformParameter);
        String testRunId = getXRayRunId(platformParameter);
        String testCaseID = getTestCaseId(result);
        String getReport = testReport(result);
        setTcResult_Xray(result, platformParameter, nameTC, testRunId, testCaseID, "PASSED", getReport, ticketAndroid, ticketiOS, bugTicketNo);
        Reporter.log(green + "------------ >>>[ Test PASSED: " + nameTC + " ]<<< ------------" + reset, true);
    }

    /**
     * Invoked each time a test fails.
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     * parameter "testStatus" - FAILED
     */
    @Override
    public void onTestFailure(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> {color:#FF0000}*FAILED*{color}  ---\\n", true);
        if(((String) result.getAttribute("deviceParameter")).contains("SauceLabs")) {
            setSauceLabsTestStatus("failed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
            String SauceLabsVideo = (String) result.getAttribute("sauceLabsTestRunDetails");
            Reporter.log("\\n" +  SauceLabsVideo + "\\n" + "App Name: " + "Florence - eBinders" + "\\n" + "Environment: " + ENVIRONMENT + "\\n" + "App Version: " + entitiesVersion, true);
        }
        String platformParameter = (String) result.getAttribute("platformParameter");
        boolean ticketAndroid = getAndroidTicket(result, platformParameter, false);
        boolean ticketiOS = getiOSTicket(result, platformParameter, false);
        String testRunId = getXRayRunId(platformParameter);
        String testCaseID = getTestCaseId(result);
        String bugTicketNo = getTicketNumberValue(result, platformParameter);
        String getReport = testReport(result);
        setTcResult_Xray(result, platformParameter, nameTC, testRunId, testCaseID, "FAILED", getReport, ticketAndroid, ticketiOS, bugTicketNo);
        Reporter.log(red + " ------------ >>>[ Test FAILED: " + nameTC + " ]<<< ------------" + reset, true);

    }

    /**
     * Invoked each time a test is skipped.
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     * parameter "testStatus" - SKIPPED
     */
    @Override
    public void onTestSkipped(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> {color:#FF8E00}*SKIPPED*{color} ---\\n", true);
        if(((String) result.getAttribute("deviceParameter")).contains("SauceLabs")) {
            setSauceLabsTestStatus("failed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
            String SauceLabsVideo = (String) result.getAttribute("sauceLabsTestRunDetails");
            Reporter.log("\\n" +  SauceLabsVideo + "\\n" + "App Name: " + "Florence - eBinders" + "\\n" + "Environment: " + ENVIRONMENT + "\\n" + "App Version: " + entitiesVersion, true);

        }
        String platformParameter = (String) result.getAttribute("platformParameter");
        boolean ticketAndroid = getAndroidTicket(result, platformParameter, true);
        boolean ticketiOS = getiOSTicket(result, platformParameter, true);
        String bugTicketNo = getTicketNumberValue(result, platformParameter);
        String testRunId = getXRayRunId(platformParameter);
        String testCaseID = getTestCaseId(result);
        String getReport = testReport(result);
        setTcResult_Xray(result, platformParameter, nameTC, testRunId, testCaseID, "SKIPPED", getReport, ticketAndroid, ticketiOS, bugTicketNo);
        Reporter.log(yellow + "------------ >>>[ Test SKIPPED: " + nameTC + " ]<<< ------------" + reset, true);
    }
}




