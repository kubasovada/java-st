package ru.stqa.pft.soap.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mantis_user_table")
public class UserData {
  @Id
  private int id;
  private String username;
  private String email;

  public String getUsername() {return username;}

  public String getEmail() {return email;}

  public  int getId() {return  id;}

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public UserData withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
