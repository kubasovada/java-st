package ru.stqa.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Hey", "Gui", "8800555666777", "newcontact@email.com"));
    app.getContactHelper().updateContactInfo();
    app.getNavigationHelper().goToHomePage();



  }

}
