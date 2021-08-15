package ru.stqa.addressbook.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1");

    app.contact().createContact(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(),before.size() + 1 );

    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    //mapToInt превращает в поток идентификаторов, целых чисел
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}
