package ru.stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper (ChromeDriver wd) {

    super(wd);
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }


  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void deleteSelectedContact() {

    click(By.xpath("//input[@value='Delete']"));

  }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void updateContactInfo() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }
}
