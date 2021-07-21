package ru.stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class NavigationHelper {
  private ChromeDriver wd;

  public NavigationHelper(ChromeDriver wd) {
    this.wd = wd;
  }

  public void gotoGroupPage() {
    wd.findElement(By.linkText("groups")).click();
  }

  public void goToHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  public void gotoAddContactPage() {
    wd.findElement(By.linkText("add new")).click();
  }
}
