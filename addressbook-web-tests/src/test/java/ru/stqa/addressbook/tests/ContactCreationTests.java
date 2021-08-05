package ru.stqa.addressbook.tests;

import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData("NEW4", "NEWONE4", "79999121000", "email@amail.com", "test1"));
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after,before + 1 );

    //System.out.println(app.getContactHelper().getContactList());
  }

}
