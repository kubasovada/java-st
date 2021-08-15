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
    if (app.contact().all().size() == 0 ) {
      app.contact().createContact(new ContactData()
              .withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1");
    app.contact().modifyContact(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(),before.size());

    before.remove(modifiedContact);
    before.add((contact));

    Assert.assertEquals(before, after);

    //System.out.println(before.size());

  }
}
