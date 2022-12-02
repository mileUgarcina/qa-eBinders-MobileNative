package testing.appium.runner;

import testing.appium.helpers.TCLogger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import testing.appium.runner.sauceLabs.SauceLabsAPI;
import testing.appium.runner.testRailReporting.TestRailAPI;


import static testing.appium.helpers.Utils.*;
import static testing.appium.helpers.jiraTicket.JiraTicket.*;
import static testing.appium.helpers.testCaseId.TestCaseId.getTestCaseId;


public class TestListener_Cloud implements ITestListener {

    /**
     * Change the color of the log printout
     *
     * Note - Test Rail cannot receive these codes when entering comments in Test Cases
     */
    private static final String reset = TCLogger.ANSI_RESET;
    private static final String red = TCLogger.ANSI_RED;
    private static final String green = TCLogger.ANSI_GREEN;
    private static final String yellow = TCLogger.ANSI_YELLOW;

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     * parameter "resultNumber" - 1 Passed
     */
    @Override
    public void onTestSuccess(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> PASSED ---\\n", true);
        SauceLabsAPI.setSauceLabsTestStatus("passed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
        String testRailParameter = (String) result.getTestContext().getAttribute("testRailParameterData");
        boolean ticketAndroid = getAndroidTicket(result, testRailParameter, true);
        boolean ticketiOS = getiOSTicket(result, testRailParameter, true);
        String ticketNo = getTicketNumberValue(result, testRailParameter);
        String testCaseID = getTestCaseId(result);
        String getReport = testReport(result);
        Reporter.log(green + " ---- Test PASSED: " + nameTC + " ---- " + reset, true);
        TestRailAPI.addTcResult(result, nameTC, testCaseID, "1", getReport, testRailParameter, ticketAndroid, ticketiOS, ticketNo);
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     * parameter "resultNumber" - 5 Failed
     */
    @Override
    public void onTestFailure(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> FAILED  ---\\n", true);
        SauceLabsAPI.setSauceLabsTestStatus("failed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
        String testRailParameter = (String) result.getTestContext().getAttribute("testRailParameterData");
        boolean ticketAndroid = getAndroidTicket(result, testRailParameter, true);
        boolean ticketiOS = getiOSTicket(result, testRailParameter, true);
        String testCaseID = getTestCaseId(result);
        String ticketNo = getTicketNumberValue(result, testRailParameter);
        String getReport = testReport(result);
        Reporter.log(red + " ---- Test FAILED: " + nameTC + " ---- " + reset, true);
        TestRailAPI.addTcResult(result, nameTC, testCaseID, "5", getReport, testRailParameter, ticketAndroid, ticketiOS, ticketNo);
    }

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     * parameter "resultNumber" - 9 Skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {

        String nameTC = result.getName();
        Reporter.log("--- Test Case - " + nameTC + " -- Finish --> SKIPPED ---\\n", true);
        SauceLabsAPI.setSauceLabsTestStatus("failed", (AppiumDriver<MobileElement>) result.getAttribute("driver"));
        String testRailParameter = (String) result.getTestContext().getAttribute("testRailParameterData");
        boolean ticketAndroid = getAndroidTicket(result, testRailParameter, true);
        boolean ticketiOS = getiOSTicket(result, testRailParameter, true);
        String ticketNo = getTicketNumberValue(result, testRailParameter);
        String testCaseID = getTestCaseId(result);
        String getReport = testReport(result);
        Reporter.log(yellow + " ---- Test SKIPPED: " + nameTC + " ---- " + reset, true);
        TestRailAPI.addTcResult(result, nameTC, testCaseID, "9", getReport, testRailParameter, ticketAndroid, ticketiOS, ticketNo);
    }
}




