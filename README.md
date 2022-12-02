Documentation in Progress
# MEP Conversational Mobile Testing
Appium - Java - TestNG - SauceLabs - Test Rail framework for mobile testing.

This is a framework for testing Mobile applications on mobile devices, both Android and iOS. 
Use Appium as a basis, and the code is written in Java. It also uses testng to manipulate test cases. 
Execution of the tests themselves is performed at Sauce Labs, and the results are stored and displayed in Test Rail.

____________________________________________________________

## Install
* Java 8
* Gradle
* Appium
* Xcode (optional)
* Android Studio (optional)

____________________________________________________________

## Project Links

* [MEP Conversational Use Cases](https://collaborate.crealogix.com/confluence/pages/viewpage.action?spaceKey=CAI&title=Use+Cases)
* [MEP Conversational Jira ](https://e-banking.crealogix.com/jira/projects/CAIC/summary)
* [MEP Conversational Repository ](https://bitbucket.ebs.crealogix.net:8443/projects/MEP/repos/clx-mep-deploy/browse)
* [Test Rail Suite](https://testrail.ebs.crealogix.net/index.php?/suites/view/26331&group_by=cases:section_id&group_order=asc&group_id=5623651)
* [Test Rail Report](https://testrail.ebs.crealogix.net/index.php?/projects/overview/53)
* [SauceLabs Android App - MEP Conversational Dev](https://app.eu-central-1.saucelabs.com/live/app-testing/file/a90c36db-e14f-4d75-a3f1-629649ba2817/device)
* [SauceLabs iOS App - MEP Conversational Dev](https://app.eu-central-1.saucelabs.com/live/app-testing/file/119b032f-e9f0-40c6-a1d7-889cfe1ed328/device)
* [SauceLabs Android App - MEP Conversational QA](https://app.eu-central-1.saucelabs.com/live/app-testing/file/4e07541c-d0b2-4436-9f47-c91999213296/device)
* [SauceLabs iOS App - MEP Conversational QA](https://app.eu-central-1.saucelabs.com/live/app-testing/file/988236bb-3793-4697-96bf-56fcdd193ad6/device)
* [SauceLabs Android App - MEP Conversational SIT](https://app.eu-central-1.saucelabs.com/live/app-testing/file/46acc854-80ba-460b-a36d-da3d29bc0cf8/device)
* [SauceLabs iOS App - MEP Conversational SIT](https://app.eu-central-1.saucelabs.com/live/app-testing/file/7bd2c05f-44a9-4560-8c79-fcf084019381/device)
* [WEB App -  MEP Conversational Dev](https://conversational-dev-auth.clx-cloud.com/auth/realms/clx/protocol/openid-connect/auth?client_id=tenantselector&redirect_uri=https%3A%2F%2Fconversational-dev.clx-cloud.com%2Fdemo%2Fadvisor%2Flogin%2F&response_type=code&scope=email+openid&state=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTM5MDU1NzQsImlhdCI6MTY1MzkwMzc3NCwic3RhdGUiOiJodHRwczovL2NvbnZlcnNhdGlvbmFsLWRldi5jbHgtY2xvdWQuY29tL2RlbW8vIn0.dawNPMi7crslNc3dfHe5io6rp8DxVGqEow39rcKfCLU)
* [WEB App -  MEP Conversational QA](https://conversational-qa-auth.clx-cloud.com/auth/realms/clx/protocol/openid-connect/auth?client_id=tenantselector&redirect_uri=https%3A%2F%2Fconversational-qa.clx-cloud.com%2Fdemo%2Fadvisor%2Flogin%2F&response_type=code&scope=email+openid&state=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTM5MDU2MjQsImlhdCI6MTY1MzkwMzgyNCwic3RhdGUiOiJodHRwczovL2NvbnZlcnNhdGlvbmFsLXFhLmNseC1jbG91ZC5jb20vZGVtby91c2VyL2dyb3Vwcy82MjQ4NTA2ZjZjMDk5MzJhNGY3ZDZjMzAifQ.vNy7BUaL2q93-KZhQSifMm76HljvbXtu-kliWvw8tiM)
* [WEB App -  MEP Conversational SIT](https://conversational-sit-auth.clx-cloud.com/auth/realms/clx/protocol/openid-connect/auth?client_id=tenantselector&redirect_uri=https%3A%2F%2Fconversational-sit.clx-cloud.com%2Fdemo%2Fadvisor%2Flogin%2F&response_type=code&scope=email+openid&state=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTE2NjI2NTUsImlhdCI6MTY1MTY2MDg1NSwic3RhdGUiOiJodHRwczovL2NvbnZlcnNhdGlvbmFsLXNpdC5jbHgtY2xvdWQuY29tL2RlbW8vdXNlci9jaGFubmVscy82MjcyNTY2MGQyNzc4MTc5MDM3ZDRlMDQvZGV0YWlscyJ9.Nj3ey9kHpzY78RE7chTzkWpV2sCVj1IDOlOOCL0q6vo)
* [Keycloak Admin Console Dev](https://conversational-dev-auth.clx-cloud.com/auth/realms/master/protocol/openid-connect/auth?client_id=security-admin-console&redirect_uri=https%3A%2F%2Fconversational-dev-auth.clx-cloud.com%2Fauth%2Fadmin%2Fmaster%2Fconsole%2F&state=597b41cc-3ac0-4f80-9d68-33cd17e405e3&response_mode=fragment&response_type=code&scope=openid&nonce=196fb2a1-4931-424b-8ed7-2de26e42d048&code_challenge=sHDb5vStgybXEGc16TgH5f1xnv9KGKKLJd1FSLs_yCc&code_challenge_method=S256)
* [Keycloak Admin Console QA](https://conversational-qa-auth.clx-cloud.com/auth/realms/master/protocol/openid-connect/auth?client_id=security-admin-console&redirect_uri=https%3A%2F%2Fconversational-qa-auth.clx-cloud.com%2Fauth%2Fadmin%2Fmaster%2Fconsole%2F%23%2Frealms%2Fclx&state=551b97ce-fa6b-40ba-8ad6-877fbb20272b&response_mode=fragment&response_type=code&scope=openid&nonce=c848d995-610b-4ac7-add3-7f6dccde33bf&code_challenge=0deFKBUI8Bp_FCVReE7Gk9MZ8iibqmnS-JEbhIHURus&code_challenge_method=S256)
* [Keycloak Admin Console SIT](https://conversational-sit-auth.clx-cloud.com/auth/realms/master/protocol/openid-connect/auth?client_id=security-admin-console&redirect_uri=https%3A%2F%2Fconversational-qa-auth.clx-cloud.com%2Fauth%2Fadmin%2Fmaster%2Fconsole%2F%23%2Frealms%2Fclx&state=551b97ce-fa6b-40ba-8ad6-877fbb20272b&response_mode=fragment&response_type=code&scope=openid&nonce=c848d995-610b-4ac7-add3-7f6dccde33bf&code_challenge=0deFKBUI8Bp_FCVReE7Gk9MZ8iibqmnS-JEbhIHURus&code_challenge_method=S256)

____________________________________________________________
## Run Test 

- command for Mac OS terminal --> context

### _Android Test_

#### QE Environment


*  **./gradlew clean test -Pregression_Android_QA** --> Start regression testing for Android device on QA environment
*  **./gradlew clean test -PsmokeTest_Android_QA** --> Start smoke testing for Android device on QA environment

#### Dev Environment

*  **./gradlew clean test -Pregression_Android_Dev** --> Start regression testing for Android device on Dev environment
*  **./gradlew clean test -PsmokeTest_Android_Dev** --> Start smoke testing for Android device on Dev environment

#### SIT Environment

*  **./gradlew clean test -Pregression_Android_SIT** --> Start regression testing for Android device on SIT environment
*  **./gradlew clean test -PsmokeTest_Android_SIT** --> Start smoke testing for Android device on SIT environment


### _iOS Test_

#### QE Environment

*  **./gradlew clean test -Pregression_iOS_QA** --> Start regression testing for iOS device on QA environment
*  **./gradlew clean test -PsmokeTest_iOS_QA**  --> Start smoke testing for iOS device on QA environment


#### Dev Environment

*  **./gradlew clean test -Pregression_iOS_Dev**  --> Start regression testing for iOS device on Dev environment
*  **./gradlew clean test -PsmokeTest_iOS_Dev**  --> Start smoke testing for iSO device on Dev environment


#### SIT Environment

*  **./gradlew clean test -Pregression_iOS_SIT**  --> Start regression testing for iOS device on SIT environment
*  **./gradlew clean test -PsmokeTest_iOS_SIT**  --> Start smoke testing for iSO device on SIT environment




### _Parallel Execution_ 

#### QE Environment

*  **./gradlew clean test -Pregression_Android_QA & ./gradlew clean test -Pregression_iOS_QA** --> Start parallel regression testing for Android and iOS device on QA environment
*  **./gradlew clean test -PsmokeTest_Android_QA & ./gradlew clean test -PsmokeTest_iOS_QA** --> Start parallel smoke testing for Android and iOS device on QA environment


#### Dev Environment

*  **./gradlew clean test -Pregression_Android_Dev & ./gradlew clean test -Pregression_iOS_Dev** --> Start parallel regression testing for Android and iOS device on Dev environment
*  **./gradlew clean test -PsmokeTest_Android_Dev & ./gradlew clean test -PsmokeTest_iOS_Dev** --> Start parallel smoke testing for Android and iOS device on Dev environment


#### SIT Environment

* **./gradlew clean test -Pregression_Android_SIT & ./gradlew clean test -Pregression_iOS_SIT** --> Start parallel regression testing for Android and iOS device on SIT environment
* **./gradlew clean test -PsmokeTest_Android_SIT & ./gradlew clean test -PsmokeTest_iOS_SIT** --> Start parallel smoke testing for Android and iOS device on SIT environment
____________________________________________________________


## [testNG](testNG)
Folder contains testNG XML files

* Android
  - Advisor
    - [Set1_Conversations_Advisor_AndroidEmulator.xml](testNG/Android/Advisor/Set1_Conversations_Advisor_AndroidEmulator.xml)
    - [Set1_Conversations_Advisor_AndroidRealDevice.xml](testNG/Android/Advisor/Set1_Conversations_Advisor_AndroidRealDevice.xml)
    - [SmokeTest_Advisor_AndroidRealDevice.xml](testNG/Android/Advisor/SmokeTest_Advisor_AndroidRealDevice.xml)
  - Client
    - [Set1_Conversations_Client_AndroidEmulator.xml](testNG/Android/Client/Set1_Conversations_Client_AndroidEmulator.xml)
    - [Set1_Conversations_Client_AndroidRealDevice.xml](testNG/Android/Client/Set1_Conversations_Client_AndroidRealDevice.xml)
    - [Set2_Channels_Client_AndroidRealDevice.xml](testNG/Android/Client/Set2_Channels_Client_AndroidRealDevice.xml)
    - [Set3_Assistant_Client_AndroidRealDevice.xml](testNG/Android/Client/Set3_Assistant_Client_AndroidRealDevice.xml)
    - [SmokeTest_Client_AndroidRealDevice.xml](testNG/Android/Client/SmokeTest_Client_AndroidRealDevice.xml)
  - [Regression_Android_Suite.xml](testNG/Android/Web/Regression_Android_Suite.xml)
  - [Smoke_Android_Suite.xml](testNG/Android/Web/Smoke_Android_Suite.xml)
* iOS
  - Advisor
    - [Set1_Conversations_Advisor_iOSRealDevice.xml](testNG/iOS/Advisor/Set1_Conversations_Advisor_iOSRealDevice.xml)
    - [Set1_Conversations_Advisor_iOSSimulator.xml](testNG/iOS/Advisor/Set1_Conversations_Advisor_iOSSimulator.xml)
    - [SmokeTest_Advisor_iOSRealDevice.xml](testNG/iOS/Advisor/SmokeTest_Advisor_iOSRealDevice.xml)
  - Client
    - [Set1_Conversations_Client_iOSRealDevice.xml](testNG/iOS/Client/Set1_Conversations_Client_iOSRealDevice.xml)
    - [Set1_Conversations_Client_iOSSimulator.xml](testNG/iOS/Client/Set1_Conversations_Client_iOSSimulator.xml)
    - [Set2_Channels_Client_iOSRealDevice.xml](testNG/iOS/Client/Set2_Channels_Client_iOSRealDevice.xml)
    - [Set3_Assistant_Client_iOSRealDevice.xml](testNG/iOS/Client/Set3_Assistant_Client_iOSRealDevice.xml)
    - [SmokeTest_Client_iOSRealDevice.xml](testNG/iOS/Client/SmokeTest_Client_iOSRealDevice.xml)
  - [Regression_iOS_Suite.xml](testNG/iOS/Regression_iOS_Suite.xml)
  - [Smoke_iOS_Suite.xml](testNG/iOS/Smoke_iOS_Suite.xml)

### testNG parameter 
* name="platformParameter" - Parameter for setting desire capability 
  - [value="remoteAndroidCapsEmulator"](src/test/java/ch/clx/testing/appium/runner/Init_Android.java)
  - [value="remoteAndroidCapsDefault"](src/test/java/ch/clx/testing/appium/runner/Init_Android.java)
  - [value="localAndroidCaps"](src/test/java/ch/clx/testing/appium/runner/Init_Android.java)
  - [value="remoteiOSCapsSimulator"](src/test/java/ch/clx/testing/appium/runner/Init_iOS.java)
  - [value="remoteiOSCapsDefault"](src/test/java/ch/clx/testing/appium/runner/Init_iOS.java)
  - [value="localiOSimulatorSCaps"](src/test/java/ch/clx/testing/appium/runner/Init_iOS.java)
  - [value="localiOSRealDeviceCaps"](src/test/java/ch/clx/testing/appium/runner/Init_iOS.java)
* name="testRailParameter" - Parameter for separate Android and iOS Test Run in Test Rail
  - [value="Android"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
  - [value="iOS"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
* name="testRailRun" - Parameter for creation or update Test Run in Test Rail
  - [value="Create"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
  - [value="Update"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
  - [value="No"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
* name="testRailRunCompleted" - Parameter for closing the Test Run and creating a Test Report
  - [value="Yes"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
  - [value="No"](src/test/java/ch/clx/testing/appium/mepTesting/BaseTestSet.java)
* name="user" - User for testing
* name="searchUser" - User for testing
* name="forwardUser" - User for testing
* name="participantUser" - User for testing
* name="bulkUser" - User for testing
* name="inboundUser" - User for testing

____________________________________________________________

## Config File

[settings.json](config/settings.json)

Serves for Ci CD pipeline main data file . The following, for all three environments can be found and set in it:

1. Android
* PackageId
* SauceLabsId
* SauceLabsName
* OSVersion
* Admin Username
* AdminPassword
* Client Username
* Client Password


2. iOS
* PackageId
* SauceLabsId
* SauceLabsName
* OSVersionRealDevice
* OSVersionSimulator
* Admin Username
* Admin Password 
* Client Username
* Client Password
____________________________________________________________

## [Properties file](src/main/resources)


They include configuration data for Sauce Labs, Test Rail and Test Data

### *testRailRunId.properties 
is used to store the Test Rail Run ID number
* [Android_dev_testRailRunId.properties](src/main/resources/Android_dev_testRailRunId.properties)
* [Android_qa_testRailRunId.properties](src/main/resources/Android_qa_testRailRunId.properties)
* [Android_sit_testRailRunId.properties](src/main/resources/Android_sit_testRailRunId.properties)
* [iOS_dev_testRailRunId.properties](src/main/resources/iOS_dev_testRailRunId.properties)
* [iOS_qa_testRailRunId.properties](src/main/resources/iOS_qa_testRailRunId.properties)
* [iOS_sit_testRailRunId.properties](src/main/resources/iOS_sit_testRailRunId.properties)


#### [environment.properties](src/main/resources/environment.properties)
serves to store basic environment parameters

* sauceLabsAppiumUrl - Sauce Labs appium server URL
* localAppiumUrl local appium server URL
* devTeam - Name of The Dev Team
* appName - Name of The Application
* envVariableQA - Name of the QA environment
* envVariableDev - Name of the Dev environment
* envVariableSIT - Name of the SIt environment
* manualSauceLabsData - Automatic or manual entry of File ID from Sauce Labs


### Configuration.properties

It serves to store [Appium Desired Capabilities](https://appium.io/docs/en/writing-running-appium/caps/) configuration data  for both local and Sauce Labs


* [localAndroidConfiguration.properties](src/main/resources/localAndroidConfiguration.properties)
* [localiOSConfiguration.properties](src/main/resources/localiOSConfiguration.properties)
* [slAndroidConfiguration.properties](src/main/resources/slAndroidConfiguration.properties)
* [sliOSConfiguration.properties](src/main/resources/sliOSConfiguration.properties)



### [slAuthorisationDataAndSetup.properties](src/main/resources/slAuthorisationDataAndSetup.properties)

In it, we can find the basic parameters needed for Sauce Labs such as:

* sLTestResultLink - Test Result link
* GetAppStorageFiles - App Storage Files link
* username - Sauce Labs Username
* accessKey - Sauce Labs Access Key
* sauceLabsToken - Sauce Labs User Token
* virtualDeviceLink - Get Jobs Virtual Device
* realDeviceLink -Get Jobs Real Device
* limit - Number of Get Jobs Real Device


### [tcData.properties](src/main/resources/tcData.properties)

All test data is in this file

* Path to test files
* Home Page Test Data
* Message Page Test Data
* Assistant Page


### [testRailData.properties](src/main/resources/testRailData.properties)

Here are all the necessary parameters and data for integration with Test Rail.
One of the most important data here would be "_case_ids_Android_" and "_case_ids_iOS_"

_case_ids_Android_ / _case_ids_iOS_ - variable that stores a string of IDs from Test Cases written in Test Rail.

____________________________________________________________



