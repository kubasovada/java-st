package ru.stqa.addressbook.tests;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before =  app.contact().all();
    File photo =  new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstname("Name1").withLastname("LastName1")
            .withMobilePhone("79997775533").withWorkPhone("111").withHomePhone("222")
            .withEmail("email@email.ru").withGroup("test1").withPhoto(photo);
    app.contact().createContact(contact);
    assertThat(app.contact().contactCount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    //mapToInt превращает в поток идентификаторов, целых чисел
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test (enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/stru.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }


  @Test(enabled = false)
  public void testBadNameContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before =  app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Name1'").withLastname("LastName1").withMobilePhone("79997775533").withEmail("email@email.ru").withGroup("test1");
    app.contact().createContact(contact);
    assertThat(app.contact().contactCount(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before));
  }

}
