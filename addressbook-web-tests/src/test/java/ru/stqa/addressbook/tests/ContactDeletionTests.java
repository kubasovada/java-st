package ru.stqa.addressbook.tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().createContact(new ContactData().withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactDeletion() {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(),before.size() - 1 );

    before.remove(deletedContact);
    Assert.assertEquals(before, after);
  }
}
