package ru.stqa.addressbook.appmanager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
  ApplicationManager manager;

  public ContactHelper(ApplicationManager manager) {
    super(manager.wd);
    this.manager = manager;
  }

  public void createContact(ContactData contact) {
    manager.goTo().AddContactPage();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    manager.goTo().homePage();
  }

  public void modifyContact(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    updateContactInfo();
    contactCache = null;
    manager.goTo().homePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContact();
    acceptAlert();
    manager.goTo().homePage();
  }

  public void delete(ContactData deletedContact) {
    selectContactById(deletedContact.getId());
    deleteSelectedContact();
    acceptAlert();
    contactCache = null;
    manager.goTo().homePage();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("address"), contactData.getHomeAddress());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void initContactModification(int index) {
    //click(By.xpath("//img[@alt='Edit']"));
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();

  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    /*
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

    В XPATH нумерация начинается с 1, а не с 0.
    wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();
    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    */
  }

  public void updateContactInfo() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("td.center input[id='" + id + "']")).click();
  }

  public void deleteSelectedContact() { click(By.xpath("//input[@value='Delete']")); }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int contactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public List<ContactData> contactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.cssSelector("td.center input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }

  private Contacts contactCache = null;


  public Contacts allOld() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.cssSelector("td.center input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return new Contacts(contactCache);
  }

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allPhones = cells.get(5).getText();
      //String[] phones = allPhones.split("\n");
      String allEmails = cells.get(4).getText();
      String homeAddress = cells.get(3).getText();

      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAllPhones(allPhones).withAllEmails(allEmails).withHomeAddress(homeAddress));
    }
    return new Contacts(contactCache);
  }


  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3).withHomeAddress(address);

  }


}
