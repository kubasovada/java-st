package ru.stqa.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddContactPage();
    app.fillContactForm(new ContactData("Darya", "K", "79999121000", "email@amail.com"));
    app.submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }

}
