package ru.stqa.addressbook.tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import java.util.*;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().contactList().size() == 0 ) {
      app.contact().createContact(new ContactData("test1", "test2", "79999121000", "email@amail.com", "test1"));
    }
  }

  @Test(enabled = true)
  public void testContactModification() {
    List<ContactData> before = app.contact().contactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "имяМ1", "фамилияМ1", "8800555666777", "newcontact@email.com", null);
    app.contact().modifyContact(index, contact);
    List<ContactData> after = app.contact().contactList();
    Assert.assertEquals(after.size(),before.size());

    before.remove(index);
    before.add((contact));
    Comparator<? super ContactData> byId = (с1,с2) -> Integer.compare(с1.getId(), с2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

    //System.out.println(before.size());

  }
}
