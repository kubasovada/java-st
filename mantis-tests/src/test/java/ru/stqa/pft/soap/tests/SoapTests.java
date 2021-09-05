package ru.stqa.pft.soap.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.soap.model.Issue;
import ru.stqa.pft.soap.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(4);
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project: projects) {
      System.out.println(project.getName());
    }
  }

  @Test(enabled = false)
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description").withProject(projects.iterator().next());
    Issue created =  app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }

  @Test(enabled = false)
  public void getIssueStatus() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(2);
    int issueId = 1;
    String issueStatus = app.soap().getIssueStatus(issueId);
    System.out.println(issueStatus);
    Assert.assertEquals(issueStatus, "new");
  }
}
