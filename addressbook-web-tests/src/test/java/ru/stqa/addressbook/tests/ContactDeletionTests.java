package ru.stqa.addressbook.tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import java.util.List;
public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().contactList().size() == 0) {
      app.contact().createContact(new ContactData().withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactDeletion() {
    List<ContactData> before = app.contact().contactList();
    int index = before.size() - 1;
    app.contact().delete(index);
    List<ContactData> after = app.contact().contactList();
    Assert.assertEquals(after.size(),before.size() - 1 );

    before.remove(index);
    Assert.assertEquals(before, after);

  }


}
