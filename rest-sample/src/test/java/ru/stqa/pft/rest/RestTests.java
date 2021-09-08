package ru.stqa.pft.rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {
  @Test(enabled = false)
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("TestIssue").withDescription("new Test Issue");
    int issueId =  createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test(enabled = true)
  public  void testStatus() throws IOException {
    String issueStatus = getIssueStatus(1280);
    System.out.println("Статус задачи: "+ issueStatus);
  }

  private Set<Issue> getIssues() throws IOException {
    String json =  getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues"); //jsonArray \[{assignee
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private String getIssueStatus(int id) throws IOException {
    String issueById = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", id))).returnContent().asString(); // {total
    JsonElement parsed = new JsonParser().parse(issueById); //JsonObject {total
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues"); //JsonArray [{assignee
    Set<Issue> issues = new Gson().fromJson( issuesJson, new TypeToken<Set<Issue>>(){}.getType());
    Issue issue = issues.iterator().next();
    String stateName = issue.getStateName();
    return stateName;
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json =  getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                      new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    String issueStatus = getIssueStatus(1280);
    boolean isOpen;
    if ((issueStatus.equals("Closed")) | (issueStatus.equals("Resolved"))) {
      isOpen = false;
    } else isOpen = true;
    return isOpen;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
