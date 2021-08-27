package ru.stqa.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;
import ru.stqa.addressbook.model.GroupData;
import ru.stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDelFromGroupTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("newName").withHeader("newHeader").withFooter("newFooter"));
    }

    if (app.db().contacts().size() == 0) {
      GroupData group = app.db().groups().iterator().next();
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstname("newFirstName").withLastname("newLastName").withHomeAddress("newAddress").inGroup(group));
    }
  }

  @Test
  public void testContactDeletionFromGroup() {
    ContactData contact = selectContact();
    GroupData groupForRemove = selectGroup(contact);
    Groups before = contact.getGroups();
    app.goTo().homePage();

    app.contact().selectGroupForRemove(groupForRemove.getId());
    app.contact().removeContactFromGroup(contact);

    ContactData contactsAfter = selectContactById(contact);
    Groups after = contactsAfter.getGroups();
    assertThat(after, equalTo(before.without(groupForRemove)));

  }

  private ContactData selectContactById(ContactData contact) {
    Contacts contactsById = app.db().contacts();
    return contactsById.iterator().next().withId(contact.getId());
  }

  private GroupData selectGroup(ContactData removeContact) {
    ContactData contact = selectContactById(removeContact);
    Groups removedContact = contact.getGroups();
    return removedContact.iterator().next();
  }

  private ContactData selectContact() {
    Contacts contacts = app.db().contacts();
    for (ContactData contact: contacts) {
      if (contact.getGroups().size() > 0) {
        return contact;
      }
    }
    ContactData addContact = app.db().contacts().iterator().next();
    app.contact().addContactToGroup(addContact, app.db().groups().iterator().next());
    return addContact;
  }


}
