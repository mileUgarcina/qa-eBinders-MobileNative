
package ch.clx.testing.appium.runner;

public class Config {
  public static class qa {

  public static class Android {
    public static final String PackageId = "com.crealogix.conversational.qa";
    public static final String SauceLabsId = "c6c926f0-69ee-4733-9856-20c7831d6cdb";
    public static final String SauceLabsName = "eBinders_QA";
    public static final String OSVersion = "12.0";
    
    public static final class Clinical {
    public static final String Username = "mobile.automation@florencehc.com";
    public static final String Password = "Password123*";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
    
    public static final class Participant {
    public static final String Username = "mobile.automation+active_android@florencehc.com";
    public static final String Password = "Password123*";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
  }

  public static class iOS {
    public static final String PackageId = "com.crealogix.conversational.qa";
    public static final String SauceLabsId = "c6c926f0-69ee-4733-9856-20c7831d6cdb";
    public static final String SauceLabsName = "eBinders_QA";
    public static final String OSVersionRealDevice = "16";
    public static final String OSVersionSimulator = "16";

  public static final class Clinical {
    public static final String Username = "mobile.automation@florencehc.com";
    public static final String Password = "Password123*";
   public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }

  public static final class Participant {
    public static final String Username = "mobile.automation+active_ios@florencehc.com";
    public static final String Password = "Password123*";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }
}

  public static class App {
    public static final String Url = "https://us-participant-auth.se.qav2.researchbinders.com";
  }
}

public static class fate {

  public static class Android {
    public static final String PackageId = "com.crealogix.conversational.dev";
    public static final String SauceLabsId = "c6c926f0-69ee-4733-9856-20c7831d6cdb";
    public static final String SauceLabsName = "eBinders_FATE";
    public static final String OSVersion = "12.0";
    
    public static final class Clinical {
    public static final String Username = "Admin.Android.dev@crealogix.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
    
    public static final class Participant {
    public static final String Username = "Client.Android.dev@gmail.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
  }

  public static class iOS {
    public static final String PackageId = "com.crealogix.conversational.dev";
    public static final String SauceLabsId = "c6c926f0-69ee-4733-9856-20c7831d6cdb";
    public static final String SauceLabsName = "eBinders_FATE";
    public static final String OSVersionRealDevice = "16";
    public static final String OSVersionSimulator = "15.4";

  public static final class Clinical {
    public static final String Username = "Admin.iOS.dev@crealogix.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }

  public static final class Participant {
    public static final String Username = "Client.iOS.dev@gmail.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }
}

  public static class App {
    public static final String Url = "https://us-participant-auth.se.fatev2.researchbinders.com";
  }
}
  
  public static class uat {

  public static class Android {
    public static final String PackageId = "com.crealogix.conversational.sit";
    public static final String SauceLabsId = "46acc854-80ba-460b-a36d-da3d29bc0cf8";
    public static final String SauceLabsName = "eBinders_UAT";
    public static final String OSVersion = "12";
    
    public static final class Clinical {
    public static final String Username = "Admin.Android.sit@crealogix.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
    
    public static final class Participant {
    public static final String Username = "Client.Android.sit@gmail.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "Android";
    }
  }

  public static class iOS {
    public static final String PackageId = "com.crealogix.conversational.sit";
    public static final String SauceLabsId = "c6c926f0-69ee-4733-9856-20c7831d6cdb";
    public static final String SauceLabsName = "eBinders_UAT";
    public static final String OSVersionRealDevice = "16";
    public static final String OSVersionSimulator = "15.4";

  public static final class Clinical {
    public static final String Username = "Admin.iOS.sit@crealogix.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }

  public static final class Participant {
    public static final String Username = "Client.iOS.sit@gmail.com";
    public static final String Password = "1234";
    public static final String FirstName = "Active";
    public static final String MiddleName = "Appium";
    public static final String LastName = "iOS";
  }
}

  public static class App {
    public static final String Url = "https://us-participant-auth.se.uatv2.researchbinders.com";
  }
}
}

