package ru.stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;
import ru.stqa.addressbook.model.GroupData;
import ru.stqa.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("newName").withHeader("newHeader").withFooter("newFooter"));
    }

    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstname("newFirstName").withLastname("newLastName").withHomeAddress("newAddress"));
    }
  }

  @Test
  public void testAddContactToGroup() {
    app.goTo().homePage();
    ContactData contact = selectContact();
    Groups before = contact.getGroups();
    GroupData groupForAdd = selectGroup(contact);
    app.contact().addContactToGroup(contact, groupForAdd);
    Groups after = app.db().getContactsFromDbById(contact.getId()).getGroups();
    assertThat(after, equalTo(before.withAdded(groupForAdd)));
  }

  public ContactData selectContact() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      }
    }

    app.goTo().groupPage();
    app.group().create(new GroupData().withName("test11111"));
    app.goTo().homePage();
    return  contacts.iterator().next();
  }

  public GroupData selectGroup(ContactData contact) {
    Groups allGroups = app.db().groups();
    Collection<GroupData> availableGroups = new HashSet<>(allGroups);
    availableGroups.removeAll(contact.getGroups());
    return availableGroups.iterator().next();
  }
}
