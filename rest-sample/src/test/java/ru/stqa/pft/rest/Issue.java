package ru.stqa.pft.rest;

import java.util.Objects;

public class Issue {
  private int id;
  private String subject;
  private String description;
  private String stateName;



  public String getStateName() {
    return stateName;
  }

  public Issue withStateName(String stateName) {
    this.stateName = stateName;
    return this;
  }

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id && Objects.equals(subject, issue.subject) && Objects.equals(description, issue.description) && Objects.equals(stateName, issue.stateName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subject, description, stateName);
  }

}
