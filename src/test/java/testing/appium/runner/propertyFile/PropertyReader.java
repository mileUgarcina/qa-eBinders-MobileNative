package testing.appium.runner.propertyFile;

import testing.appium.helpers.TCLogger;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {


    /**
     * This method reads and extracts the value of a variable from tcData.properties file.
     *
     * @keyName
     */
    public static class TcDataReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/tcData.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -TcDataReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from localAndroidConfiguration.properties file.
     *
     * @keyName
     */
    public static class LocalAndroidConfigReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/localAndroidConfiguration.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -LocalAndroidConfigReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from localiOSConfiguration.properties file.
     *
     * @keyName
     */
    public static class LocaliOSConfigurationReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/localiOSConfiguration.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -iOSLocalConfigReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from SlAndroidConfiguration file.
     *
     * @keyName
     */
    public static class SlAndroidConfigurationReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/slAndroidConfiguration.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -SlAndroidConfiguration- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from SliOSConfiguration file.
     *
     * @keyName
     */
    public static class SliOSConfigurationReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/sliOSConfiguration.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -SliOSConfigurationReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from slAuthorisationDataAndSetup.propertiese file.
     *
     * @keyName
     */
    public static class SlAuthorisationDataReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/SauceLabs_Config.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -slAuthorisationData- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }


    /**
     * This method reads and extracts the value of a variable from browserStackAuthorisationDataAndSetup.properties file.
     *
     * @keyName
     */
    public static class browserStackAuthorisationDataReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/BrowserStack_Config.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -browserStackAuthorisationData- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from environment.properties file.
     *
     * @keyName
     */
    public static class EnvironmentReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/environment.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -environment- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {
            return properties.getProperty(keyName);
        }
    }

    /**
     * This method reads and extracts the value of a variable from testRailData.properties file.
     *
     * @keyName
     */
    public static class testRailReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/testRailData.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -TestRailDataReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {return properties.getProperty(keyName);}
    }

    /**
     * This method reads and extracts the value of a variable from xRayData.properties file.
     *
     * @keyName
     */
    public static class xRayReader {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/xRayData.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -xRayDataReader- failed: " , ex.getMessage(), true);
            }
        }

        public static String get(String keyName) {return properties.getProperty(keyName);}
    }

    /**
     * This method reads and extracts the value of a variable from AndroidtestRailRunId.properties file.
     *
     * @keyName
     */
    public static class xRayIdAndroidQa {
        private static Properties  properties;

        static  {
            try {
                String path = "src/main/resources/Android_QA_xRayRunId.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed( "File Input Stream -Android_qa_testRailRunId- failed: " , ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
    }

    public static class xRayAndroidFate {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/Android_FATE_xRayRunId.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed("File Input Stream -Android_dev_testRailRunId- failed: ", ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
}

    public static class xRayAndroidUat {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/Android_UAT_xRayRunId.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed("File Input Stream -Android_sit_testRailRunId- failed: ", ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
    }

        /**
         * This method reads and extracts the value of a variable from iOStestRailRunId.properties file.
         *
         * @keyName
         */
        public static class xRayRunIdiOSQa {
            private static Properties properties;

            static {
                try {
                    String path = "src/main/resources/iOS_QA_xRayRunId.properties";
                    FileInputStream input = new FileInputStream(path);
                    properties = new Properties();
                    properties.load(input);
                    input.close();
                } catch (Exception ex) {
                    TCLogger.LoggerStep_Failed("File Input Stream -iOS_qa_testRailRunId- failed: ", ex.getMessage(), true);
                }
            }
            public static String get(String keyName) {return properties.getProperty(keyName);}
        }

    public static class xRayRunIdiOSFate {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/iOS_FATE_XRay.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed("File Input Stream -iOS_dev_testRailRunId- failed: ", ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
    }


    public static class xRayRunIdiOSSit {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/iOS_UAT_xRayRunId.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed("File Input Stream -iOS_sit_testRailRunId- failed: ", ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
    }

    public static class eConsentAPI_Data {
        private static Properties properties;

        static {
            try {
                String path = "src/main/resources/eConsentAPI_Data.properties";
                FileInputStream input = new FileInputStream(path);
                properties = new Properties();
                properties.load(input);
                input.close();
            } catch (Exception ex) {
                TCLogger.LoggerStep_Failed("File Input Stream -eConsentAPI_Data- failed: ", ex.getMessage(), true);
            }
        }
        public static String get(String keyName) {return properties.getProperty(keyName);}
    }
}
