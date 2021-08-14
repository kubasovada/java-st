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
      app.contact().createContact(new ContactData()
              .withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactModification() {
    List<ContactData> before = app.contact().contactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData().withId(before.get(before.size() - 1).getId())
            .withFirstname("Name1").withLastname("LastName1").withMobile("79997775533").withEmail("email@email.ru").withGroup("test1");
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
