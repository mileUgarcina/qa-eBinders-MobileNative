package testing.appium.helpers.jiraTicket;

import org.testng.ITestResult;

import static testing.appium.helpers.TCLogger.LoggerInformation;

public class JiraTicket {

    /**
     * Get Android Ticket Value from Bug Annotation
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @param testRailParameter testNG parameter related to the device type (Android/iOS)
     * @param log    Whether to print a log or not
     * @see Bug
     * @see ITestResult
     */

    public static boolean getAndroidTicket(ITestResult result, String testRailParameter, Boolean log) {
        String android;

        if(testRailParameter.equals("Android"))
        try {
            android = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Bug.class).androidTicket();
            if(!android.equals("null")) {
            if (log) {
                    LoggerInformation("Jira Bug Ticket Android: Yes");
                }
                return true;
            }
        } catch (Exception ex) {
            if (log)
                LoggerInformation("Jira Bug Ticket Android: No");
        }
        return false;
    }


    /**
     * Get iOS Ticket Value from Bug Annotation
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @param testRailParameter testNG parameter related to the device type (Android/iOS)
     * @param log    Whether to print a log or not
     * @see Bug
     * @see ITestResult
     */

    public static boolean getiOSTicket(ITestResult result, String testRailParameter, Boolean log) {

        String iOS;
        if(testRailParameter.equals("iOS"))
        try {
            iOS = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Bug.class).iOSTicket();
            if(!iOS.equals("null")) {
                if (log) {
                    LoggerInformation("Jira Bug Ticket iOS: Yes");
                }
                return true;
            }
        } catch (Exception ex) {
            if (log)
                LoggerInformation("Jira Bug Ticket iOS: No");
        }
        return false;
    }

    /**
     * Get Jira Ticket Number Value from Bug Annotation
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @param platformParameter testNG parameter related to the device type (Android/iOS)
     * @see Bug
     * @see ITestResult
     */

    public static String getTicketNumberValue(ITestResult result, String platformParameter) {

        if (platformParameter.equals("iOS")) {
            boolean iOS = getiOSTicket(result, platformParameter, false);
            if (iOS) {
                String ticketNo = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Bug.class).iOSTicket();
//                LoggerInformation("Jira Bug Ticket Number -iOS: " + ticketNo);
                return ticketNo;
            }
        } else if (platformParameter.equals("Android")) {
            boolean android = getAndroidTicket(result, platformParameter, false);
            if (android) {
                String ticketNo = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Bug.class).androidTicket();
//                LoggerInformation("Jira Bug Ticket Number -Android: " + ticketNo);
                return ticketNo;
            }
        } else {
//            LoggerInformation("No Jira Bug Ticket for " + platformParameter);
        }
        return "null";
    }
}


