package ru.stqa.addressbook.tests;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().createContact(new ContactData()
              .withFirstname("Name1").withLastname("LastName1").withMobilePhone("79997775533").withHomePhone("111").withWorkPhone("222")
              .withEmail("email@email.ru").withEmail2("email2@email.ru").withEmail3("email3@email.ru").withHomeAddress("address").withGroup("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstname("Name1").withLastname("LastName1").withMobilePhone("79997775533").withHomePhone("111").withWorkPhone("222")
            .withEmail("email@email.ru").withEmail2("email2@email.ru").withEmail3("email3@email.ru").withHomeAddress("address").withGroup("test1");
    app.goTo().homePage();
    app.contact().modifyContact(contact);
    assertThat(app.contact().contactCount(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUi();
  }
}
