package ru.stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;

import java.util.*;

public class ContactModificationTests extends TestBase{

  @Test(enabled = true)
  public void testContactModification() {
    app.goTo().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "79999121000", "email@amail.com", "test1"));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    //app.getContactHelper().selectContact(before.size() - 1);
    int index = before.size() - 1;
    app.getContactHelper().initContactModification(index);
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "имяМ1", "фамилияМ1", "8800555666777", "newcontact@email.com", null);
    app.getContactHelper().fillContactForm((contact), false);
    app.getContactHelper().updateContactInfo();
    app.goTo().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size());

    before.remove(index);
    before.add((contact));
    Comparator<? super ContactData> byId = (с1,с2) -> Integer.compare(с1.getId(), с2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
