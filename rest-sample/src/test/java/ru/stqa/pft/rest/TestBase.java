package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {
  protected String getIssueStatus(int id) throws IOException {
    String issueById = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", id))).returnContent().asString(); // {total
    JsonElement parsed = new JsonParser().parse(issueById); //JsonObject {total
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues"); //JsonArray [{assignee
    Set<Issue> issues = new Gson().fromJson( issuesJson, new TypeToken<Set<Issue>>(){}.getType());
    Issue issue = issues.iterator().next();
    String stateName = issue.getStateName();
    System.out.println("StateName =  " + stateName);
    return stateName;
  }

  protected Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    String issueStatus = getIssueStatus(issueId);
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


