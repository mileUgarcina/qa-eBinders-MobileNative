package testing.appium.helpers;

import org.testng.Assert;
import org.testng.Reporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TCLogger {

    static DateTimeFormatter instantTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Change the color of the log printout
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void LoggerWaiting(String text) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Waiting--> " + text, true);
    }

    public static void LoggerCheck(String text) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Check-->  " + text, true);
    }

    public static void LoggerAction(String text) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Action-->  " + text, true);
    }

    public static void LoggerInformation(String text) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Information-->  " + text, true);
    }

    public static void LoggerStep_Failed(String text, String getMessage, boolean failTest) {
        String[] getMessageCut = getMessage.split("\n");
        String getMessage0 = getMessageCut[0];
        Reporter.log(LocalDateTime.now().format(instantTime) + " *Step FAILED-->*  {color:#FF0000}" + text + " - "+ getMessage0 + "{color}",  true);
        if(failTest){Assert.fail();}
    }

    public static void LoggerException(String text) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Exception-->  " + text, true);
    }

    public static void LoggerContext(String text, String context) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Context-->  " + text + context , true);
    }

    public static void LoggerAssert_Passed(String messageContent) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Assert-->  " + messageContent, true);
    }

    public static void LoggerAssert_Failed(String messageContent) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  *Assert FAILED-->*  {color:#FF0000}" + messageContent +"{color}", true);
    }

    public static void LoggerPrintScreen(String ts) {
        Reporter.log(LocalDateTime.now().format(instantTime) + "  Print Screen-->  " + ts, true);
    }
}
