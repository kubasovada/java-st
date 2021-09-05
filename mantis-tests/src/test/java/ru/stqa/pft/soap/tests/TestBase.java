package ru.stqa.pft.soap.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.soap.appmanager.ApplicationManager;
import ru.stqa.pft.soap.model.Issue;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {

  protected static ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    app.soap().getMantisConnect();
    String issue = app.soap().getIssueStatus(issueId);
    boolean isOpen;
    if (issue.equals("closed")) {
      isOpen = false;
    } else isOpen = true;
    return isOpen;
  }

  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}
