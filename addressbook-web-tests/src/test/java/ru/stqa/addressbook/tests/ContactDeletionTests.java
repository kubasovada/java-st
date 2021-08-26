package ru.stqa.addressbook.tests;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;
import ru.stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {



  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().createContact(new ContactData()
              .withFirstname("Name1").withLastname("LastName1").withMobilePhone("79997775533").withEmail("email@email.ru").inGroup(groups.iterator().next()));
    }
  }


  @Test(enabled = true)
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.goTo().homePage();
    app.contact().delete(deletedContact);
    assertThat(app.contact().contactCount(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();
    assertThat(after, CoreMatchers.equalTo(before.without(deletedContact)));
    verifyContactListInUi();
  }
}
