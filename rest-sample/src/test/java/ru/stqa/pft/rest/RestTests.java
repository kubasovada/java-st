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

public class RestTests extends TestBase {
  @Test(enabled = true)
  public void testCreateIssue() throws IOException {
    skipIfNotFixed(1280);
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("TestIssue").withDescription("new Test Issue");
    int issueId =  createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test(enabled = false)
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

  private int createIssue(Issue newIssue) throws IOException {
    String json =  getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                      new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

}
